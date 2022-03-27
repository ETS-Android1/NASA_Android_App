package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.finalproject.databinding.ActivityLogInBinding;
import com.google.android.material.navigation.NavigationView;

public class LogInActivity extends DrawerBaseActivity {
    //to extend common nav drawer and toolbar
    ActivityLogInBinding activityLogInBinding;
    //edit text for user to enter name
    private EditText textEntered;
    //initialize shared preferences
    SharedPreferences prefs = null;
    //initialize button to add name to shared preferences
    Button addNameButton;
    String savedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInBinding = ActivityLogInBinding.inflate(getLayoutInflater());
        allocateActivityTitle("LogIn");
        setContentView(activityLogInBinding.getRoot());

        //set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText("Log In");

        //get edit text
        textEntered = (EditText) findViewById(R.id.enterName);
        //
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        savedName = prefs.getString("TypedText", " ");
        EditText typeField = findViewById(R.id.enterName);
        typeField.setText(savedName);

        addNameButton = (Button) findViewById(R.id.enterNameButton);
        addNameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //when button clicked save text to shared preferences
                saveSharedPrefs(typeField.getText().toString());
                //make toast to say save successful
                Toast.makeText(LogInActivity.this, "Welcome "+savedName +". Please explore!", Toast.LENGTH_LONG).show();

            }
        });}

        public void openDateActivity () {
            Intent intent = new Intent(this, EnterDateActivity.class);
            textEntered = (EditText) findViewById(R.id.enterName);
            intent.putExtra("textEntered", textEntered.getText().toString());
            int LAUNCH_ENTER_DATE_ACTIVITY = 1;
            startActivityForResult(intent, LAUNCH_ENTER_DATE_ACTIVITY);
        }

        private void saveSharedPrefs (String stringToSave)
        {
            SharedPreferences prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("TypedText", textEntered.getText().toString());
            editor.commit();
        }
    }
