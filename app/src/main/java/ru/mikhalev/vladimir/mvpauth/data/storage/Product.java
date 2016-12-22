package ru.mikhalev.vladimir.mvpauth.data.storage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.mikhalev.vladimir.mvpauth.catalog.ProductViewModel;
import ru.mikhalev.vladimir.mvpauth.data.dto.ProductRes;

/**
 * Developer Vladimir Mikhalev 19.12.2016
 */

public class Product extends RealmObject {
    @PrimaryKey
    private int id;
    private String productName;
    private String imageUrl;
    private String description;
    private int price;
    private int count = 1;
    private boolean favorite;

    public Product() {
    }

    public Product(ProductRes productRes) {
        this.id = productRes.getRemoteId();
        this.productName = productRes.getProductName();
        this.imageUrl = productRes.getImageUrl();
        this.description = productRes.getDescription();
        this.price = productRes.getPrice();
    }

    public Product(ProductViewModel viewModel) {
        this.id = viewModel.getId();
        this.productName = viewModel.getProductName();
        this.imageUrl = viewModel.getImageUrl();
        this.description = viewModel.getDescription();
        this.price = viewModel.getPrice();
        this.count = viewModel.getCount();
        this.favorite = viewModel.isFavorite();
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

    public int getCount() {
        return count;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
