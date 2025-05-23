package com.example.ecommerce_project.models;

import java.io.Serializable;

public class FoodProductModel implements Serializable {
    String description;
    String name;
    String rating;
    String img_url;
    int price;

    public FoodProductModel() {
    }

    public FoodProductModel(String description, String name, String rating, String img_url, int price) {
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.img_url = img_url;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
