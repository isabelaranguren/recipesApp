package com.example.myrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeResultsActivity extends AppCompatActivity {

    private static final String TAG = "RecipeResultsActivity";
    List ingredientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_results);

        Log.d(TAG, "Received intent from SearchActivity");

        // Get the Intent that started this activity
        Intent intent = getIntent();
        ingredientsList = intent.getStringArrayListExtra(SearchActivity.EXTRA_MESSAGE);

        // Set up a new instance of our runnable object that will be run on the background thread
        GetRecipeAsync getRecipeAsync = new GetRecipeAsync(this, (ArrayList<String>) ingredientsList);

        // Set up the thread that will use our runnable object
        Thread t = new Thread(getRecipeAsync);

        // starts the thread in the background. It will automatically call the run method of
        // the getRecipeAsync object we gave it earlier
        t.start();

    }


    void handleRecipeListResult(Recipe[] recipes) {
        Log.d(TAG, "Back from API on the UI thread with the recipe results!");

        // Check for an error
        if (recipes == null) {
            Log.d(TAG, "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the recipes",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "recipes: " + recipes[0].getId());

            // Show the temperature to the user
            Toast.makeText(this, "Successfully retrieved recipes",
                    Toast.LENGTH_LONG).show();
        }
    }
}
