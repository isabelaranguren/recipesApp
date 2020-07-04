package com.example.myrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class FiltersActivity extends AppCompatActivity {

    /**
     * Displays filters for user to modify search results
     * Launched from RecipeResultsActivity
     */

    private static final String TAG = "FiltersActivity";
    public HashMap<String, Boolean> filters = new HashMap<>();
    public ArrayList<String> ingredientsList = new ArrayList<>();
    public Button filterResultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        Log.d(TAG, "Received intent from RecipeResultsActivity");

        // Get the Intent that started this activity
        Intent intent = getIntent();
        ingredientsList = intent.getStringArrayListExtra("ingredients");

        // Initialize Filters button
        filterResultsButton = findViewById(R.id.filterButton);


    }

    /**
     * Checks for user's filter choices then passes on this info
     * thru intent to launch RecipeResultsActivity
     * @param view
     */
    public void filter(View view) {
        Log.d(TAG, "Creating intent for Search Results Activity to update results with filters");

        CheckBox dairy = findViewById(R.id.dairy);
        if(dairy.isChecked()){
            filters.put("Dairy-free", true);
        } else {
            filters.put("Dairy-free", false);
        }

        CheckBox gluten = findViewById(R.id.gluten);
        if(gluten.isChecked()){
            filters.put("Gluten-free", true);
        } else {
            filters.put("Gluten-free", false);
        }

        CheckBox vegan = findViewById(R.id.vegan);
        if(vegan.isChecked()){
            filters.put("Vegan", true);
        } else {
            filters.put("Vegan", false);
        }

        Log.d(TAG, "Users filters: " + filters);

        // Launch RecipeResultsActivity to display results based on filters
        Intent intent = new Intent(this, RecipeResultsActivity.class);
        intent.putExtra("filters", filters);
        intent.putExtra("ingredients", ingredientsList);
        startActivity(intent);
    }

}
