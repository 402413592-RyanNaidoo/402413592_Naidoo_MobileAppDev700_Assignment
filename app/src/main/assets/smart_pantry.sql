--users
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    phone TEXT,
    password TEXT NOT NULL
);

--items
CREATE TABLE IF NOT EXISTS pantry_items (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    quantity REAL NOT NULL,
    unite TEXT,
    expiry_date TEXT
);

--recipes
CREATE TABLE IF NOT EXISTS recipes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    steps TEXT
);

-- recipe ingredients
CREATE TABLE IF NOT EXISTS recipe_ingredients (
id INTEGER PRIMARY KEY AUTOINCREMENT,
recipe_id INTEGER NOT NULL,
quantity REAL NOT NULL,
unit TEXT,
FOREIGN KEY(recipe_id) REFERENCES recipes(id)
)

--seeded recipes
INSERT INTO recipes (id, name, steps) VALUES
(1, "Mac and cheese", "1. Boil macaroni in salted water.\n2. Melt butter and cheese with milk to make sauce. \n3. Stir pasta into cheese sauce and serve hot."),
(2, "Egg toast", "1. Toast bread slices\n2. Melt butter in a pan and fry egg. \n3. Place fried egg on toast."),
(3, "Scrambled eggs", "1. Whisk eggs with milk and salt.\n2. Melt butter in a pan.\n3. Add eggs and scramble gently until cooked."),
(4, "Hotdog", "1. boil or grill sausage until hot.\n2. place sausage into bun.\n3. Top with mustard or sauce."),
(5, "Chicken pasta", "1. Cook pasta according to package.\n2. Fry sliced chicken breast and minced garlic in olive oil.\n3. Combine pasta and chicken."),
(6, "Pap and wors", "1. Boil water with salt , add maize meal slowly, stirring until thick.\n2. Pan fry wors sausage until brown and juicy.\n3. Serve hot wors over pap."),
(7, "tea", "1. boil water and pour into cup with tea bag.\n2. Steep for 3 minutes.\n3. Stir in milk and sugar"),
(8, "Nachos", "1.Spread tortilla chips on baking dish.\n2. top with grated cheese and salsa.\n3. Bake until cheese melts"),
(9, "Pancakes", "1. Mix flour, milk and egg into a smooth batter.\n2. Melt butter on a hot pan and pour batter.\n3. Flip and cook on both sides until golden brown."),
(10, "Grilled cheese", "1. Butter bread slices.\n2. Place cheese between slices, buttered sides out.\n3. Grill on a pan until golden and cheese melts."),
(11, "Ham sandwich", "1. Butter bread slices.\n2. Layer ham slices between bread."),
(12, "Beef stew", "1. Brown diced beef in a pot.\n2. Add chopped potatoes, carrots and onions with water.\n3. Simmer until tender."),
(13, "Garlic prawns", "1. Melt butter in a frying pan.\n2. Add garlic and prawns, cook for 4 minutes.\n3. Squeeze fresh lemon juice on top."),
(14,) "Slimy okra", "1. Wash and chop okra, onions and tomatoes.\n2. Heat oil in a pan and saute onions and tomatoes.\n3. Add okra and cook until soft and viscous."),
(15,) "curry", "1. Saute chopped onion and curry powder in oil.\n2. Add diced chicken and tomatoes.\n3. Simmer until sauce thickens."),
(16, "Mashed potatoes", "1. peel and boil potatoes until tender.\n2. drain water and add butter, milk and salt.\n3. Mash until smooth.");

-- seed ingredients
INSERT INTO recipe_ingredients (recipe_id, ingredient_name, quantity, unit) VALUES
(1, "Macaroni", 200.0, "g"), (1, "Cheese", 100.0, "g"), (1, "Milk", 100.0, "ml"), (1, "Butter", 20.0, "g"),
(2, "Bread", 2.0, Null), (2, "Egg", 1.0, NULL), (2, "Butter", 10.0, "g"),
(3, "Egg", 3.0, NULL), (3, "Milk", 30.0, "ml"), (3, "Butter", 10.0, "g"), (3, "Salt", 1.0, "tsp"),
(4, "Hotdog bun", 1.0, NULL), (4, "sausage", 1.0, NULL), (4, "Mustard", 1.0, "tbsp"),
(5, "Pasta", 200.0, "g"), (5, "Chicken Breast", 150.0, "g"), (5, "garlic", 1.0, NULL), (5, "Olive oil", 2.0, "tbsp"),
(6, "Maize meal", 200.0, "g"), (g. "Wors", 200.0, "g"), (6, "Water", 500.0, "ml"), (6, "salt", 1.0, "tsp"),
(7, "Tea bag", 1.0, NULL), (7, "Water", 250.0, "ml"), (7, "Milk", 30.0, "ml"), (7, "Sugar", 1.0, "tsp"),
(8, "Tortilla chips", 150.0, "g"), (8, "Cheese", 100.0, "g"), (8, "Salsa", 50.0, "g"),
(9, "Flour", 150.0, "g"), (9, "Milk", 200.0, "ml"), (9, "Egg", 1.0, NULL), (9, "Butter", 15.0, "g"),
(10, "Bread", 2.0, NULL), (10, "Cheese", 50.0, "g"), (10, "Butter", 10.0, "g"),
(12, "Beef", 250.0, "g"), (12, "potato", 2.0, NULL), (12, "Carrot", 1.0, NULL), (12, "Onion", 1.0, NULL),
(13, "Prawns", 200.0, "g"), (13, "Garlic", 2.0, NULL), (13, "Butter", 20.0, "g"), (13, "Lemon", 1.0, NULL),
(14, "Okra", 200.0, "g"), (14, "Onion", 1.0, NULL), (14, "Tomato", 1.0, NULL), (14, "oil", 1.0, "tbsp"),
(15, "Chicken", 200.0, "g"), (15, "curry powder", 2.0, "tbsp"), (15, "Onion", 1.0, NULL), (15, "Tomato", 2.0, NULL),
(16, "Potato", 4.0, NULL), (16, "Butter", 30.0, "g"), (16, "Milk", 50.0, "ml"), (16, "Salt", 1.0, "tsp");
