package com.example.finalproject;

import android.animation.ValueAnimator;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.finalproject.databinding.ActivityMarsWeatherBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The MarsWeatherActivity
 *
 * @author Aladah M + Will B
 * @version 1.00
 *
 * This activity pulls data from MarsWeatherQuery API
 * to display Mars weather to users
 *
 * This activity binds to DrawerBaseActivity for navigation drawer and toolbar.
 *
 * @see com.example.finalproject.DrawerBaseActivity
 *
 */

public class MarsWeatherActivity extends DrawerBaseActivity {

    TextView actTitle;
    TextView currentSol;
    TextView tempHigh;
    TextView tempLow;
    TextView currentDate;
    MaterialCardView weatherDisplayCardView;
    String weatherSol, weatherTempHigh, weatherTempLow, formattedDate;
    Animation fadeInTitle, fadeInCardView;

    ActivityMarsWeatherBinding activityMarsWeatherBinding;

    /**
     * On creation of this activity binding is used to share nav drawer and toolbar
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mars_weather);

        activityMarsWeatherBinding = ActivityMarsWeatherBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Mars Weather");
        setContentView(activityMarsWeatherBinding.getRoot());

        // set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText(R.string.mars_weather_page);
        ((TextView) headView.findViewById(R.id.activityVersion)).setText(BuildConfig.VERSION_NAME);
        //use variables to grab xml items by id
        actTitle = findViewById(R.id.activityTitle);
        currentSol = findViewById(R.id.solDisplay);
        tempHigh = findViewById(R.id.tempHighDisplay);
        tempLow = findViewById(R.id.tempLowDisplay);
        currentDate = findViewById(R.id.currentDateDisplay);
        weatherDisplayCardView = findViewById(R.id.weatherCardView);

        //Set initial visibility of CardView to invisible while it loads in
        weatherDisplayCardView.setVisibility(View.INVISIBLE);

        //Animate Title
        fadeInTitle = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        actTitle.startAnimation(fadeInTitle);


        //Run MarsWeatherQuery; connect with API
        MarsWeatherQuery req = new MarsWeatherQuery();
        req.execute("https://api.maas2.apollorion.com/");
    }

    /**
     * Asynctask used to query API
     */
    class MarsWeatherQuery extends AsyncTask<String, Integer, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... args) {
            try {
                //Establish Connection
                URL url = new URL(args[0]);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream input = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                //Parse results, create JSONObject
                String result = sb.toString();
                JSONObject weatherData = new JSONObject(result);

                //Get data from API
                weatherSol = weatherData.getString("sol");
                weatherTempHigh = weatherData.getString("max_temp");
                weatherTempLow = weatherData.getString("min_temp");
                String tempString = weatherData.getString("terrestrial_date");

                //Date conversion logic
                boolean curLanguage = Locale.getDefault().getISO3Language().equals(Locale.CANADA.getISO3Language());
                LocalDateTime date = LocalDateTime.parse(tempString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                if (curLanguage != true) {
                    formattedDate = date.format(DateTimeFormatter.ofPattern("d MMMM uuuu", Locale.CANADA_FRENCH));
                }else {
                    formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, uuuu", Locale.CANADA));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... args) {
            super.onProgressUpdate(args);
        }

        /**
         * upon completion of AsyncTask this method will execute
         * @param s
         */

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //set text fetched from API to TextViews
            tempHigh.setText(weatherTempHigh + "\u2103");
            tempLow.setText(weatherTempLow + "\u2103");
            currentDate.setText(formattedDate);

            //Animate Sol Date number
            startCountAnimation(weatherSol, currentSol);

            //Once all information loads, make CardView visible and fade in
            weatherDisplayCardView.setVisibility(View.VISIBLE);
            fadeInCardView = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            fadeInCardView.setStartOffset(500);
            weatherDisplayCardView.startAnimation(fadeInCardView);
        }
    }

    /**
     * This method programs animation into display of mars weather
     * @param curNum
     * @param updatingView
     */
    private void startCountAnimation(String curNum, TextView updatingView) {

        int num = Integer.parseInt(curNum);
        ValueAnimator animator = ValueAnimator.ofInt(num - 20, num);
        animator.setDuration(2500);
        animator.addUpdateListener(animation -> updatingView.setText(animation.getAnimatedValue().toString()));
        animator.start();
    }


}