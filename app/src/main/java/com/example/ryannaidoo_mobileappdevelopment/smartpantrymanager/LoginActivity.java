package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import com.example.ryannaidoo_mobileappdevelopment.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    public DatabaseHelper dbHelper;
    public EditText editUsername, editPassword;
    public Button buttonLogin;
    public TextView textRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DatabaseHelper(this);
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        buttonLogin = findViewById(R.id.button_login);
        textRegisterLink = findViewById(R.id.text_register_link);

        buttonLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            //verify login
            boolean valid = dbHelper.checkUserLogin(username, password);
            if (valid) {
                //session
                SharedPreferences prefs = getSharedPreferences(SettingsFragment.PREFS_NAME, MODE_PRIVATE);
                prefs.edit()
                        .putBoolean("is_logged_in", true)
                        .putString("logged_in_username", username)
                        .apply();
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        //registration page
        textRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
