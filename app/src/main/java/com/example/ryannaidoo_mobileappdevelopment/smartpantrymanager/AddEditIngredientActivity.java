package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ryannaidoo_mobileappdevelopment.R;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {
    public DatabaseHelper dbHelper;
    public EditText editName, editQuantity, editExpiry;
    public Spinner spinnerUnit;
    public Button buttonSave, buttonDelete;
    public long itemId = -1;
    public static final String[] UNITS = {"", "g", "kg", "ml", "l", "tsp", "tbsp", "cup", "oz", "lb"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);
        dbHelper = new DatabaseHelper(this);
        View buttonBack = findViewById(R.id.button_back);

        if (buttonBack != null) {
            buttonBack.setOnClickListener(v -> finish());
        }
        View buttonCancel = findViewById(R.id.button_cancel);
        if (buttonCancel != null) {
            buttonCancel.setOnClickListener(v -> finish());
        }
        editName = findViewById(R.id.edit_name);
        editQuantity = findViewById(R.id.edit_quantity);
        editExpiry = findViewById(R.id.edit_expiry);
        spinnerUnit = findViewById(R.id.spinner_unit);
        buttonSave = findViewById(R.id.button_save);
        buttonDelete = findViewById(R.id.button_delete);
        editName.requestFocus();

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, UNITS);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);

        itemId = getIntent().getLongExtra("item_id", -1);
        if (itemId != -1) {
            PantryItem existing = dbHelper.getPantryItem(itemId);
            if (existing != null) {
                editName.setText(existing.name);
                double q = existing.quantity;

                if (q == Math.floor(q)) {
                    editQuantity.setText(String.valueOf((long) q));
                } else {
                    editQuantity.setText(String.valueOf(q));
                }
                editExpiry.setText(existing.expiryDate);

                int unitIdx = 0;
                if (existing.unit != null) {
                    for (int i = 0; i < UNITS.length; i++) {
                        if (UNITS[i].equalsIgnoreCase(existing.unit)) {
                            unitIdx = i;
                            break;
                        }
                    }
                }
                spinnerUnit.setSelection(unitIdx);
                buttonDelete.setVisibility(View.VISIBLE);
            }
        }

        buttonSave.setOnClickListener(v -> saveItem());
        buttonDelete.setOnClickListener(v -> {
            dbHelper.deletePantryItem(itemId);
            finish();
        });
    }

    private void saveItem() {
    String name = editName.getText().toString().trim();
    String quantityText = editQuantity.getText().toString().trim();
    String unit = (String) spinnerUnit.getSelectedItem();
    String expiry = editExpiry.getText().toString().trim();

    if (name.isEmpty()) {
        editName.setError("Ingredient name is required");
        return;
    }

    double quantity;
    try {
        quantity = Double.parseDouble(quantityText);
        if (quantity <= 0) {
            editQuantity.setError("Quantity must be greater than zero");
            return;
        }
    } catch (NumberFormatException e) {
        editQuantity.setError("Enter a valid number");
        return;
    }

    PantryItem item = new PantryItem();
    item.id = itemId;
    item.name = name;
    item.quantity = quantity;
    item.unit = (unit == null || unit.isEmpty() ? null : unit);
    item.expiryDate = (expiry.isEmpty() ? null : expiry);

    if (itemId == -1) {
        dbHelper.addPantryItem(item);
        Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
    }
    else {
        dbHelper.updatePantryItem(item);
        Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
    }
    finish();
    }}




