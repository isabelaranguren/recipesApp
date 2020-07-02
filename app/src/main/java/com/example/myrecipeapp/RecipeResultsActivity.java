package com.example.myrecipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeResultsActivity extends AppCompatActivity {

    /**
     * Launches GetRecipeAsync to get recipes from API call
     * then displays the results in the ListView
     */

    private static final String TAG = "RecipeResultsActivity";
    public List ingredientsList;
    public ArrayList<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

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

        // Display the ingredients user has typed for search results
        TextView textView = findViewById(R.id.recipesTextView);
        StringBuffer sb = new StringBuffer();

        for (Object s : ingredientsList) {
            sb.append(s);
            sb.append(", ");
        }

        String str = "Recipes with: " + sb.toString();
        textView.setText(str);

    }


    /**
     * Displays recipes in custom layout ListView
     * @param recipes
     */
    void handleRecipeListResult(final Recipe[] recipes) {
        Log.d(TAG, "Back from API on the UI thread with the recipe results!");
        recipeList = new ArrayList<>(Arrays.asList(recipes));

        // Check for an error
        if (recipes == null) {
            Log.d(TAG, "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the recipes",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "Successfully retrieved recipes!");

            ListView listView = findViewById(R.id.searchResults);

            // Create the CustomAdapter for the Search Results
            RecipeListAdapter adapter = new RecipeListAdapter(this, R.layout.custom_recipe_layout, recipeList);
            listView.setAdapter(adapter);


            // Launch new Activity when ListView item is clicked
            /** < ? >
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView  parent, View view, int position, long id) {
                    // When clicked, open new Activity for Recipe's full info
                    Intent intent = new Intent(this, RecipeInfo.class);
                    intent.putExtra("recipe_id", recipes[position].getId());
                    startActivity(intent);

                }

            });
             */
            }


        }

}
