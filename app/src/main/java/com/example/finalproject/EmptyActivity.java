package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/**
 * The Empty Activity
 *
 * @author Aladah M + Will B
 * @version 1.00
 *
 * This activity is used by DetailsFragmentActivity as a parent
 * @see com.example.finalproject.DetailsFragment
 *

 *
 */
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