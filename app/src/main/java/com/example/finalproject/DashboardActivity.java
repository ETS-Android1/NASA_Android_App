package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.databinding.ActivityDashboardBinding;
import com.google.android.material.navigation.NavigationView;

/**
 *
 */
public class DashboardActivity extends DrawerBaseActivity  {
    //create the binding
    ActivityDashboardBinding activityDashBoardBinding;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashBoardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Dashboard");
        setContentView(activityDashBoardBinding.getRoot());
        //set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText("DashBoard");
    }
}