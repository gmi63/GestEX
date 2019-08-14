package com.example.gestex;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;

import com.example.gestex.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainAdminActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        mDrawerLayout=findViewById(R.id.drawer);
        firebaseAuth = FirebaseAuth.getInstance();
        mToogle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        navigationView = findViewById(R.id.layout_nav);

        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);

//ajouter default
        AjouterExamenFragment fragment = new AjouterExamenFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment,fragment).commit();
    }
    public void selectItemDrawer(MenuItem menuItem){

        Fragment fragment = null;
        Class fragmentClass = AjouterExamenFragment.class;
        switch (menuItem.getItemId()) {

            case R.id.ajex:
                fragmentClass = AjouterExamenFragment.class;
                break;
            case R.id.ajens:
                fragmentClass = AjouterEnseignantFragment.class;
                break;
            case R.id.cx1:
                fragmentClass = CxFragment.class;
                break;
            case R.id.env:
                fragmentClass = EnvoyerFragment.class;
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                Intent i = new Intent(MainAdminActivity.this,MainActivity.class);
                startActivity(i);
                finish();

                break;
            default:
                fragmentClass = AjouterExamenFragment.class;
                break;
        }
             try{

                 fragment = (Fragment) fragmentClass.newInstance();
             }
             catch (Exception e){
                 e.printStackTrace();
             }
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment,fragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawer(GravityCompat.START);



    }

    private void setupDrawerContent(NavigationView navigationView){

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                selectItemDrawer(menuItem);
                return true;
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;
        if (id == R.id.ajex) {

            AjouterExamenFragment ajx = new AjouterExamenFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment,ajx).commit();
            // Handle the camera action
        } else if (id == R.id.ajens) {



        }



        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
}
