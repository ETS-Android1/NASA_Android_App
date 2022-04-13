package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.finalproject.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Random;

/**
 * The first activity
 *
 * @author Aladah M + Will B
 * @version 1.00
 *
 * This is the first activity a user sees when opening application.
 * An random greeting pops up with the users name
 * If the user has never opened the app, they will be directed to the log-in activity.
 * Login activity will allow user to enter their name
 * @see com.example.finalproject.LogInActivity
 *
 * This activity binds to DrawerBaseActivity for navigation drawer and toolbar.
 *
 * @see com.example.finalproject.DrawerBaseActivity
 *
 */

public class MainActivity extends DrawerBaseActivity {
    //variable to hold bindings
    ActivityMainBinding activityMainBinding;
    //initialize shared preferences
    SharedPreferences prefs = null;
    TextView greeting;
    CardView cd;
    String full;
    Animation slideGreeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         // inflates the common navbar and toolbar using binding
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Main");
        setContentView(activityMainBinding.getRoot());
        //set title and version # in nav drawer header
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText(getResources().getString(R.string.landing_page));
        ((TextView) headView.findViewById(R.id.activityVersion)).setText(R.string.landing_page_version);

         //get name of user from shared preferences
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("TypedText", " " );
        // Initialize greeting to text view on landing page
        greeting = (TextView) findViewById(R.id.marsWeatherGreeting);
        //If user has not previously entered their name
        if (savedString.trim().isEmpty()) {
            //alerts user to enter their name
            full = getString(R.string.no_information_greeting);
        }
        //Else, randomly selects from 3 greetings
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
        //set text view on main page to greeting and users name
        greeting.setText(full);
        //set variable to CardView
        cd = findViewById(R.id.main_activity_card);
        //set variable to slide up
        slideGreeting = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        //off set timing of animation
        slideGreeting.setStartOffset(250);
        //set card view variable to animate
        cd.startAnimation(slideGreeting);

    }

}