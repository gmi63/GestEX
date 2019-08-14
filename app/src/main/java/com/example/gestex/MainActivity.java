package com.example.gestex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    public void click(View v){
        Intent i = new Intent(MainActivity.this, sessionAdmin.class);
        startActivity(i);
        finish();
    }
    public void click2(View v){
        Intent i= new Intent(MainActivity.this, sessionEnseignant.class);
        startActivity(i);
finish();
    }
}
