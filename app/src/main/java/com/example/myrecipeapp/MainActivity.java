package com.example.myrecipeapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ArrayAdapter<String> arrayAdapter;
    List<String> stepslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // First create the List and the ArrayAdapter
        stepslist = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.activity_main, stepslist);

        // Now connect the ArrayAdapter to the ListView
        ListView listView = findViewById(R.id.RecipeView);
        listView.setAdapter(arrayAdapter);

    }

    /**
     * This is the onclick event handler that will be called for the "Get Recipe" button.
     * It will make sure the API gets called on a background thread.
     * @param view The button that was clicked.
     */
    public void getRecipe(View view) {

        // Get the id of the recipe
        EditText txtId = findViewById(R.id.txtId);
        String holder = txtId.getText().toString();
        int id = Integer.parseInt(holder);

        Log.i("MainActivity","Getting weather for city: " + id);

        // Set up a new instance of our runnable object that will be run on the background thread
        GetRecipeAsync getRecipeAsync = new GetRecipeAsync(this, id);

        // Set up the thread that will use our runnable object
        Thread t = new Thread(getRecipeAsync);

        // starts the thread in the background. It will automatically call the run method of
        // the getWeatherAsync object we gave it earlier
        t.start();
    }

    /**
     * This function will get called (on the main UI thread) once we have successfully returned
     * from the API with results.
     * @param elements The results of the API call. If an error occurred, this will be null.
     */
    void handleRecipeElementsResult(RecipeElements elements) {
        Log.d("MainActivity", "Back from API on the UI thread with the recipe!");

        // Check for an error
        if (elements == null) {
            Log.d("MainActivity", "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the Recipe",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d("MainActivity", "elements: " + elements.getSteps().toString());

            // Clear any previous recipe information
            arrayAdapter.clear();

            // Go through each step in the recipe and add it to the ListView
            for (RecipeElements steps : RecipeElements.getSteps()) {
                String instructions = "";
                if (steps.getSteps().size() > 0) {
                    // Just get the first item in the outlook descriptions...
                    instructions = steps.getSteps().get(0).getStep();
                }

                // Prepare a string that has the time and the outlook
                String result = steps.getName() + ": " + instructions;
                Log.d("MainActivity", result);

                // Add the result to our arrayAdapter which will put it in the list on the UI.
                arrayAdapter.add(result);
            }
        }
    }

}
