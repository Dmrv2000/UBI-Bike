package com.example.ubi_bike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeAdminActivity extends AppCompatActivity {
    AchivementsFragmentAdmin achievementsFragment = new AchivementsFragmentAdmin();

    TextFragment textFragment = new TextFragment();

    StoreFragmentAdmin storeFragmentAdmin = new StoreFragmentAdmin();
    UserFragmentAdmin userFragmentAdmin = new UserFragmentAdmin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, textFragment)
                .commit();

        // Write a message to the database
        FirebaseFirestore database = FirebaseFirestore.getInstance();
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("Página Inicial")) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, textFragment)
                    .commit();
        } else if (item.getTitle().equals("Loja")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, storeFragmentAdmin)
                    .commit();
        } else if (item.getTitle().equals("Achievements")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, achievementsFragment)
                    .commit();
        }else if (item.getTitle().equals("Pedidos")){

        }else if (item.getTitle().equals("Utilizadores")){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, userFragmentAdmin)
                    .commit();
        }else if (item.getTitle().equals("Terminar Sessão")){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeAdminActivity.this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}