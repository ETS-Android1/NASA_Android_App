package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalproject.databinding.ActivityBookMarkBinding;
import com.example.finalproject.databinding.ActivityNewsBinding;

public class NewsActivity extends DrawerBaseActivity {

    ActivityNewsBinding activityNewsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNewsBinding = ActivityNewsBinding.inflate(getLayoutInflater());
        allocateActivityTitle("News");
        setContentView(activityNewsBinding.getRoot());

    }
}