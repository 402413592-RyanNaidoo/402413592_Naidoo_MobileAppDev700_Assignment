package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public long id;
    public String name;
    public String steps;
    public List<RecipeIngredientRequirement> ingredients = new ArrayList<>();
    public Recipe() {}
    public Recipe(long id, String name, String steps) {
        this.id = id;
        this.name = name;
        this.steps = steps;
    }

    public long getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSteps() {
        return steps;
    }

    public List<RecipeIngredientRequirement> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<RecipeIngredientRequirement> ingredients) {
        this.ingredients = ingredients;
    }


}
