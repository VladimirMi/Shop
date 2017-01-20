package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */
public class ProductViewModel extends BaseViewModel {

    private final ProductRealm mProductRealm;
    public int id;
    public final ObservableField<String> productName = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableInt price = new ObservableInt();
    public final ObservableInt count = new ObservableInt();
    public final ObservableBoolean favorite = new ObservableBoolean();

    public ProductViewModel(ProductRealm productRealm) {
        mProductRealm = productRealm;
        id = productRealm.getId();
        init();
        mProductRealm.addChangeListener(element -> init());
    }

    public void init() {
        productName.set(mProductRealm.getProductName());
        if (!mProductRealm.getImageUrl().equals(imageUrl.get())) {
            imageUrl.set(mProductRealm.getImageUrl());
        }
        description.set(mProductRealm.getDescription());
        price.set(mProductRealm.getPrice());
        count.set(mProductRealm.getCount());
        favorite.set(mProductRealm.isFavorite());
    }

    public int getId() {
        return id;
    }
}
