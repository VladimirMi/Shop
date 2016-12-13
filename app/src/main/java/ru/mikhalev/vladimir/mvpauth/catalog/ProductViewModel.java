package ru.mikhalev.vladimir.mvpauth.catalog;

import android.databinding.Bindable;

import ru.mikhalev.vladimir.mvpauth.BR;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseViewModel;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */
public class ProductViewModel extends BaseViewModel {

    private int id;
    private String productName;
    private String imageUrl;
    private String description;
    private int price;

    private int count;

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
