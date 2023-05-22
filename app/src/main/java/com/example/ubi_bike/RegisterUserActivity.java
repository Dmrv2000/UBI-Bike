package com.example.ubi_bike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

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


    private EditText username,email,bike,pass,repass, birthdate;
    private DatePickerDialog datePickerDialog;
    //private DatePicker birthdate;
    private Button register;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        bike = findViewById(R.id.bike_id);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.repeat_password);
        register = findViewById(R.id.register_button);

        birthdate = findViewById(R.id.birthdate_picker);
        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePickerDialog = new DatePickerDialog(RegisterUserActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                birthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_value = String.valueOf(username.getText());
                String password_value = String.valueOf(pass.getText());
                String repassword_value = String.valueOf(repass.getText());
                String email_value = String.valueOf(email.getText());
                String bike_value = String.valueOf(bike.getText());
                String birthdate_value = String.valueOf(birthdate.getText());

                if(TextUtils.isEmpty(username_value) || TextUtils.isEmpty(password_value) || TextUtils.isEmpty(repassword_value) || TextUtils.isEmpty(email_value) || TextUtils.isEmpty(bike_value) || TextUtils.isEmpty(birthdate_value)) {
                    Toast.makeText(RegisterUserActivity.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(!TextUtils.equals(password_value, repassword_value)) {
                    Toast.makeText(RegisterUserActivity.this, "As passwords não combinam.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String date = birthdate.getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("username", username.getText().toString());
                user.put("email", email.getText().toString());
                user.put("bikeid", bike.getText().toString());
                user.put("birth", date);
                user.put("points","0");
                user.put("admin", "0");

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(RegisterUserActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterUserActivity.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                            FirebaseUser cUser = mAuth.getCurrentUser();
                            db.collection("users").document(cUser.getUid())
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("s", "DocumentSnapshot successfully written!");
                                            startActivity(new Intent(RegisterUserActivity.this, MainActivity.class));
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("f", "Error writing document", e);
                                        }
                                    });

                        } else {
                            Log.w("f", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterUserActivity.this, "Já existe uma conta com este email.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

    }
}