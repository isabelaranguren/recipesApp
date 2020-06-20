package com.example.myrecipeapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeList {

    private RecipeList recipes;

    public RecipeList getRecipes() {
        return recipes;
    }
    public void setRecipes(RecipeList recipes) {
        this.recipes = recipes;
    }
}