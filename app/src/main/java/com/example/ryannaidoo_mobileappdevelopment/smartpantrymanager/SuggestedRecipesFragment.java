package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ryannaidoo_mobileappdevelopment.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesFragment extends Fragment {
    public DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggested_recipes, container, false);
        dbHelper = new DatabaseHelper(requireContext());
        RecyclerView recyclerSuggested = view.findViewById(R.id.recycler_suggested);
        RecyclerView recyclerAlmost = view.findViewById(R.id.recycler_almost);
        TextView noSuggestions = view.findViewById(R.id.text_no_suggestions);
        TextView almostTitle = view.findViewById(R.id.text_almost_title);

        recyclerSuggested.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerAlmost.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<PantryItem> pantryItems = dbHelper.getAllPantryItems();
        List<Recipe> allRecipes = dbHelper.getAllRecipesWithIngredients();
        List<Recipe> suggested = new ArrayList<>();
        List<Recipe> almost = new ArrayList<>();

        //recipe matching
        for (int i = 0; i < allRecipes.size(); i++) {
            Recipe recipe = allRecipes.get(i);
            int missing = RecipeMatcher.countMissingIngredients(recipe, pantryItems);
            if (missing == 0) {
                suggested.add(recipe);
            }
            else if (missing == 1) {
                almost.add(recipe);
            }
        }

        if (suggested.isEmpty()) {
            noSuggestions.setVisibility(View.VISIBLE);
            recyclerSuggested.setVisibility(View.GONE);
        }
        else {
            noSuggestions.setVisibility(View.GONE);
            recyclerSuggested.setVisibility(View.VISIBLE);

        }

        recyclerSuggested.setAdapter(new RecipeAdapter(suggested, recipe -> openRecipeDetail(recipe.id), "You have everything you need"));

        if (almost.isEmpty()) {
            almostTitle.setVisibility(View.GONE);
            recyclerAlmost.setVisibility(View.GONE);
        }

        else {
            almostTitle.setVisibility(View.VISIBLE);
            recyclerAlmost.setVisibility(View.VISIBLE);
            recyclerAlmost.setAdapter(new RecipeAdapter(almost, recipe -> openRecipeDetail(recipe.id), "Missing 1 ingredient"));
        }
        return view;
    }

    private void openRecipeDetail(long recipeId) {
        Intent intent = new Intent(requireContext(), RecipeDetailActivity.class);
        intent.putExtra("recipe_id", recipeId);
        startActivity(intent);
    }
}