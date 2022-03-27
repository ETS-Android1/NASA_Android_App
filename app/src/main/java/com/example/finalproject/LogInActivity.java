package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.databinding.ActivityDashboardBinding;
import com.example.finalproject.databinding.ActivityLogInBinding;
import com.google.android.material.navigation.NavigationView;

public class LogInActivity extends DrawerBaseActivity {

    ActivityLogInBinding activityLogInBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInBinding = ActivityLogInBinding.inflate(getLayoutInflater());
       allocateActivityTitle("LogIn");
      setContentView(activityLogInBinding.getRoot());

        //set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText("Log In");
    }
}