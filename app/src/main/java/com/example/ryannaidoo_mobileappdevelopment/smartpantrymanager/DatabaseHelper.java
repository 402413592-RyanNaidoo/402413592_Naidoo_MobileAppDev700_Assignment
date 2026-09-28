package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper{

    //database setup
    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "username";
    public static final String COL_USER_PHONE = "phone";
    public static final String COL_USER_PASS = "password";
    public static final String TABLE_PANTRY = "pantry_items";
    public static final String COL_PANTRY_ID = "id";
    public static final String COL_PANTRY_NAME = "name";
    public static final String COL_PANTRY_QUANTITY = "quantity";
    public static final String COL_PANTRY_UNIT = "unit";
    public static final String COL_PANTRY_EXPIRY = "expiry_date";
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "id";
    public static final String COL_RECIPE_NAME = "name";
    public static final String COL_RECIPE_STEPS = "steps";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COL_RI_ID = "id";
    public static final String COL_RI_RECIPE_ID = "recipe_id";
    public static final String COL_RI_NAME = "ingredient_name";
    public static final String COL_RI_QUANTITY = "quantity";
    public static final String COL_RI_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        //user table creation
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_USER_NAME + " TEXT UNIQUE NOT NULL, " + COL_USER_PHONE + " TEXT, " + COL_USER_PASS + " TEXT NOT NULL)");
        //pantry table creation
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " (" + COL_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_PANTRY_NAME + " TEXT NOT NULL, " + COL_PANTRY_QUANTITY + " REAL NOT NULL, " + COL_PANTRY_UNIT + " TEXT, " + COL_PANTRY_EXPIRY + " TEXT)");
        //recipe table creation
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (" + COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_RECIPE_NAME + " TEXT NOT NULL, " + COL_RECIPE_STEPS + " TEXT)");
        //recipe ingredients table creation
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" + COL_RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_RI_RECIPE_ID + " INTEGER NOT NULL, " + COL_RI_NAME + " TEXT NOT NULL, " + COL_RI_QUANTITY + " REAL NOT NULL, " + COL_RI_UNIT + " TEXT, " + "FOREIGN KEY(" + COL_RI_RECIPE_ID + ") REFERENCES " + TABLE_RECIPES + "(" + COL_RECIPE_ID + "))");

        seedRecipes(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        onCreate(db);
    }

    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PANTRY_NAME, item.name);
        values.put(COL_PANTRY_QUANTITY, item.quantity);
        values.put(COL_PANTRY_UNIT, item.unit);
        values.put(COL_PANTRY_EXPIRY, item.expiryDate);
        long id = db.insert(TABLE_PANTRY, null, values);
        db.close();
        return id;
    }

    public void updatePantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PANTRY_NAME, item.name);
        values.put(COL_PANTRY_QUANTITY, item.quantity);
        values.put(COL_PANTRY_UNIT, item.unit);
        values.put(COL_PANTRY_EXPIRY, item.expiryDate);
        db.update(TABLE_PANTRY, values, COL_PANTRY_ID + "=?", new String[]{String.valueOf(item.id)});
        db.close();

    }

    public void deletePantryItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PANTRY, COL_PANTRY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();

    }

    public void clearPantry() {
    SQLiteDatabase db = getWritableDatabase();
    db.delete(TABLE_PANTRY, null, null);
    db.close();

    }

    //user db operations
    public long addUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, user.username);
        values.put(COL_USER_PHONE, user.phone);
        values.put(COL_USER_PASS, user.password);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public boolean checkUserLogin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_USERS, null, COL_USER_NAME + "=? AND " + COL_USER_PASS + "=?",
                new String[]{username, password}, null, null, null);
        boolean ok = (c.getCount() > 0);
        c.close();
        db.close();
        return ok;
    }

    public boolean isUsernameTaken(String username){
        SQLiteDatabase db  = getReadableDatabase();
        Cursor c = db.query(TABLE_USERS, null, COL_USER_NAME + "=?",
                new String[]{username}, null, null, null);
        boolean taken = (c.getCount() > 0);
        c.close();
        db.close();
        return taken;
    }

    public void deleteUser(String username){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, COL_USER_NAME + "=?", new String[]{username});
        db.close();
    }

    public PantryItem getPantryItem(long id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_PANTRY, null, COL_PANTRY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        PantryItem item = null;
        if (c.moveToFirst()) {
            item = new PantryItem();
            item.id = c.getLong(c.getColumnIndexOrThrow(COL_PANTRY_ID));
            item.name = c.getString(c.getColumnIndexOrThrow(COL_PANTRY_NAME));
            item.quantity = c.getDouble(c.getColumnIndexOrThrow(COL_PANTRY_QUANTITY));
            item.unit = c.getString(c.getColumnIndexOrThrow(COL_PANTRY_UNIT));
            item.expiryDate = c.getString(c.getColumnIndexOrThrow(COL_PANTRY_EXPIRY));
        }
        c.close();
        db.close();
        return item;
    }

    public List<PantryItem> getAllPantryItems(){
        List<PantryItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_PANTRY, null, null, null, null, null, COL_PANTRY_NAME + " ASC");
        while (c.moveToNext()) {
            PantryItem item = new PantryItem();
            item.id = c.getLong(c.getColumnIndexOrThrow(COL_PANTRY_ID));
            item.name = c.getString(c.getColumnIndexOrThrow(COL_PANTRY_NAME));
            item.quantity = c.getDouble(c.getColumnIndexOrThrow(COL_PANTRY_QUANTITY));
            item.unit = c.getString(c.getColumnIndexOrThrow(COL_PANTRY_UNIT));
            item.expiryDate = c.getString(c.getColumnIndexOrThrow(COL_PANTRY_EXPIRY));
            list.add(item);

        }
        c.close();
        db.close();
        return list;
}

    public List<Recipe> getAllRecipesWithIngredients(){
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLE_RECIPES, null, null, null, null, null, COL_RECIPE_NAME + " ASC");
        while (c.moveToNext()) {
            Recipe recipe = new Recipe();
            recipe.id = c.getLong(c.getColumnIndexOrThrow(COL_RECIPE_ID));
            recipe.name = c.getString(c.getColumnIndexOrThrow(COL_RECIPE_NAME));
            recipe.steps = c.getString(c.getColumnIndexOrThrow(COL_RECIPE_STEPS));
            Cursor c2 = db.query(TABLE_RECIPE_INGREDIENTS, null, COL_RI_RECIPE_ID + "=?", new String[]{String.valueOf(recipe.id)},null, null, null);
            while (c2.moveToNext()) {
                recipe.ingredients.add(new RecipeIngredientRequirement(
                        c2.getLong(c2.getColumnIndexOrThrow(COL_RI_ID)),
                        c2.getLong(c2.getColumnIndexOrThrow(COL_RI_RECIPE_ID)),
                        c2.getString(c2.getColumnIndexOrThrow(COL_RI_NAME)),
                        c2.getDouble(c2.getColumnIndexOrThrow(COL_RI_QUANTITY)),
                        c2.getString(c2.getColumnIndexOrThrow(COL_RI_UNIT))));

            }
            c2.close();
            recipes.add(recipe);
        }
        c.close();
        db.close();
        return recipes;
    }

        public Recipe getRecipeById(long recipeId){
            SQLiteDatabase db = getReadableDatabase();
            Cursor c = db.query(TABLE_RECIPES, null, COL_RECIPE_ID + "=?", new String[]{String.valueOf(recipeId)}, null, null, null);
            Recipe recipe = null;
            if (c.moveToFirst()) {
                recipe = new Recipe();
                recipe.id = c.getLong(c.getColumnIndexOrThrow(COL_RECIPE_ID));
                recipe.name = c.getString(c.getColumnIndexOrThrow(COL_RECIPE_NAME));
                recipe.steps = c.getString(c.getColumnIndexOrThrow(COL_RECIPE_STEPS));
                Cursor c2 = db.query(TABLE_RECIPE_INGREDIENTS, null, COL_RI_RECIPE_ID + "=?", new String[]{String.valueOf(recipe.id)}, null, null, null);
                while (c2.moveToNext()) {
                    recipe.ingredients.add(new RecipeIngredientRequirement(
                            c2.getLong(c2.getColumnIndexOrThrow(COL_RI_ID)),
                            c2.getLong(c2.getColumnIndexOrThrow(COL_RI_RECIPE_ID)),
                            c2.getString(c2.getColumnIndexOrThrow(COL_RI_NAME)),
                            c2.getDouble(c2.getColumnIndexOrThrow(COL_RI_QUANTITY)),
                            c2.getString(c2.getColumnIndexOrThrow(COL_RI_UNIT))));
                }
                c2.close();

            }
            c.close();
            db.close();
            return recipe;
        }

    private void seedRecipes(SQLiteDatabase db) {
        insertRecipe(db, "Mac and cheese", "1. Boil macaroni in salted water.\n2. Melt butter and cheese with milk to make sauce.\n3. Stir pasta into cheese sauce and serve hot.",
                new Object[][]{{"Macaroni", 200.0, "g"}, {"Cheese", 100.0, "g"}, {"Milk", 100.0, "ml"}, {"Butter", 20.0, "g"}});

        insertRecipe(db, "Egg toast", "1. Toast bread slices.\n2. Melt butter in a pan and fry egg.\n3. Place fried egg on toast.",
                new Object[][]{{"Bread", 2.0, null}, {"Egg", 1.0, null}, {"Butter", 10.0, "g"}});

        insertRecipe(db, "Scrambled eggs", "1. Whisk eggs with milk and salt.\n2. Melt butter in a pan.\n3. Add eggs and scramble gently until cooked.",
                new Object[][]{{"Egg", 3.0, null}, {"Milk", 30.0, "ml"}, {"Butter", 10.0, "g"}, {"Salt", 1.0, "tsp"}});

        insertRecipe(db, "Hotdog", "1. Boil or grill sausage until hot.\n2. Place sausage into bun.\n3. Top with mustard or sauce.",
                new Object[][]{{"Hotdog Bun", 1.0, null}, {"Sausage", 1.0, null}, {"Mustard", 1.0, "tbsp"}});

        insertRecipe(db, "Chicken pasta", "1. Cook pasta according to package.\n2. Fry sliced chicken breast and minced garlic in olive oil.\n3. Combine pasta and chicken.",
                new Object[][]{{"Pasta", 200.0, "g"}, {"Chicken Breast", 150.0, "g"}, {"Garlic", 1.0, null}, {"Olive Oil", 2.0, "tbsp"}});

        insertRecipe(db, "Pap and wors", "1. Boil water with salt, add maize meal slowly, stirring until thick.\n2. Pan fry wors sausage until brown and juicy.\n3. Serve hot wors over pap.",
                new Object[][]{{"Maize Meal", 200.0, "g"}, {"Wors", 200.0, "g"}, {"Water", 500.0, "ml"}, {"Salt", 1.0, "tsp"}});

        insertRecipe(db, "Tea", "1. Boil water and pour into cup with tea bag.\n2. Steep for 3 minutes.\n3. Stir in milk and sugar.",
                new Object[][]{{"Tea Bag", 1.0, null}, {"Water", 250.0, "ml"}, {"Milk", 30.0, "ml"}, {"Sugar", 1.0, "tsp"}});

        insertRecipe(db, "Nachos", "1. Spread tortilla chips on a baking dish.\n2. Top with grated cheese and salsa.\n3. Bake until cheese melts.",
                new Object[][]{{"Tortilla Chips", 150.0, "g"}, {"Cheese", 100.0, "g"}, {"Salsa", 50.0, "g"}});

        insertRecipe(db, "Pancakes", "1. Mix flour, milk and egg into a smooth batter.\n2. Melt butter on a hot pan and pour batter.\n3. Flip and cook both sides until golden.",
                new Object[][]{{"Flour", 150.0, "g"}, {"Milk", 200.0, "ml"}, {"Egg", 1.0, null}, {"Butter", 15.0, "g"}});

        insertRecipe(db, "Grilled cheese", "1. Butter bread slices.\n2. Place cheese between slices, buttered sides out.\n3. Grill on a pan until golden and cheese melts.",
                new Object[][]{{"Bread", 2.0, null}, {"Cheese", 50.0, "g"}, {"Butter", 10.0, "g"}});

        insertRecipe(db, "Ham sandwich", "1. Butter bread slices.\n2. Layer ham slices between bread.",
                new Object[][]{{"Bread", 2.0, null}, {"Ham", 50.0, "g"}, {"Butter", 10.0, "g"}});

        insertRecipe(db, "Beef stew", "1. Brown diced beef in a pot.\n2. Add chopped potatoes, carrots and onions with water.\n3. Simmer until tender.",
                new Object[][]{{"Beef", 250.0, "g"}, {"Potato", 2.0, null}, {"Carrot", 1.0, null}, {"Onion", 1.0, null}});

        insertRecipe(db, "Garlic prawns", "1. Melt butter in a frying pan.\n2. Add garlic and prawns, cook for 4 minutes.\n3. Squeeze fresh lemon juice on top.",
                new Object[][]{{"Prawns", 200.0, "g"}, {"Garlic", 2.0, null}, {"Butter", 20.0, "g"}, {"Lemon", 1.0, null}});

        insertRecipe(db, "Slimy okra", "1. Wash and chop okra, onions and tomatoes.\n2. Heat oil in a pan and saute onions and tomatoes.\n3. Add okra and cook until soft and viscous.",
                new Object[][]{{"Okra", 200.0, "g"}, {"Onion", 1.0, null}, {"Tomato", 1.0, null}, {"Oil", 1.0, "tbsp"}});

        insertRecipe(db, "Curry", "1. Saute chopped onion and curry powder in oil.\n2. Add diced chicken and tomatoes.\n3. Simmer until sauce thickens.",
                new Object[][]{{"Chicken", 200.0, "g"}, {"Curry Powder", 2.0, "tbsp"}, {"Onion", 1.0, null}, {"Tomato", 2.0, null}});

        insertRecipe(db, "Mashed potatoes", "1. Peel and boil potatoes until tender.\n2. Drain water and add butter, milk and salt.\n3. Mash until smooth.",
                new Object[][]{{"Potato", 4.0, null}, {"Butter", 30.0, "g"}, {"Milk", 50.0, "ml"}, {"Salt", 1.0, "tsp"}});
    }

    private void insertRecipe(SQLiteDatabase db, String name, String steps, Object[][] ingredients) {
    ContentValues recipeValues = new ContentValues();
    recipeValues.put(COL_RECIPE_NAME, name);
    recipeValues.put(COL_RECIPE_STEPS, steps);
    long recipeId = db.insert(TABLE_RECIPES, null, recipeValues);

    for (int i = 0; i < ingredients.length; i++) {
    Object[] ing = ingredients[i];
    ContentValues iv = new ContentValues();
    iv.put(COL_RI_RECIPE_ID, recipeId);
    iv.put(COL_RI_NAME, (String) ing[0]);
    iv.put(COL_RI_QUANTITY, (Double) ing[1]);
    iv.put(COL_RI_UNIT, (String) ing[2]);
    db.insert(TABLE_RECIPE_INGREDIENTS, null, iv);
    }
}}