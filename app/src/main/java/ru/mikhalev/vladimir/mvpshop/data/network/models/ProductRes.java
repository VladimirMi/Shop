package ru.mikhalev.vladimir.mvpshop.data.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Developer Vladimir Mikhalev, 13.12.2016.
 */

public class ProductRes {
    // TODO: 06.01.2017 check setters
    @SerializedName("_id")
    private String id;
    private String productName;
    private String imageUrl;
    private String description;
    private int price;
    @SerializedName("raiting")
    private float rating;
    private boolean active;
    private List<CommentRes> comments;

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public float getRating() {
        return rating;
    }

    public List<CommentRes> getComments() {
        return comments;
    }
}
