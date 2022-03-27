package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
        ((TextView) headView.findViewById(R.id.activityTitle)).setText("Home Page");


    }

}