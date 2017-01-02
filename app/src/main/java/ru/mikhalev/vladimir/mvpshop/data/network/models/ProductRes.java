package ru.mikhalev.vladimir.mvpshop.data.network.models;

/**
 * Developer Vladimir Mikhalev, 13.12.2016.
 */

public class ProductRes {
    private int remoteId;
    private String productName;
    private String imageUrl;
    private String description;
    private int price;
    private float raiting;
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
