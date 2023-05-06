package com.example.ubi_bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.common.value.qual.IntVal;

import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity {


    private EditText username,email,bike,pass,repass;
    private DatePicker birthdate;
    private Button register;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        bike = findViewById(R.id.bike_id);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.repeat_password);
        birthdate = findViewById(R.id.birthdate_picker);
        register = findViewById(R.id.register_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("")||email.getText().toString().equals("")||bike.getText().toString().equals("")||pass.getText().toString().equals("")||repass.getText().toString().equals("")||birthdate.getYear()>2005){
                    register.setError("Please fill all the camps");
                    return;
                }

                if(!pass.getText().toString().equals(repass.getText().toString())){
                    register.setError("passwords dont match");
                    return;
                }

                String date = birthdate.getDayOfMonth() + "/" + birthdate.getMonth() + "/" + birthdate.getYear();

                Map<String, Object> user = new HashMap<>();
                user.put("username", username.getText().toString());
                user.put("email", email.getText().toString());
                user.put("biekid", bike.getText().toString());
                user.put("birth", date);
                user.put("points","0");

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("s", "createUserWithEmail:success");
                            FirebaseUser cUser = mAuth.getCurrentUser();


                            db.collection("users").document(cUser.getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("s", "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("f", "Error writing document", e);
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("f", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterUserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

    }
}