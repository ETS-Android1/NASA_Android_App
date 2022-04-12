package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends DrawerBaseActivity {
    //variable to hold bindings
    ActivityMainBinding activityMainBinding;
    //initialize shared preferences
    SharedPreferences prefs = null;
    TextView greeting;
    String full;
    ArrayList<String> greetingList = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //inflate the common navbar
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Main");
        setContentView(activityMainBinding.getRoot());
        //set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText(getResources().getString(R.string.landing_page));
        //get name from shared preferences
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("TypedText", " " );

        //Create Greeting
        //Checks to see if savedString is empty
        //If Empty, directs user to go to log in
        //Else, randomly selects from array of greetings
        greeting = (TextView) findViewById(R.id.marsWeatherGreeting);
        if (savedString.trim().isEmpty()) {
            full = "Please go to the identification page to personalize your experience!";
        }
        else {
            Random rand = new Random();
            int randGreet = rand.nextInt(2);

            if (randGreet == 0) {
                full = getResources().getString(R.string.greet1, savedString);
            } else if (randGreet == 1) {
                full = getResources().getString(R.string.greet2, savedString);
            } else {
                full = getResources().getString(R.string.greet3, savedString);
            }
        }
        greeting.setText(full);





    }

}