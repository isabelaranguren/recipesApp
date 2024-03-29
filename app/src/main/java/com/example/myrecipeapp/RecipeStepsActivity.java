package com.example.myrecipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecipeStepsActivity extends AppCompatActivity {

    /**
     * Launches GetRecipeStepsAsync to get the steps from API call
     * then displays them in a listView
     */

    private static final String TAG = "RecipeStepsActivity";

    public int recipe_id;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("recipesdb");

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

            ArrayList<Step> steps = recipe[0].getSteps();
            ArrayList<String> stepString = new ArrayList<>();


            for (int i = 0; i < steps.size(); i++) {
                stepString.add(steps.get(i).getStep());
            }


            // Show the temperature to the user
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.adapter_steps_layout, stepString);

            ListView listView = (ListView)findViewById(R.id.stepDetails);
            listView.setAdapter(adapter);

        }
    }


    public void saveRecipe(View view) {
        mDatabaseReference.push().setValue(recipe_id);
    }
}