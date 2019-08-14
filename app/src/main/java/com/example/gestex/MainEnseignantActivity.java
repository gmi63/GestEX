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

public class MainEnseignantActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_enseignant);
        mDrawerLayout=findViewById(R.id.drawer2);
        navigationView = findViewById(R.id.nav);
        mToogle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        setupDrawerContent(navigationView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ajouter default
        GxFragment fragment = new GxFragment();
        firebaseAuth = FirebaseAuth.getInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_ens,fragment).commit();
    }
    public void selectItemDrawer(MenuItem menuItem){

        Fragment fragment = null;
        Class fragmentClass = GxFragment.class;
        switch (menuItem.getItemId()) {


            case R.id.gx1:
                fragmentClass = GxFragment.class;
                break;
            case  R.id.et:
                fragmentClass = ConsulterFragment.class;
                break;

            case  R.id.time:
                fragmentClass = TimeFragment.class;
                break;


            case R.id.Lx1:
                firebaseAuth.signOut();
                Intent i = new Intent(MainEnseignantActivity.this,MainActivity.class);
                startActivity(i);
                finish();

                break;
            default:
                fragmentClass = GxFragment.class;
                break;
        }
        try{

            fragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_ens,fragment).commit();
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
