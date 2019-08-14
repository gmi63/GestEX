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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class sessionEnseignant extends AppCompatActivity {

    private EditText email;
    private  EditText password;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_enseignant);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        email = findViewById(R.id.username2);
        password = findViewById(R.id.pass2);

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
                                if (!Email.equals("admin@gmail.com")) {


                                    final String user_id = firebaseAuth.getUid();

                                    firestore.collection("cm").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                            if(task.getResult().exists()){
                                                Toast.makeText(sessionEnseignant.this, "Login cm ", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(sessionEnseignant.this, MainEnseignantActivity2.class);
                                                startActivity(i);
                                                finish();


                                            }
                                            else{



                                            firestore.collection("ens").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                    if(task.getResult().exists()){
                                                        Toast.makeText(sessionEnseignant.this, "Login ens ", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(sessionEnseignant.this, MainEnseignantActivity.class);
                                                        startActivity(i);
                                                        finish();


                                                    }

                                                }
                                            });









                                            }

                                        }
                                    });


















                                }
                            }
                            else Toast.makeText(sessionEnseignant.this, "Wrong Email or password", Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
        });


    }


}
