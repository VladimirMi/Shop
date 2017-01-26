package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.databinding.Bindable;

import ru.mikhalev.vladimir.mvpshop.BR;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */
public class ProductViewModel extends BaseViewModel {

    private final ProductRealm mProductRealm;
    private int id;
    @Bindable private String productName = "";
    @Bindable private String imageUrl = "";
    @Bindable private String description = "";
    @Bindable private int price;
    @Bindable private int count;
    @Bindable private boolean favorite;

    public ProductViewModel(ProductRealm productRealm) {
        mProductRealm = productRealm;
        id = productRealm.getId();
        init();
        // TODO: 22.01.2017 remake to obs
        mProductRealm.addChangeListener(element -> init());
    }

    public void init() {
        setProductName(mProductRealm.getProductName());
        setImageUrl(mProductRealm.getImageUrl());
        setDescription(mProductRealm.getDescription());
        setPrice(mProductRealm.getPrice());
        setCount(mProductRealm.getCount());
        setFavorite(mProductRealm.isFavorite());
    }

    public void setProductName(String productName) {
        if (!this.productName.equals(productName)) {
            this.productName = productName;
            notifyPropertyChanged(BR.productName);
        }
    }

    public void setImageUrl(String imageUrl) {
        if (!this.imageUrl.equals(imageUrl)) {
            this.imageUrl = imageUrl;
            notifyPropertyChanged(BR.imageUrl);
        }
    }

    public void setDescription(String description) {
        if (!this.description.equals(description)) {
            this.description = description;
            notifyPropertyChanged(BR.description);
        }
    }

    public void setPrice(int price) {
        if (this.price != price) {
            this.price = price;
            notifyPropertyChanged(BR.price);
        }
    }

    public void setCount(int count) {
        if (this.count != count) {
            this.count = count;
            notifyPropertyChanged(BR.count);
        }
    }

    public void setFavorite(boolean favorite) {
        if (this.favorite != favorite) {
            this.favorite = favorite;
            notifyPropertyChanged(BR.favorite);
        }
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
