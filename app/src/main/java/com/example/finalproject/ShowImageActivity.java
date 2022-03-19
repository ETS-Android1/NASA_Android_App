package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
//import com.squareup.picasso.Picasso;
import com.example.finalproject.databinding.ActivityShowImageBinding;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ShowImageActivity extends DrawerBaseActivity {

    ImageView image;
    TextView selImageTitle;
    TextView imageDescription;
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

        //Receive date from DatePicker in previous Activity
        Intent receivedDate = getIntent();
        datePassed = receivedDate.getStringExtra("Date");
        Log.d("DatePickerPassedDate: ", datePassed);

        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(0);

        selImageTitle = findViewById(R.id.imageTitle);
        image = findViewById(R.id.iotdImageDisplay);
        imageDescription = findViewById(R.id.imageDescription);


        IOTDQuery req = new IOTDQuery();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=sA4ZPV2ROeOvL7cSDa5ktjxKYa8VXTCbi2gDTfjF&date=" + datePassed);
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
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);



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
            //Populate fields; display image using Picasso library
            //Picasso.get().load(imageURL).into(image);
            image.setImageBitmap(imageBitmap);
            selImageTitle.setText(imgTitle);
            imageDescription.setText(imageDesc);
            //Remove progress bar
           pb.setVisibility(View.INVISIBLE);
        }
    }
}