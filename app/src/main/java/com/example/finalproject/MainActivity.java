package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import com.example.finalproject.databinding.ActivityMainBinding;


public class MainActivity extends DrawerBaseActivity {
    //variable to hold bindings
    ActivityMainBinding activityMainBinding;
    //initialize shared preferences
    SharedPreferences prefs = null;
    TextView greeting;



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
        ((TextView) headView.findViewById(R.id.activityTitle)).setText("Mars Weather");
        //get name from shared preferences
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("TypedText", " " );
        greeting = (TextView) findViewById(R.id.marsWeatherGreeting);
        String full = "Whats the weather today " + savedString + "?";
        greeting.setText(full);





    }

}