package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ryannaidoo_mobileappdevelopment.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check user
        SharedPreferences prefs = getSharedPreferences(SettingsFragment.PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
        if (!isLoggedIn){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        View navPantry = findViewById(R.id.nav_pantry);
        View navRecipes = findViewById(R.id.nav_recipes);
        View navSettings = findViewById(R.id.nav_settings);

        if (savedInstanceState == null) {
            loadFragment(new PantryListFragment());
        }
        navPantry.setOnClickListener(v -> loadFragment(new PantryListFragment()));
        navRecipes.setOnClickListener(v -> loadFragment(new SuggestedRecipesFragment()));
        navSettings.setOnClickListener(v -> loadFragment(new SettingsFragment()));

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}
