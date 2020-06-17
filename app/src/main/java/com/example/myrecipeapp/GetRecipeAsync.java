package com.example.myrecipeapp;

import android.util.Log;

public class GetRecipeAsync implements Runnable {

    private MainActivity activity;;

    private String ingredients;

    /**
     * Sets up the runnable to be called. It needs the MainActivity so it can run code on the
     * UI thread, and also the id so that it can get the recipe steps.
     * @param activity
     * @param ingredients
     */
    public GetRecipeAsync(MainActivity activity, String ingredients) {
        this.activity = activity;
        this.ingredients = ingredients;
    }

    @Override
    public void run() {
        // This is the function that will be run on the background thread.

        RecipeLoader loader = new RecipeLoader();

        // Now, call the function that will get the results from the API

        loader.getRecipeAndPostResults(ingredients, new RecipeResultHandler() {
            @Override
            public void handleResult(final RecipeList recipes) {
                Log.d("GetRecipeAsync", "Back from API, but still on background thread.");
                // At this point we will be back from the API with the results stored in `elements`

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // This is code that will now run on the UI thread. Call the function in
                        // MainActivity that will update the UI correctly.
                        activity.handleRecipeListResult(recipes);
                    }
                });
            }
        });
    }
}

