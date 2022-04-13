package com.example.finalproject;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    public void setContentView(View view) {
        //inflate drawer layout
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        //in frame of contentLayout
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        //toolbar.inflateMenu(R.menu.main_drawer_menu);

        navigationView = drawerLayout.findViewById(R.id.nav_view);
        //must implement onNavigationItemSelected method
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.menu_drawer_open, R.string.menu_drawer_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.helmet) {
            startActivity(new Intent(this, LogInActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.search) {
            startActivity(new Intent(this, EnterDateActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.redplanet) {
            startActivity(new Intent(this, MarsWeatherActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.rocketship) {
            startActivity(new Intent(this, ShowSavedImageActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.help) {
            HelpDialogSwitcher();
        } else if (id == R.id.moon) {
            finishAffinity();
        }


        return true;
    }

    //for toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer_menu, menu);
        return true;
    }

    //for toolbar icon options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.helmet) {
            startActivity(new Intent(this, LogInActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.search) {
            startActivity(new Intent(this, EnterDateActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.redplanet) {
            startActivity(new Intent(this, MarsWeatherActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.rocketship) {
            startActivity(new Intent(this, ShowSavedImageActivity.class));
            overridePendingTransition(0, 0);
        } else if (id == R.id.help) {
            HelpDialogSwitcher();
        } else if (id == R.id.moon) {
            finishAffinity();

        }
        return true;
    }


    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }

    public void HelpDialogSwitcher() {
        String activityName = this.getClass().getSimpleName();
        String activityNameDisplay = null;
        String alertMessage = null;

        switch (activityName) {
            case "MainActivity":
                alertMessage = getString(R.string.alert_main_message);
                activityNameDisplay = getString(R.string.alert_title_main);
                break;
            case "LogInActivity":
                alertMessage = getString(R.string.alert_log_in_message);
                activityNameDisplay = getString(R.string.alert_title_log_in);
                break;
            case "EnterDateActivity":
                alertMessage = getString(R.string.alert_date_picker_message);
                activityNameDisplay = getString(R.string.alert_title_picker);
                break;
            case "MarsWeatherActivity":
                alertMessage = getString(R.string.alert_mars_weather_message);
                activityNameDisplay = getString(R.string.alert_title_weather);
                break;
            case "ShowImageActivity":
                alertMessage = getString(R.string.alert_show_image_message);
                activityNameDisplay = getString(R.string.alert_title_image);
                break;
            case "ShowSavedImageActivity":
                alertMessage = getString(R.string.alert_saved_image_list_message);
                activityNameDisplay = getString(R.string.alert_title_image_list);
                break;
        }

        //Initialize AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //AlertDialog Title
        alertDialogBuilder.setTitle(activityNameDisplay);

        //AlertDialog Message
        alertDialogBuilder.setMessage(alertMessage);

        //Positive Selection Button
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.alert_acknowledge), (click, arg) -> {
            //no action; closes alert
        });

        alertDialogBuilder.create().show();

    }
}