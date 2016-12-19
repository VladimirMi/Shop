package ru.mikhalev.vladimir.mvpauth.data.storage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
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
    private int count;
    private boolean favorite;

    public Product(ProductRes productRes) {
        this.id = productRes.getRemoteId();
        this.productName = productRes.getProductName();
        this.imageUrl = productRes.getImageUrl();
        this.description = productRes.getDescription();
    }
}
