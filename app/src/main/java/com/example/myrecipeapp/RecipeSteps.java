package com.example.myrecipeapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RecipeSteps {

    @SerializedName("name")
    private String name;
    @SerializedName("steps")
    private List<Step> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

}
