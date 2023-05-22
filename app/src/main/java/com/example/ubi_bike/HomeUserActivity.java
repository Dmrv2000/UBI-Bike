package com.example.ubi_bike;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.*;

public class HomeUserActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    MapFragment mapFragment = new MapFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    StoreFragment storeFragment = new StoreFragment();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeUserActivity.this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.map);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
        });
    }


}