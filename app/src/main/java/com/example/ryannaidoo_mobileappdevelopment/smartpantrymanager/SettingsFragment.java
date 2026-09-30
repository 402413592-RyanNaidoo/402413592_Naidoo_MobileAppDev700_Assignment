package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ryannaidoo_mobileappdevelopment.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    public static final String PREFS_NAME = "pantry_prefs";
    public static final String KEY_EXPIRY_ALERTS = "expiry_alerts_enabled";
    public static final String KEY_UNITS = "units_preference";
    public DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        dbHelper = new DatabaseHelper(requireContext());
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        Switch switchExpiry = view.findViewById(R.id.switch_expiry_alerts);
        RadioGroup radioUnits = view.findViewById(R.id.radio_units);
        View buttonDeleteAccount = view.findViewById(R.id.button_delete_account);
        View buttonDeletePantry = view.findViewById(R.id.button_delete_pantry);
        View buttonLogout = view.findViewById(R.id.button_logout);

        switchExpiry.setChecked(prefs.getBoolean(KEY_EXPIRY_ALERTS, true));
        String unitPrefs = prefs.getString(KEY_UNITS, "metric");

        if (unitPrefs.equals("imperial")) {
            radioUnits.check(R.id.radio_imperial);
        }
        else {
            radioUnits.check(R.id.radio_metric);
        }

        switchExpiry.setOnCheckedChangeListener((buttonView, isChecked) ->
                prefs.edit().putBoolean(KEY_EXPIRY_ALERTS, isChecked).apply());

        radioUnits.setOnCheckedChangeListener((group, checkedId) -> {
            String value = "metric";

                    if (checkedId == R.id.radio_imperial) {
                        value = "imperial";
                    }
                    prefs.edit().putString(KEY_UNITS, value).apply();
        });

        //delete pantry
        buttonDeletePantry.setOnClickListener(v -> {
            dbHelper.clearPantry();
            Toast.makeText(requireContext(), "Pantry list cleared", Toast.LENGTH_SHORT).show();
        });

        //delete account
        buttonDeleteAccount.setOnClickListener(v -> {
            String username = prefs.getString("logged_in_username", null);
            if (username != null) {
                dbHelper.deleteUser(username);
            }
            prefs.edit().remove("is_logged_in").remove("logged_in_username").apply();
            Toast.makeText(requireContext(), "Account deleted", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        //logout
        buttonLogout.setOnClickListener(v -> {
        });
        return view;


    }
}