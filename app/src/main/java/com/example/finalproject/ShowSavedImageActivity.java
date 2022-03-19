package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.finalproject.databinding.ActivityShowImageBinding;
import com.example.finalproject.databinding.ActivityShowSavedImageBinding;

public class ShowSavedImageActivity extends DrawerBaseActivity {
    String imageTitle;
    TextView test;

    ActivityShowSavedImageBinding activityShowSavedImageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShowSavedImageBinding = ActivityShowSavedImageBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Saved Images");
        setContentView(activityShowSavedImageBinding.getRoot());
        //setContentView(R.layout.activity_show_saved_image);

        //Receive date from DatePicker in previous Activity
        Intent receivedImageTitle = getIntent();
        //datePassed = receivedDate.getStringExtra("Date");
        imageTitle = receivedImageTitle.getStringExtra("Title");
        test = (TextView) findViewById(R.id.imageTitleSaved);
        test.setText(imageTitle);
    }
}