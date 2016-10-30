package ru.mikhalev.vladimir.mvpauth.catalog.product;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */
public class ProductDto implements Parcelable {
    private int id;
    private String productName;
    private String imageUrl;
    private String description;
    private int price;
    private int count;

    public ProductDto(int id, String productName, String imageUrl, String description, int price, int count) {
        this.id = id;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.count = count;
    }

    //region ==================== Parcelable ========================

    protected ProductDto(Parcel in) {
        id = in.readInt();
        productName = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        price = in.readInt();
        count = in.readInt();
    }

    public static final Creator<ProductDto> CREATOR = new Creator<ProductDto>() {
        @Override
        public ProductDto createFromParcel(Parcel in) {
            return new ProductDto(in);
        }

        @Override
        public ProductDto[] newArray(int size) {
            return new ProductDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(productName);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeInt(price);
        dest.writeInt(count);
    }

    //endregion

    //region ==================== Getters ========================
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
    //endregion

    public void addProduct() {
        count++;
    }

    public void deleteProduct() {
        count--;
    }
}
