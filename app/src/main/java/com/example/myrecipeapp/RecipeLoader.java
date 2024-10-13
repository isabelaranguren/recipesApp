package com.example.myrecipeapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import io.github.pixee.security.HostValidator;
import io.github.pixee.security.Urls;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RecipeLoader {

    // Some extra API keys: "cab6a9c0a9f8487fb902b9f9a2558a58"; "40dbe64d6bcc4634b8b49e64a6936e48"; "f84904e3b9564ea2966a4f537c261b8a"
    private static final String API_KEY = "0d5bedd68f2243ce9a22746a4626a5dd";
    private static final String URL_ENDPOINT_RECIPE = "https://api.spoonacular.com/recipes/findByIngredients";
    private static final String URL_ENDPOINT_RECIPE_FULL ="https://api.spoonacular.com/recipes/";
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
        URLConnection connection = Urls.create(url, Urls.HTTP_PROTOCOLS, HostValidator.DENY_COMMON_INFRASTRUCTURE_TARGETS).openConnection();

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
     * Gets recipes thru a search by ingredient call
     * Then individual API calls are made for each recipe in
     * the response to get each recipe's full info
     *
     * @param ingredients
     * @return recipeList
     *  - recipeList if a list of RecipeFull objects
     * @throws IOException
     */
    public ArrayList<RecipeFull> getRecipe(String ingredients) throws IOException {
        // Call the API
        String results = getRecipeJson(ingredients);

        // Use GSON to deserialize the result
        Gson gson = new Gson();
        Recipe[] recipes = gson.fromJson(results, Recipe[].class);

        // Olea's modification:
        // Create an arrayList of integers for the recipe IDs from the API response
        ArrayList<Integer> idList = new ArrayList<>();

        for (Recipe recipe : recipes) {
            idList.add(recipe.getId());
        }

        // Then extract RecipeFull objects with complete recipe info
        // into an arrayList then return it
        ArrayList<RecipeFull> full_recipes = getFullInfo(idList);

        return full_recipes;
    }

    public void getRecipeAndPostResults(String ingredients, RecipeResultHandler handler) {
        try {
            ArrayList<RecipeFull> recipes = getRecipe(ingredients);
            handler.handleResult(recipes);
        } catch (IOException e) {
            // TODO: Decide what to do here...
            e.printStackTrace();
            handler.handleResult(null);
        }
    }


    /**
     * This method makes separate API calls for each recipe to get
     * the full info needed for the filters
     * @param idList
     * @return
     * @throws IOException
     */
    public ArrayList<RecipeFull> getFullInfo(ArrayList<Integer> idList) throws IOException {

        ArrayList<RecipeFull> recipeList = new ArrayList<>();

        for (Integer id : idList) {
            String url = String.format("%s%s/information?includeNutrition=false&apiKey=%s",
                    URL_ENDPOINT_RECIPE_FULL,
                    URLEncoder.encode(String.valueOf(id), apiCharset),
                    URLEncoder.encode(API_KEY, apiCharset));

            String results = getHttpResults(url);

            Gson gson = new Gson();
            RecipeFull recipe = gson.fromJson(results, RecipeFull.class);

            recipeList.add(recipe);
        }

        return recipeList;
    }


}
