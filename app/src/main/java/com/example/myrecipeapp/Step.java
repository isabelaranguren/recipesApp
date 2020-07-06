package com.example.myrecipeapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Step {

    @SerializedName("number")
    private Integer number;
    @SerializedName("step")
    private String step;
    @SerializedName("ingredients")
    private List<StepsIngredients> ingredients;
    @SerializedName("equipment")
    private List<Equipment> equipment;
    @SerializedName("length")
    private Length length;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public List<StepsIngredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<StepsIngredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }
}
