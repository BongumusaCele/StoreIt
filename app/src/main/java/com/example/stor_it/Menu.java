package com.example.stor_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Menu extends AppCompatActivity {

    BottomNavigationView bottomnavigationView;
    NavigationView navigation;

    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuItemADD:
                Intent additem = new Intent(Menu.this, Additem.class);
                startActivity(additem);
                return true;

            case R.id.menuItemSetGoal:
                Intent setGoal = new Intent(Menu.this, SetGoal.class);
                startActivity(setGoal);
                return true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //getting element by ID
        bottomnavigationView = findViewById(R.id.NavigationView);
        //navigation = findViewById(R.menu.navigationView,menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, homeFragment).commit();


        bottomnavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, homeFragment).commit();
                        return true;

                    case R.id.person_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, profileFragment).commit();
                        return true;

                    case R.id.settings_item:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containerr, settingsFragment).commit();
                        return true;
                }

                return false;
            }
        });

        /*navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuItemADD:
                        Intent additem = new Intent(Menu.this, Additem.class);
                        startActivity(additem);
                        return true;

                    case R.id.menuItemSetGoal:
                        Intent setGoal = new Intent(Menu.this, SetGoal.class);
                        startActivity(setGoal);
                        return true;
                }
                return false;
            }
        });*/


    }
}