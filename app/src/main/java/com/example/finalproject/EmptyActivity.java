package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass = getIntent().getExtras();

        DetailsFragment parent = new DetailsFragment();
        parent.setArguments( dataToPass );

        getSupportFragmentManager()

                .beginTransaction()
                .replace(R.id.fragmentLocation, parent)
                .commit();

    }

}