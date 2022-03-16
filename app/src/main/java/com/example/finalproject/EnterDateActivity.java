package com.example.finalproject;



import android.os.Bundle;

import com.example.finalproject.databinding.ActivityEnterDateBinding;

public class EnterDateActivity extends DrawerBaseActivity {

    ActivityEnterDateBinding activityEnterDateBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting common nav drawer and toolbar
        activityEnterDateBinding = ActivityEnterDateBinding.inflate(getLayoutInflater());
        allocateActivityTitle("News");
        setContentView(activityEnterDateBinding.getRoot());

    }
}