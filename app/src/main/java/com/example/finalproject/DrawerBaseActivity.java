package com.example.finalproject;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

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

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        //must implement onNavigationItemSelected method
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.menu_drawer_open, R.string.menu_drawer_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.helmet:
                startActivity(new Intent(this, LogInActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.search:
                startActivity(new Intent(this, EnterDateActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.redplanet:
                startActivity(new Intent(this, MarsWeatherActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.rocketship:
                startActivity(new Intent(this, ShowSavedImageActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.moon:
                finishAffinity();
        }

        return false;
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
        switch (item.getItemId()) {
            case R.id.helmet:
                startActivity(new Intent(this, LogInActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.search:
                startActivity(new Intent(this, EnterDateActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.redplanet:
                startActivity(new Intent(this, MarsWeatherActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.rocketship:
                startActivity(new Intent(this, ShowSavedImageActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.moon:
                finishAffinity();
        }
        return true;
        //return false;
    }


    protected void allocateActivityTitle(String titleString) {
        if(getSupportActionBar() !=null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}