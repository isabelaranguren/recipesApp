package com.example.myrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeStepsActivity extends AppCompatActivity {

    /**
     * Launches GetRecipeStepsAsync to get the steps from API call
     * then displays them in a listView
     */

    private static final String TAG = "RecipeStepsActivity";

    public int recipe_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        Log.d(TAG, "Received intent from RecipeResultsActivity");

        // Get the Intent that started this activity
        Intent intent = getIntent();
        recipe_id = intent.getIntExtra("recipe_id", 0);

        // Set up a new instance of our runnable object that will be run on the background thread
        GetRecipeStepsAsync getRecipeStepsAsync = new GetRecipeStepsAsync(this, recipe_id);

        // Set up the thread that will use our runnable object
        Thread t = new Thread(getRecipeStepsAsync);

        // starts the thread in the background. It will automatically call the run method of
        // the getRecipeAsync object we gave it earlier
        t.start();

        // Display the ingredients user has typed for search results
        TextView textView = findViewById(R.id.recipeId);

        String str = "Recipes with: " + recipe_id;
        textView.setText(str);

    }

    void handleStepsListResult(RecipeSteps[] recipe) {
        Log.d("RecipeStepsActivity", "Back from API on the UI thread with the steps results!");

        // Check for an error
        if (recipe == null) {
            Log.d("RecipeStepsActivity", "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the recipes",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d("RecipeStepsActivity", "recipes: ");

            // Show the temperature to the user
            Toast.makeText(this, "Successfully retrieved recipes",
                    Toast.LENGTH_LONG).show();
        }
    }
}
