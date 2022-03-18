package com.example.finalproject;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.databinding.ActivityEnterDateBinding;

public class EnterDateActivity extends DrawerBaseActivity {

    ActivityEnterDateBinding activityEnterDateBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting common nav drawer and toolbar
        activityEnterDateBinding = ActivityEnterDateBinding.inflate(getLayoutInflater());
        allocateActivityTitle("IOTD Date Selector");
        setContentView(activityEnterDateBinding.getRoot());

        //Create date picking widget
        android.widget.DatePicker calendar;
        calendar = findViewById(R.id.datePicker);

        Button dateSelectButton = findViewById(R.id.selectDate);
    dateSelectButton.setOnClickListener(click -> {
        String dateSelected = calendar.getYear() + "-" + (calendar.getMonth() + 1) + "-" + calendar.getDayOfMonth();

        Intent sendDateInformation = new Intent(getBaseContext(), ShowImageActivity.class);
        sendDateInformation.putExtra("Date", dateSelected);
        startActivity(sendDateInformation);
    });
    }
}