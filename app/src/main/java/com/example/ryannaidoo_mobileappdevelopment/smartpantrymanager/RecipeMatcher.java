package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import java.util.List;

public class RecipeMatcher {
    public static int countMissingIngredients(Recipe recipe, List<PantryItem> pantryItems) {
        int missing = 0;

        for (int i = 0; i < recipe.ingredients.size(); i++) {
            RecipeIngredientRequirement req = recipe.ingredients.get(i);
            String reqNorm = IngredientMatcher.normalizeName(req.ingredientName);
            String reqFamily = IngredientMatcher.unitFamily(req.unit);
            double reqAmount = IngredientMatcher.toCanonical(req.quantity, req.unit);
            double totalInPantry = 0.0;
            boolean foundNameMatch = false;

            for (int j = 0; j < pantryItems.size(); j++) {
                PantryItem item = pantryItems.get(j);
                String itemNorm = IngredientMatcher.normalizeName(item.name);

                if (reqNorm.equals(itemNorm)) {
                    foundNameMatch = true;
                    String itemFamily = IngredientMatcher.unitFamily(item.unit);
                    if (reqFamily.equals(itemFamily)) {
                        totalInPantry += IngredientMatcher.toCanonical(item.quantity, item.unit);
                    }

                }
            }

            if (!foundNameMatch || totalInPantry < reqAmount) {
                missing++;
            }
        }

        return missing;
    }

    public static boolean matchesStrictly(Recipe recipe, List<PantryItem> pantryItems) {
        return countMissingIngredients(recipe, pantryItems) == 0;

    }
}




