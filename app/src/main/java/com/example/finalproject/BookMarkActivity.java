package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproject.databinding.ActivityBookMarkBinding;

public class BookMarkActivity extends DrawerBaseActivity {

    ActivityBookMarkBinding activityBookMarkBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBookMarkBinding = ActivityBookMarkBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Bookmark");
        setContentView(activityBookMarkBinding.getRoot());
    }
}