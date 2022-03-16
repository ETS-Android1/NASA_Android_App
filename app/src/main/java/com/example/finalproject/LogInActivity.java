package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproject.databinding.ActivityDashboardBinding;
import com.example.finalproject.databinding.ActivityLogInBinding;

public class LogInActivity extends DrawerBaseActivity {

    ActivityLogInBinding activityLogInBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInBinding = ActivityLogInBinding.inflate(getLayoutInflater());
       allocateActivityTitle("LogIn");
      setContentView(activityLogInBinding.getRoot());
    }
}