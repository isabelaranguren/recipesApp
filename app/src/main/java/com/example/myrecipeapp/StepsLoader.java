package com.example.myrecipeapp;

import com.google.gson.Gson;
import io.github.pixee.security.BoundedLineReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class StepsLoader {

    private static final String API_KEY = "cab6a9c0a9f8487fb902b9f9a2558a58";
    private static final String URL_ENDPOINT_RECIPE = "https://api.spoonacular.com/recipes";

    private String apiCharset;

    public StepsLoader() {
        // Encoding - Default is UTF-8
        apiCharset = StandardCharsets.UTF_8.name();
    }

    /**
     * This function does a generic web service GET HTTP request and returns the result.
     * @param url
     * @return
     * @throws IOException
     */
    private String getHttpResultsSteps(String url) throws IOException {
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
        while ((line = BoundedLineReader.readLine(reader, 5_000_000)) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }

    /**
     * This function will prepare the URL for the current Recipe call
     * and return the JSON result.
     * @param recipe_id
     * @return
     * @throws IOException
     */
    private String getStepsJson(int recipe_id) throws IOException {
        String url = String.format("%s/%s/analyzedInstructions?apiKey=%s",
                URL_ENDPOINT_RECIPE,
                recipe_id,
                URLEncoder.encode(API_KEY, apiCharset));

        return getHttpResultsSteps(url);
    }

    /**
     * Calls the weather api for recipe of the provided id.
     * @param recipe_id
     * @return StepsList
     * @throws IOException
     */
    public RecipeSteps[] getSteps(int recipe_id) throws IOException {
        // Call the API
        String results = getStepsJson(recipe_id);

        // Use GSON to deserialize the result
        Gson gson = new Gson();
        RecipeSteps[] recipeSteps = gson.fromJson(results, RecipeSteps[].class);

        return recipeSteps;
    }

    public void getStepsAndPostResults(int recipe_id, StepsResultHandler handler) {
        try {
            RecipeSteps[] recipeSteps = getSteps(recipe_id);
            handler.handleResult(recipeSteps);
        } catch (IOException e) {
            // TODO: Decide what to do here...
            e.printStackTrace();
            handler.handleResult(null);
        }
    }

}
