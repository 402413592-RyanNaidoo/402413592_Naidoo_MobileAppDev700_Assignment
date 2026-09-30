package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.ryannaidoo_mobileappdevelopment.R;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        View buttonBack = findViewById(R.id.button_back);

        if (buttonBack != null) {
            buttonBack.setOnClickListener(v -> finish());
        }

        long recipeId = getIntent().getLongExtra("recipe_id", -1);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Recipe recipe = dbHelper.getRecipeById(recipeId);
        TextView toolbarTitle = findViewById(R.id.text_toolbar_title);
        TextView nameView = findViewById(R.id.text_detail_name);
        TextView ingredientsView = findViewById(R.id.text_detail_ingredients);
        TextView stepsView = findViewById(R.id.text_detail_steps);

        if (recipe != null) {
            toolbarTitle.setText(recipe.name);
            nameView.setText(recipe.name);

            StringBuilder ingredientsText = new StringBuilder();
            List<RecipeIngredientRequirement> ingredients = recipe.ingredients;
            for (int i = 0; i < ingredients.size(); i++) {
                RecipeIngredientRequirement ing = ingredients.get(i);
                double q = ing.quantity;
                String qStr = (q == Math.floor(q)) ? String.valueOf((long) q) : String.valueOf(q);
                ingredientsText.append("- ").append(qStr);

                if (ing.unit != null && !ing.unit.isEmpty()) {
                    ingredientsText.append(" ").append(ing.unit);
                }
                ingredientsText.append(" ").append(ing.ingredientName).append("\n");
            }
            ingredientsView.setText(ingredientsText.toString().trim());
            stepsView.setText(recipe.steps);

            }
        }

    }

