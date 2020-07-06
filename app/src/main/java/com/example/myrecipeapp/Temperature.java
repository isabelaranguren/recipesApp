package com.example.myrecipeapp;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("number")
    private Double number;
    @SerializedName("unit")
    private String unit;

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
