package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ShowImageActivity extends AppCompatActivity {

    ImageView image;
    TextView selImageTitle;
    String datePassed;
    String imgTitle;
    ProgressBar pb;
    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

//        activityDisplayImageBinding = ActivityEnterDateBinding.inflate(getLayoutInflater());
//        allocateActivityTitle("IOTD Image Display");
//        setContentView(activityDisplayImageBinding.getRoot());

        //Receive date from DatePicker in previous Activity
        Intent receivedDate = getIntent();
        datePassed = receivedDate.getStringExtra("Date");
        Log.d("DatePickerPassedDate: ", datePassed);

        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(0);

        selImageTitle = findViewById(R.id.imageTitle);
        image = findViewById(R.id.iotdImageDisplay);

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
                Log.d("ImageDisplayActivity: ", imageURL);



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
            Picasso.get().load(imageURL).into(image);
            selImageTitle.setText(imgTitle);
            //Remove progress bar
            pb.setVisibility(View.INVISIBLE);
        }
    }
}