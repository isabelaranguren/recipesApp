package com.example.myrecipeapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    void handleRecipeListResult(RecipeList recipes) {
        Log.d("MainActivity", "Back from API on the UI thread with the weather results!");

        // Check for an error
        if (recipes == null) {
            Log.d("MainActivity", "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the recipes",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d("MainActivity", "recipes: " + recipes.getRecipes().toString());

            // Show the temperature to the user
            Toast.makeText(this, "Successfully retrieved recipes",
                    Toast.LENGTH_LONG).show();
        }
    }
}
