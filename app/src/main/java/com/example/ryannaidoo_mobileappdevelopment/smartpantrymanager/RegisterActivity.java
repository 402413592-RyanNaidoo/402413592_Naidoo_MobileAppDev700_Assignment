package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ryannaidoo_mobileappdevelopment.R;

public class RegisterActivity extends AppCompatActivity {
    public DatabaseHelper dbHelper;
    public EditText editUsername, editPhone, editPassword;
    public Button buttonRegister;
    public TextView textLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DatabaseHelper(this);
        editUsername = findViewById(R.id.edit_username);
        editPhone = findViewById(R.id.edit_phone);
        editPassword = findViewById(R.id.edit_password);
        buttonRegister = findViewById(R.id.button_register);
        textLoginLink = findViewById(R.id.text_login_link);

    buttonRegister.setOnClickListener(v ->

    {
        String username = editUsername.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (username.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //check user available
        if (dbHelper.isUsernameTaken(username)) {
            Toast.makeText(RegisterActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
            return;
        }

        //insert user
        User user = new User(-1, username, phone, password);
        long id = dbHelper.addUser(user);

        if (id != -1) {
            //save session
            SharedPreferences prefs = getSharedPreferences(SettingsFragment.PREFS_NAME, MODE_PRIVATE);
            prefs.edit()
                    .putBoolean("is_logged_in", true)
                    .putString("logged_in_username", username)
                    .apply();
            Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(RegisterActivity.this, "Error creating account", Toast.LENGTH_SHORT).show();
        }
    });

    //link to login
    textLoginLink.setOnClickListener(v -> finish());
    }
}

