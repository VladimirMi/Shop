package ru.mikhalev.vladimir.mvpauth.catalog;

import ru.mikhalev.vladimir.mvpauth.data.dto.ProductLocalInfo;
import ru.mikhalev.vladimir.mvpauth.data.dto.ProductRes;

/**
 * Developer Vladimir Mikhalev, 17.12.2016.
 */
public class ProductDto {

    private int id;
    private String productName;
    private String imageUrl;
    private String description;
    private int price;
    private int count;
    private boolean favorite;

    public ProductDto(ProductRes productRes, ProductLocalInfo productLocalInfo) {
        this.id = productRes.getRemoteId();
        this.productName = productRes.getProductName();
        this.imageUrl = productRes.getImageUrl();
        this.description = productRes.getDescription();
        this.price = productRes.getPrice();
        this.count = productLocalInfo.getCount();
        this.favorite = productLocalInfo.isFavorite();
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", favorite=" + favorite +
                '}';
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
