package com.example.myrecipeapp;

import com.google.gson.annotations.SerializedName;

public class RecipeFull {

    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("imageType")
    private String imageType;
    @SerializedName("readyInMinutes")
    private Integer readyInMinutes;
    @SerializedName("dairyFree")
    private Boolean dairyFree;
    @SerializedName("glutenFree")
    private Boolean glutenFree;
    @SerializedName("vegan")
    private Boolean vegan;
    @SerializedName("vegetarian")
    private Boolean vegetarian;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    public Boolean getDairyFree() {
        return dairyFree;
    }

    public Boolean getGlutenFree() {
        return glutenFree;
    }

    public Boolean getVegan() {
        return vegan;
    }

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setReadyInMinutes(Integer readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public void setDairyFree(Boolean dairyFree) {
        this.dairyFree = dairyFree;
    }

    public void setGlutenFree(Boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}
