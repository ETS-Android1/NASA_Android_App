package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
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
            case R.id.news:
                startActivity(new Intent(this, NewsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.bookmark:
                startActivity(new Intent(this, BookMarkActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.Share:
                startActivity(new Intent(this, DashboardActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.close:
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
            case R.id.news:
                startActivity(new Intent(this, NewsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.bookmark:
                startActivity(new Intent(this, BookMarkActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.Share:
                startActivity(new Intent(this, DashboardActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.close:
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