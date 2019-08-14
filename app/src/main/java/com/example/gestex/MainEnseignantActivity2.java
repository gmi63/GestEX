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

import com.example.gestex.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainEnseignantActivity2 extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_enseignant2);
        mDrawerLayout=findViewById(R.id.drawer3);
        mToogle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        navigationView = findViewById(R.id.cm_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(navigationView);
        firebaseAuth = FirebaseAuth.getInstance();
        //ajouter default
        LxFragment fragment = new LxFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_cm,fragment).commit();
    }

    public void selectItemDrawer(MenuItem menuItem){

        Fragment fragment = null;
        Class fragmentClass = LxFragment.class;
        switch (menuItem.getItemId()) {


            case R.id.lxx2:
                fragmentClass = LxFragment.class;
                break;
            case R.id.Lx2:
                firebaseAuth.signOut();
                Intent i = new Intent(MainEnseignantActivity2.this,MainActivity.class);
                startActivity(i);
                finish();

                break;
            case R.id.cx2:
                fragmentClass = CxFragment.class;
                break;
            default:
                fragmentClass = LxFragment.class;
                break;
        }
        try{

            fragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_cm,fragment).commit();
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
}
