package ru.mikhalev.vladimir.mvpauth.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Developer Vladimir Mikhalev, 13.12.2016.
 */

public class ProductRes {
    @SerializedName("remoteId")
    @Expose
    private int remoteId;

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("raiting")
    @Expose
    private float raiting;

    @SerializedName("active")
    @Expose
    private boolean active;

    public int getRemoteId() {
        return remoteId;
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
}
