package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.finalproject.databinding.ActivityShowImageBinding;
import com.google.android.material.navigation.NavigationView;
import com.jgabrielfreitas.core.BlurImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ShowImageActivity
 *
 * @author Aladah M + Will B
 * @version 1.00
 *
 *This is the activity that pops up to display the image
 * associated with the date picked in EnterDateActivity
 *
 * @see com.example.finalproject.EnterDateActivity
 *
 * The button allows user option to save image to database
 *
 * @see com.example.finalproject.ShowSavedImageActivity
 *
 * This activity binds to DrawerBaseActivity for navigation drawer and toolbar.
 *
 * @see com.example.finalproject.DrawerBaseActivity
 *
 */

public class ShowImageActivity extends DrawerBaseActivity {

    ImageView image;
    BlurImageView bgImage;
    CardView imageDisplayCardView;
    TextView selImageTitle;
    TextView imageDescription;
    TextView hdURL;
    TextView dateDisplay;
    Button saveImageButton;
    String datePassed;
    String imgTitle;
    ProgressBar pb;
    private String imageURL;
    String HDimageURL;
    String imageDesc;
    String formattedDate;
    String mediaType;
    String videoID;
    Animation fadeInCardView;
    URL urll;

    Bitmap imageBitmap;

    ActivityShowImageBinding activityShowImageBinding;

    /**
     * On creation of this activity binding will be used to share the nav drawer and toolbar
     *
     * Intent passed from EnterDateActivity contains date of image to show
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_show_image);
        activityShowImageBinding = ActivityShowImageBinding.inflate(getLayoutInflater());
        allocateActivityTitle("IOTD");
        setContentView(activityShowImageBinding.getRoot());
        //set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText(R.string.aotd_page);
        ((TextView) headView.findViewById(R.id.activityVersion)).setText(R.string.aotd_version);

        //Receive date from DatePicker in previous Activity
        Intent receivedDate = getIntent();
        datePassed = receivedDate.getStringExtra("Date");
        Log.d("DatePickerPassedDate", datePassed);

        //Convert date formatting for display
        LocalDate date = LocalDate.parse(datePassed, DateTimeFormatter.ofPattern("yyyy-M-d"));
        formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, uuuu", Locale.CANADA));
        Log.d("Date", String.valueOf(date));
        Log.d("Date", String.valueOf(formattedDate));

        //Initialize progress bar, set progress and visibility
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(0);

        //Initiate display components
        selImageTitle = findViewById(R.id.imageTitle);
        image = findViewById(R.id.iotdImageDisplay);
        bgImage = findViewById(R.id.showImageBackground);
        imageDescription = findViewById(R.id.imageDescription);
        hdURL = findViewById(R.id.hdLink);
        dateDisplay = findViewById(R.id.dateDisplay);

        imageDisplayCardView = findViewById(R.id.imageDisplayCardView);
        imageDisplayCardView.setVisibility(View.INVISIBLE);

        //Call API Query
        IOTDQuery req = new IOTDQuery();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=sA4ZPV2ROeOvL7cSDa5ktjxKYa8VXTCbi2gDTfjF&date=" + datePassed);

        //button to initiate save image to database
        saveImageButton = findViewById(R.id.saveImage);
        //if button clicked
        saveImageButton.setOnClickListener(click -> {

            Intent sendImageInformation = new Intent(getBaseContext(), ShowSavedImageActivity.class);
            //put image information in intent object to start ShowSavedImageActivity
            sendImageInformation.putExtra("Title", imgTitle);
            sendImageInformation.putExtra("url", imageURL);
            sendImageInformation.putExtra("date", datePassed);
            sendImageInformation.putExtra("explanation", imageDesc);
            sendImageInformation.putExtra("HDurl", HDimageURL);

            startActivity(sendImageInformation);

        });
        saveImageButton.setVisibility(View.INVISIBLE);

    }

    /**
     * Asynctask used to query NASA IOTD API
     */
    class IOTDQuery extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... args) {
            try {
                //Connect to Website, parse JSON files for information
                URL url = new URL(args[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream input = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject imageData = new JSONObject(result);
                mediaType = imageData.getString("media_type");
                imageURL = imageData.getString("url");
                publishProgress(25);
                imgTitle = imageData.getString("title");
                publishProgress(50);
                imageDesc = imageData.getString("explanation");
                publishProgress(60);

                //if mediaType is a video get the HD url
                if (!"video".equals(mediaType)) {
                    HDimageURL = imageData.getString("hdurl");
                    publishProgress(70);
                    urll = new URL(imageURL);
                }
                else {
                    videoID = extractYTId(imageURL);
                    urll = new URL("http://img.youtube.com/vi/"+ videoID + "/mqdefault.jpg");
                }


                    InputStream is = urll.openConnection().getInputStream();
                    imageBitmap = BitmapFactory.decodeStream(is);
                    FileOutputStream outputStream = openFileOutput(imgTitle, Context.MODE_PRIVATE);
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);


            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(100);
            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... args) {
            super.onProgressUpdate(args);
            pb.setProgress(args[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            image.setImageBitmap(imageBitmap);
            //Set Background image dynamically, blur
            bgImage.setImageBitmap(imageBitmap);
            bgImage.setBlur(20);

            //Return API information for setting to display
            if (!"video".equals(mediaType)) {
                String urlReference = "<a href='" + HDimageURL + "'> " + getResources().getString(R.string.hd_url_link) + " </a>";
                hdURL.setText(Html.fromHtml(urlReference));
            }
            else {
                //Opens Video Link
                String urlReference = "<a href='" + imageURL + "'> " + getResources().getString(R.string.video_link) + " </a>";
                hdURL.setText(Html.fromHtml(urlReference));
                saveImageButton.setText(R.string.save_video);
            }

            selImageTitle.setText(imgTitle);
            imageDescription.setText(imageDesc);
            dateDisplay.setText(formattedDate);

            //allow user option to click to see HD image
            hdURL.setClickable(true);
            hdURL.setMovementMethod(LinkMovementMethod.getInstance());

            //show CardView once everything loads
            imageDisplayCardView.setVisibility(View.VISIBLE);
            fadeInCardView = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            imageDisplayCardView.startAnimation(fadeInCardView);

            //Remove progress bar
            pb.setVisibility(View.INVISIBLE);

            //Make save button visible once post has loaded
            saveImageButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method allows us to extract videoId from YouTube url
     * @param ytUrl the url to parse
     * @return a string of videoId
     */
    public static String extractYTId(String ytUrl) {

        //Regex for extracting the video ID from Youtube
        String vId = null;
        Pattern pattern = Pattern.compile(
                "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }
}