package com.example.myrecipeapp;

import java.util.List;

public class RecipeSteps {
    int number;
    public static String step;
    List<Ingredients> ingredients;
    List<Equipment> equipment;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }
}
