package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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


public class ShowImageActivity extends DrawerBaseActivity {

    ImageView image;
    BlurImageView bgImage;
    CardView imageDisplayCardView;
    TextView selImageTitle;
    TextView imageDescription;
    TextView hdURL;
    TextView dateDisplay;
    String datePassed;
    String imgTitle;
    ProgressBar pb;
    private String imageURL;
    String HDimageURL;
    String imageDesc;

    Bitmap imageBitmap;

    ActivityShowImageBinding activityShowImageBinding;

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
        ((TextView) headView.findViewById(R.id.activityTitle)).setText("View Image");

        //Receive date from DatePicker in previous Activity
        Intent receivedDate = getIntent();
        datePassed = receivedDate.getStringExtra("Date");
        Log.d("DatePickerPassedDate: ", datePassed);

        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(0);

        selImageTitle = findViewById(R.id.imageTitle);
        image = findViewById(R.id.iotdImageDisplay);
        bgImage = findViewById(R.id.showImageBackground);
        imageDescription = findViewById(R.id.imageDescription);
        hdURL = findViewById(R.id.hdLink);
        dateDisplay = findViewById(R.id.dateDisplay);

        imageDisplayCardView = findViewById(R.id.imageDisplayCardView);
        imageDisplayCardView.setVisibility(View.INVISIBLE);


        IOTDQuery req = new IOTDQuery();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=sA4ZPV2ROeOvL7cSDa5ktjxKYa8VXTCbi2gDTfjF&date=" + datePassed);

        //button to initiate save image to database
        Button saveImageButton = findViewById(R.id.saveImage);
        saveImageButton.setOnClickListener(click -> {

            Intent sendImageInformation = new Intent(getBaseContext(), ShowSavedImageActivity.class);

            sendImageInformation.putExtra("Title", imgTitle);
            sendImageInformation.putExtra("url", imageURL);

            startActivity(sendImageInformation);

        });

    }

    class IOTDQuery extends AsyncTask<String, Integer, String> {

        //Bitmap currentImage = null;

        @Override
        protected String doInBackground(String... args) {
            try {
                //Connect to Website, parse JSON files for information
                URL url = new URL(args[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream input = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject imageData = new JSONObject(result);

                imageURL = imageData.getString("url");
                publishProgress(25);
                imgTitle = imageData.getString("title");
                publishProgress(50);
                HDimageURL = imageData.getString("hdurl");
                publishProgress(60);
                imageDesc = imageData.getString("explanation");
                publishProgress(70);
                Log.d("ImageDisplayActivity: ", imageURL);

                URL urll = new URL(imageURL);
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
            bgImage.setImageBitmap(imageBitmap);
            bgImage.setBlur(20);
            selImageTitle.setText(imgTitle);
            imageDescription.setText(imageDesc);
            dateDisplay.setText(datePassed);
            //allow user option to click to see HD image
            hdURL.setClickable(true);
            hdURL.setMovementMethod(LinkMovementMethod.getInstance());
            String urlReference = "<a href='" + HDimageURL + "'> " + getResources().getString(R.string.hd_url_link) + " </a>";
            hdURL.setText(Html.fromHtml(urlReference));

            //show CardView once everything loads
            imageDisplayCardView.setVisibility(View.VISIBLE);

            //Remove progress bar
            pb.setVisibility(View.INVISIBLE);


        }
    }
}