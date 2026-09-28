package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

import java.util.Locale;

public class IngredientMatcher {
    public static String normalizeName(String name) {
        if (name == null) return "";
        String n = name.trim().toLowerCase(Locale.ROOT);
        n = n.replaceAll("[^a-z\\s]", "");
        n = n.trim();

        if(n.endsWith("ies") && n.length() > 3) {
            n = n.substring(0, n.length() - 3) + "y";
        } else if (n.endsWith("oes") && n.length() > 3) {
            n = n.substring(0, n.length() - 2);
        } else if ((n.endsWith("ches") || n.endsWith("shes") || n.endsWith("xes") || n.endsWith("ses")) && n.length() > 4) {
            n = n.substring(0, n.length() - 2);
        } else if (n.endsWith("s") && !n.endsWith("ss") && n.length() > 1) {
            n = n.substring(0, n.length() -1);
        }
        return n;
    }
    public static String unitFamily(String unit) {
        if (unit == null) return "count";
        String u = unit.trim().toLowerCase(Locale.ROOT);
        if (u.equals("g") || u.equals("kg") || u.equals("oz") || u.equals("lb")) {
            return "weight";
        }
        if (u.equals("ml") || u.equals("l") || u.equals("tsp") || u.equals("tbsp") || u.equals("cup")) {
            return "volume";
        }
        return "count";
    }
    public static double toCanonical(double qty, String unit) {
        if (unit == null) return qty;
        String u = unit.trim().toLowerCase(Locale.ROOT);
        if (u.equals("g") || u.equals("ml")) return qty;
        if (u.equals("kg") || u.equals("l")) return qty * 1000;
        if (u.equals("oz")) return qty * 28.3495;
        if (u.equals("lb")) return qty * 453.592;
        if (u.equals("tsp")) return qty * 5.0;
        if (u.equals("tbsp")) return qty * 15.0;
        if (u.equals("cup")) return qty *240.0;
        return qty;
    }

}
