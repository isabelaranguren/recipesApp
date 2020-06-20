package com.example.myrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myrecipeapp.MESSAGE";
    private static final String TAG = "SearchActivity";
    private ArrayAdapter<String> arrayAdapter;
    public ArrayList<String> ingredientsList;
    public TextView autocomplete_text;
    private EditText EditTextInput;
    private String first_hit;
    private RecipeResultsActivity activity;

    long delay = 500; // 0.5 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();


    // Runs Autocomplete .5 secs after last keystroke
    private Runnable input_finish_checker = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                String input_string = EditTextInput.getText().toString();

                Autocomplete auto_runnable = new Autocomplete(input_string, SearchActivity.this);
                new Thread(auto_runnable).start();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.d(TAG, "Received intent from MainActivity");

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Create the List and the ArrayAdapter
        ingredientsList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientsList);

        // Now connect the ArrayAdapter to the ListView
        ListView listView = findViewById(R.id.ingredientsListView);
        listView.setAdapter(arrayAdapter);

        EditTextInput = (EditText) findViewById(R.id.search);
        EditTextInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //avoid triggering event when text is empty
                if (s.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {

                }
            }
        });

    }

    /** Updates UI Thread with Autocomplete result */
    void handleAutocompleteResult(final String result) {
        autocomplete_text = findViewById(R.id.autocomplete);
        first_hit= result;

        if (result == null) {
            Log.d(TAG, "API results were null");

            // Inform the user
            autocomplete_text.setText("No ingredient match");
        } else {
            Log.d(TAG, "Autocomplete result: " + result);
            autocomplete_text.setText(result);
        }

        // Changes EditTextView's text to that of Autocomplete result's
        autocomplete_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTextInput.setText(result);
            }
        });

    }

    /** Adds ingredients to listView */
    public void addIngredients(View view){
        EditText search_editText = (EditText) findViewById(R.id.search);
        String search_text = search_editText.getText().toString();

        // If the API returned nothing
        if (first_hit == null) {
            Log.d(TAG, "An error occurred: No ingredient hit found");

            // inform the user that something happened.
            Toast.makeText(this, "Can't add ingredient to list",
                    Toast.LENGTH_LONG).show();
        }
        // If user's input does not match autocomplete
        else if (!first_hit.equals(search_text)){
            Log.d(TAG, "User input does not match autocomplete ingredient");

            Toast.makeText(this, "Did you mean " + first_hit + "?",
                    Toast.LENGTH_LONG).show();
        }
        else {
            Log.d(TAG, "Ingredient valid. Adding to Ingredients List");
            ingredientsList.add(first_hit);
            search_editText.setText("");
            arrayAdapter.notifyDataSetChanged();
        }
    }



    /** TODO Pass ingredientsList data for Recipe Search Results */
    public void findRecipes(View view) {
        Log.d(TAG, "About to create intent for RecipeResultsActivity");

        // Set up a new instance of our runnable object that will be run on the background thread
        GetRecipeAsync getRecipeAsync = new GetRecipeAsync(activity, ingredientsList);

        // Set up the thread that will use our runnable object
        Thread t = new Thread(getRecipeAsync);

        // starts the thread in the background. It will automatically call the run method of
        // the getRecipeAsync object we gave it earlier
        t.start();

        Intent intent = new Intent(this, RecipeResultsActivity.class);
        startActivity(intent);
    }


}
