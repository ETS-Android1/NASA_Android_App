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
/**
 * The LogInActivity
 *
 * @author Aladah M + Will B
 * @version 1.00
 *
 * This is where the user can enter their name
 * which gets saved to shared preferences
 * If user has already visited the app
 * the log in page will display the name retrieved from shared preferences
 *
 * This activity binds to DrawerBaseActivity for navigation drawer and toolbar.
 *
 * @see com.example.finalproject.DrawerBaseActivity
 *
 */
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

    /**
     * On creation of this activity binding used to share nav drawer and toolbar
     * @param savedInstanceState
     */
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
        ((TextView) headView.findViewById(R.id.activityVersion)).setText(R.string.user_id_version);

        //get user input from edit text and save in variable
        textEntered = (EditText) findViewById(R.id.enterName);
        //retrieve name saved in shared preferences
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        savedName = prefs.getString("TypedText", " ");
        //set edit text to name saved in shared preferences
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
        //if addNameButton is clicked a snack bar will appear to welcome user
        addNameButton.setOnClickListener(v -> {
            previousSavedName = savedName;
            //when button clicked save text to shared preferences
            saveSharedPrefs(textEntered.getText().toString());
            //savedName = typeField.getText().toString();
            savedName = prefs.getString("TypedText", "");

            Snackbar.make(textEntered, getResources().getString(R.string.snackbar_message) + " " + savedName + "!", Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.snackbar_undo), new NameInputUndoListener()).show();

        });}

    /**
     * This method sends user name to EnterDateActivity with an intent
     */
    public void openDateActivity () {
            Intent intent = new Intent(this, EnterDateActivity.class);
            textEntered = (EditText) findViewById(R.id.enterName);
            intent.putExtra("textEntered", textEntered.getText().toString());
            int LAUNCH_ENTER_DATE_ACTIVITY = 1;
            startActivityForResult(intent, LAUNCH_ENTER_DATE_ACTIVITY);
        }

    /**
     * This method saves strings to shared prefererences
     * @param stringToSave
     */
        private void saveSharedPrefs (String stringToSave)
        {
            SharedPreferences prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("TypedText", stringToSave);
            editor.commit();
        }

    /**
     * if the user inputs a different name, it is saved to shared preferences
     */
    public class NameInputUndoListener implements View.OnClickListener {

        @Override
            public void onClick(View v) {
            textEntered.setText(previousSavedName);
            saveSharedPrefs(textEntered.getText().toString());
            savedName = prefs.getString("TypedText", "");
        }
        }
    }
