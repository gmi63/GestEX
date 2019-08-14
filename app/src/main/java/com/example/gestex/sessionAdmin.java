package com.example.gestex;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sessionAdmin extends AppCompatActivity {

    private EditText email;
    private  EditText password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_admin);

        firebaseAuth = FirebaseAuth.getInstance();


        email = findViewById(R.id.username1);
        password = findViewById(R.id.pass1);

        Button button = findViewById(R.id.login2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Email = email.getText().toString();
                String Password = password.getText().toString();


                if(!TextUtils.isEmpty(Email)&&!TextUtils.isEmpty(Password)){

                    firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                if (Email.equals("admin@gmail.com")) {

                                    Toast.makeText(sessionAdmin.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(sessionAdmin.this, MainAdminActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                            else Toast.makeText(sessionAdmin.this, "Wrong Email or password", Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
        });


    }


}
