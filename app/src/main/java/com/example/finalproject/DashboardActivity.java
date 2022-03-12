package com.example.finalproject;

import android.os.Bundle;

import com.example.finalproject.databinding.ActivityDashboardBinding;

public class DashboardActivity extends DrawerBaseActivity  {
    //create the binding
    ActivityDashboardBinding activityDashBoardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashBoardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Dashboard");
        setContentView(activityDashBoardBinding.getRoot());
    }
}