package com.example.myrecipeapp;

import com.google.gson.annotations.SerializedName;

public class Length {
    @SerializedName("number")
    private Integer number;
    @SerializedName("unit")
    private String unit;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
