package com.example.myrecipeapp;

import java.util.List;

public class RecipeElements {

    String name;
    public static List<RecipeSteps> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<RecipeSteps> getSteps() {
        return steps;
    }

    public void setDetails(List<RecipeSteps> steps) {
        this.steps = steps;
    }

}
