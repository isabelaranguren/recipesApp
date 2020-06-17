package com.example.myrecipeapp;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    private static final String TAG = "Ingredient" ;
    @SerializedName("name")
    private String name;

    public String getName() {
        Log.d(TAG, "First hit: " + name);
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
