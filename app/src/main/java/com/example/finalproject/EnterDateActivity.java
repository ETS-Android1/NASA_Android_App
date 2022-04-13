package com.example.finalproject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.databinding.ActivityEnterDateBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.Date;

/**
 * The EnterDateActivity
 *
 * @author Aladah M + Will B
 * @version 1.00
 *
 * This activity includes a date picker
 * User can select a date + click the button
 * and app will show them the associated NASA image of the day
 *
 * @see com.example.finalproject.ShowImageActivity
 *
 * This activity binds to DrawerBaseActivity for navigation drawer and toolbar.
 *
 * @see com.example.finalproject.DrawerBaseActivity
 *
 */
public class EnterDateActivity extends DrawerBaseActivity {

    ActivityEnterDateBinding activityEnterDateBinding;

    private TextView greeting;
    SharedPreferences prefs = null;

    /**
     * on creation of this activity binding used to share nav drawer and toolbar
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting common nav drawer and toolbar
        activityEnterDateBinding = ActivityEnterDateBinding.inflate(getLayoutInflater());
        //use method declared in DrawerBaseActivity to set title in nav drawer
        allocateActivityTitle("IOTD Date Selector");
        setContentView(activityEnterDateBinding.getRoot());
        //set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText(R.string.date_picker_page);
        ((TextView) headView.findViewById(R.id.activityVersion)).setText(R.string.date_picker_version);
        //button to confirm selection of date from date picker
        Button dateSelectButton = findViewById(R.id.selectDate);

        //get name from shared preferences
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        //save name in variable
        String savedString = prefs.getString("TypedText", " ");
        //create custom string
        String full = getResources().getString(R.string.date_picker_button_text, savedString);
        //set button text to custom string
        dateSelectButton.setText(full);

        //Create date picking widget
        android.widget.DatePicker calendar;
        //set widget to date picker xml item
        calendar = findViewById(R.id.datePicker);

        //set Max date to current day
        calendar.setMaxDate(new Date().getTime());
        //when button clicked
        dateSelectButton.setOnClickListener(click -> {
            //format date selection
            String dateSelected = calendar.getYear() + "-" + (calendar.getMonth() + 1) + "-" + calendar.getDayOfMonth();
            //create intent object to open showImageActivity
            Intent sendDateInformation = new Intent(getBaseContext(), ShowImageActivity.class);
            //put date in intent obj
            sendDateInformation.putExtra("Date", dateSelected);
            //start activity with intent sent
            startActivity(sendDateInformation);
        });
    }
}