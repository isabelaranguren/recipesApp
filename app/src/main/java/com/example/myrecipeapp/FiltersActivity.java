package com.example.myrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FiltersActivity extends AppCompatActivity {

    /**
     * Displays filters for user to modify search results
     * Launched from RecipeResultsActivity
     */

    private static final String TAG = "FiltersActivity";
    HashMap<String, Boolean> filters = new HashMap<>();
    public ArrayList<String> ingredientsList = new ArrayList<>();
    public Button filterResultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);


        // Get the Intent that started this activity
        Intent intent = getIntent();
        ingredientsList = intent.getStringArrayListExtra("ingredients");


        // Initialize Filters button
        filterResultsButton = findViewById(R.id.filterButton);


    }

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

        Switch vegan = findViewById(R.id.vegan);
        if(vegan.isChecked()){
            filters.put("Vegan", true);
        } else {
            filters.put("Vegan", false);
        }

        Log.d(TAG, String.valueOf(filters));


        Intent intent = new Intent(this, RecipeResultsActivity.class);
        intent.putExtra("filters", filters);
        intent.putExtra("ingredients", ingredientsList);
        startActivity(intent);
    }

}
