package ru.mikhalev.vladimir.mvpshop.features.details;

import android.databinding.Bindable;

import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.data.storage.Product;

/**
 * Developer Vladimir Mikhalev 23.12.2016
 */

public class DetailsViewModel extends BaseViewModel {
    private int id;
    private String productName;
    private String imageUrl;
    private String description;
    private int price;
    private int count;
    private boolean favorite;

    public DetailsViewModel() {
    }

    public DetailsViewModel(Product product) {
        id = product.getId();
        productName = product.getProductName();
        imageUrl = product.getImageUrl();
        description = product.getDescription();
        price = product.getPrice();
        count = product.getCount();
        favorite = product.isFavorite();
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }


    @Bindable
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addProduct() {
        setCount(++count);
    }

    public void deleteProduct() {
        setCount(--count);
    }

    @Bindable
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}