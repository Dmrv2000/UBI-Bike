package com.example.ubi_bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_value = String.valueOf(username.getText());
                String password_value = String.valueOf(password.getText());

                if(TextUtils.isEmpty(username_value)) {
                    Toast.makeText(LoginActivity.this, "Introduza um username.", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(password_value)) {
                    Toast.makeText(LoginActivity.this, "Introduza uma password.", Toast.LENGTH_SHORT).show();
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(username_value).matches()) {
                    username.setError("Insira um username válido!");
                }

                /*mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Bem-vindo!.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeUserActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Autenticação falhada.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

                mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Bem-vindo!.", Toast.LENGTH_SHORT).show();
                        checkUserAccessLevel(authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Autenticação falhada.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void checkUserAccessLevel(String uid){
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID", uid);
        editor.apply();
        DocumentReference df = fStore.collection("users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(Objects.equals(documentSnapshot.getString("admin"), "0")){
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Log.d("Uname",documentSnapshot.getString("username"));
                    editor.putString("username", documentSnapshot.getString("username"));
                    editor.putString("email", documentSnapshot.getString("email"));
                    editor.putString("points", String.valueOf(documentSnapshot.getLong("points")));
                    editor.putString("bikeid", String.valueOf(documentSnapshot.getLong("bikeid")));
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));
                    finish();
                } else  {
                    startActivity(new Intent(getApplicationContext(), HomeAdminActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), HomeUserActivity.class));
            finish();
        }
    }
}