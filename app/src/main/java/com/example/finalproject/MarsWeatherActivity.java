package com.example.finalproject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MarsWeatherActivity extends AppCompatActivity {

    TextView currentSol;
    TextView tempHigh;
    TextView tempLow;
    TextView currentDate;
    MaterialCardView weatherDisplayCardView;
    String weatherSol, weatherTempHigh, weatherTempLow, formattedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_weather);

        //set title of nav drawer to activity name
//        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        View headView = navigationView.getHeaderView(0);
//        ((TextView) headView.findViewById(R.id.activityTitle)).setText("Mars Weather");

        currentSol = findViewById(R.id.solDisplay);
        tempHigh = findViewById(R.id.tempHighDisplay);
        tempLow = findViewById(R.id.tempLowDisplay);
        currentDate = findViewById(R.id.currentDateDisplay);
        weatherDisplayCardView = findViewById(R.id.weatherCardView);

        MarsWeatherQuery req = new MarsWeatherQuery();
        req.execute("https://api.maas2.apollorion.com/");

    }

    class MarsWeatherQuery extends AsyncTask<String, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... args) {
            try {
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
                JSONObject weatherData = new JSONObject(result);

                weatherSol = weatherData.getString("sol");
                weatherTempHigh = weatherData.getString("max_temp");
                weatherTempLow = weatherData.getString("min_temp");
                String tempString = weatherData.getString("terrestrial_date");

                LocalDateTime date = LocalDateTime.parse(tempString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, uuuu", Locale.CANADA));
                Log.d("Date", String.valueOf(date));
                Log.d("Date", String.valueOf(formattedDate));



            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... args) {
            super.onProgressUpdate(args);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            currentSol.setText(weatherSol);
            tempHigh.setText(weatherTempHigh + "\u2103");
            tempLow.setText(weatherTempLow + "\u2103");

            currentDate.setText(formattedDate);
        }
    }


}