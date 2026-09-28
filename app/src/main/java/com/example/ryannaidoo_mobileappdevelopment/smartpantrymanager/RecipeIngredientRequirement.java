package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

public class RecipeIngredientRequirement {
    public long id;
    public long recipeId;
    public String ingredientName;
    public double quantity;
    public String unit;
    public RecipeIngredientRequirement() {

    }

    public RecipeIngredientRequirement(long id, long recipeId, String ingredientName, double quantity, String unit) {
        this.id = id;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
    }
    public long getId() {
        return id;
    }
    public long getRecipeID() {
        return recipeId;
    }
    public String getIngredientname() {
        return ingredientName;
    }
    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }


}
