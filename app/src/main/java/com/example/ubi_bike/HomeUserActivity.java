package com.example.ubi_bike;


import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.*;

public class HomeUserActivity extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.map);
    }
    MapFragment mapFragment = new MapFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    StoreFragment storeFragment = new StoreFragment();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {

        if(item.getItemId() == R.id.map){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, mapFragment)
                    .commit();
            return true;
        }else if(item.getItemId() == R.id.profile){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, profileFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == R.id.store) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, storeFragment)
                    .commit();
            return true;
        }
        /*switch (item.getItemId()) {
            case R.id.map:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, mapFragment)
                        .commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, profileFragment)
                        .commit();
                return true;

            case R.id.store:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, storeFragment)
                        .commit();
                return true;
        }*/
        return false;
    }
}