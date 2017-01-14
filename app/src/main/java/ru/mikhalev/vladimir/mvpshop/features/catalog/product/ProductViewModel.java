package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.data.storage.Product;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */
public class ProductViewModel extends BaseViewModel {

    private final Product mProduct;
    public int id;
    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableInt price = new ObservableInt();
    public final ObservableInt count = new ObservableInt();
    public final ObservableBoolean favorite = new ObservableBoolean();

    public ProductViewModel(Product product) {
        mProduct = product;
        id = product.getId();
        init();
        mProduct.addChangeListener(element -> init());
    }

    public void init() {
        productName.set(mProduct.getProductName());
        imageUrl.set(mProduct.getImageUrl());
        description.set(mProduct.getDescription());
        price.set(mProduct.getPrice());
        count.set(mProduct.getCount());
        favorite.set(mProduct.isFavorite());
    }

    public int getId() {
        return id;
    }

    public void addProduct() {
        int curCount = count.get();
        mProduct.setCount(++curCount);
    }

    public void deleteProduct() {
        int curCount = count.get();
        mProduct.setCount(--curCount);
    }

    public void setFavorite() {
        mProduct.setFavorite(!favorite.get());
    }
}
