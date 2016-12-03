package ru.mikhalev.vladimir.mvpauth.catalog;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import ru.mikhalev.vladimir.mvpauth.BR;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */
@Parcel
public class ProductViewModel extends BaseObservable {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("name")
    @Expose
    String productName;

    @SerializedName("image_url")
    @Expose
    String imageUrl;

    @SerializedName("description")
    @Expose
    String description;

    @SerializedName("price")
    @Expose
    int price;

    int count;

    public ProductViewModel() {
        this.count = 1;
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
        notifyPropertyChanged(BR.count);
    }

    public void addProduct() {
        setCount(++count);
    }

    public void deleteProduct() {
        setCount(--count);
    }
}
