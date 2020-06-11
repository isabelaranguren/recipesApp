package com.example.myrecipeapp;

import android.util.Log;

public class GetRecipeAsync implements Runnable {

    private MainActivity activity;;

    private int id;

    /**
     * Sets up the runnable to be called. It needs the MainActivity so it can run code on the
     * UI thread, and also the id so that it can get the recipe steps.
     * @param activity
     * @param id
     */
    public GetRecipeAsync(MainActivity activity, int id) {
        this.activity = activity;
        this.id = id;
    }

    @Override
    public void run() {
        // This is the function that will be run on the background thread.

        RecipeLoader loader = new RecipeLoader();

        // Now, call the function that will get the results from the API

        loader.getRecipeAndPostResults(id, new RecipeResultHandler() {
            @Override
            public void handleResult(final RecipeElements elements) {
                Log.d("GetRecipeAsync", "Back from API, but still on background thread.");
                // At this point we will be back from the API with the results stored in `elements`

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // This is code that will now run on the UI thread. Call the function in
                        // MainActivity that will update the UI correctly.
                        activity.handleRecipeElementsResult(elements);
                    }
                });
            }
        });
    }
}
