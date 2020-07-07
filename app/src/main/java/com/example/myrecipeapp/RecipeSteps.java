package com.example.myrecipeapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecipeSteps {

    @SerializedName("name")
    private String name;
    @SerializedName("steps")
    private ArrayList<Step> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

}
