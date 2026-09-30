# Smart Pantry Manager
402413592 - Ryan Naidoo
A Java Android application that helps users reduce food waste by tracking ingredients they have at home and suggesting recipes based strictly on what's currently in their pantry.

## Features

- Add, edit and delete pantry ingredients (name, quantity, unit, expiry date)
- View all pantry items in a list
- Get recipe suggestions that strictly match your available ingredients
- View full recipe details, including ingredients and preparation steps
- Adjust settings such as expiry alerts and measurement units
- User login and registration

## Database

This app uses SQLite, implemented locally on device via SQLiteOpenHelper. All pantry items, recipes and recipe ingredient requirements are stored in local SQLite tables, with full CRUD support and persistence between app sessions.

## Setup & Run Instructions

1. Clone the repository:
```bash
   git clone https://github.com/402413592-RyanNaidoo/402413592_Naidoo_MobileAppDev700_Assignment.git
```
2. Open the project in Android Studio.
3. Connect an Android device or start an emulator.
4. Click Run in Android Studio
6. On first launch the app will seed the database with a starter set of recipes automatically.
