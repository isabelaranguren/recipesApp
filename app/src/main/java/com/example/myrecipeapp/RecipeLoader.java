package com.example.myrecipeapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RecipeLoader {
    private static final String API_KEY = "cab6a9c0a9f8487fb902b9f9a2558a58";
    private static final String URL_ENDPOINT_RECIPE = "https://api.spoonacular.com/recipes/findByIngredients";

    private String apiCharset;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public RecipeLoader() {
        // Encoding - Default is UTF-8
        apiCharset = StandardCharsets.UTF_8.name();
    }

    /**
     * This function does a generic web service GET HTTP request and returns the result.
     * @param url
     * @return
     * @throws IOException
     */
    private String getHttpResults(String url) throws IOException {
        System.out.println("Making call to URL: " + url);

        // Make a connection to the provided URL
        URLConnection connection = new URL(url).openConnection();

        // Open the response stream and get a reader for it.
        InputStream responseStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));

        // If the API was written well, there will be only one line,
        // but just in case, I will go through each line in the response.

        // Because this could happen multiple times, a StringBuilder is much more efficient.
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    /**
     * This function will prepare the URL for the current Recipe call
     * and return the JSON result.
     * @param ingredients
     * @return
     * @throws IOException
     */
    private String getRecipeJson(String ingredients) throws IOException {
        String url = String.format("%s?ingredients=%s&apiKey=%s",
                URL_ENDPOINT_RECIPE,
                URLEncoder.encode(ingredients, apiCharset),
                URLEncoder.encode(API_KEY, apiCharset));

        return getHttpResults(url);
    }

    /**
     * Calls the weather api for recipe of the provided id.
     * @param ingredients
     * @return recipeList
     * @throws IOException
     */
    public Recipe[] getRecipe(String ingredients) throws IOException {
        // Call the API
        String results = getRecipeJson(ingredients);

        // Use GSON to deserialize the result
        Gson gson = new Gson();
        Recipe[] recipes = gson.fromJson(results, Recipe[].class);

        return recipes;
    }

    public void getRecipeAndPostResults(String ingredients, RecipeResultHandler handler) {
        try {
            Recipe[] recipes = getRecipe(ingredients);
            handler.handleResult(recipes);
        } catch (IOException e) {
            // TODO: Decide what to do here...
            e.printStackTrace();
            handler.handleResult(null);
        }
    }

}
