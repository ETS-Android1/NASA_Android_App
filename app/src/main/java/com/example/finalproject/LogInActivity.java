package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.finalproject.databinding.ActivityLogInBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class LogInActivity extends DrawerBaseActivity {
    //to extend common nav drawer and toolbar
    ActivityLogInBinding activityLogInBinding;
    //edit text for user to enter name
    private EditText textEntered;
    //initialize shared preferences
    SharedPreferences prefs = null;
    //initialize button to add name to shared preferences
    Button addNameButton;
    String savedName, previousSavedName;
    TextView title;
    CardView cd;
    Animation slideCardDown, fadeInTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInBinding = ActivityLogInBinding.inflate(getLayoutInflater());
        allocateActivityTitle("LogIn");
        setContentView(activityLogInBinding.getRoot());

        //set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText(R.string.user_id_page);

        //get edit text
        textEntered = (EditText) findViewById(R.id.enterName);
        //
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        savedName = prefs.getString("TypedText", " ");

        textEntered.setText(savedName);

        //Animate title TextView
        title = findViewById(R.id.login_title);

        fadeInTitle = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        fadeInTitle.setStartOffset(100);
        title.startAnimation(fadeInTitle);

        //Animate CardView
        cd = findViewById(R.id.login_card);

        slideCardDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        slideCardDown.setStartOffset(500);
        cd.startAnimation(slideCardDown);

        addNameButton = (Button) findViewById(R.id.enterNameButton);
        addNameButton.setOnClickListener(v -> {
            previousSavedName = savedName;
            //when button clicked save text to shared preferences
            saveSharedPrefs(textEntered.getText().toString());
            //savedName = typeField.getText().toString();
            savedName = prefs.getString("TypedText", "");
            //make toast to say save successful
//                Toast.makeText(LogInActivity.this, "Welcome"+savedName +"!", Toast.LENGTH_LONG).show();
//                openDateActivity();
            //Create SnackBar to allow user to see confirmation of changes, as well as undo and re-enter their name
            Snackbar.make(textEntered, getResources().getString(R.string.snackbar_message) + " " + savedName + "!", Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.snackbar_undo), new NameInputUndoListener()).show();

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
            editor.putString("TypedText", stringToSave);
            editor.commit();
        }

        public class NameInputUndoListener implements View.OnClickListener {

        @Override
            public void onClick(View v) {
            textEntered.setText(previousSavedName);
            saveSharedPrefs(textEntered.getText().toString());
            savedName = prefs.getString("TypedText", "");
        }
        }
    }
