package com.example.ubi_bike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_value = String.valueOf(username.getText());
                String password_value = String.valueOf(password.getText());

                if(username_value.equals(""))
                    username.setError("Please fill the username");
                if(password_value.equals(""))
                    password.setError("Please fill the password");

                if(password_value.equals("admin") && username_value.equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, HomeUserActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
}