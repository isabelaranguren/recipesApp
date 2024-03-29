package com.example.myrecipeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    public ArrayList<String> ingredientsList;
    public CustomAdapter adapter;
    public TextView autocomplete_text;
    private EditText EditTextInput;
    private String first_hit;


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

        // Create the CustomAdapter for the Search Results
        ListView listView = findViewById(R.id.ingredientsListView);
        adapter = new CustomAdapter(this, R.layout.custom_ingredient_layout, ingredientsList);
        listView.setAdapter(adapter);


        // Add listener to EditText to launch autocomplete per keystroke
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

    /**
     * Updates UI Thread with Autocomplete result
     * or error message if null
     * @param result
     */
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

    /**
     *  Adds user input ingredient to ingredientList then
     *  updates the adapter to display
     *  ingredient on the UI
     *
     */
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
            autocomplete_text.setText("");
            adapter.notifyDataSetChanged();
        }
    }



    /**
     * Launches activity for Search Results
     * Passes on ingredientsList for API call
     */
    public void findRecipes(View view) {
        Log.d(TAG, "About to create intent for RecipeResultsActivity");
        Intent intent = new Intent(this, RecipeResultsActivity.class);
        intent.putExtra("ingredients", ingredientsList);
        startActivity(intent);
    }

    /**
     * Creates custom layout for Ingredient ListView to
     * include checkbox and delete button
     */
    public class CustomAdapter extends ArrayAdapter<String> {

        public static final String TAG = "SearchActivity: customAdapter";
        private final Context context;
        int resource;

        public CustomAdapter(Context context, int resource, ArrayList<String> ingredients) {
            super(context, resource, ingredients);
            this.context = context;
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);

            TextView textView = convertView.findViewById(R.id.ingredient);
            textView.setText(ingredientsList.get(position));

            ImageButton deleteImageView = convertView.findViewById(R.id.deleteButton);
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ingredientsList.remove(position);
                    adapter.notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }
}
