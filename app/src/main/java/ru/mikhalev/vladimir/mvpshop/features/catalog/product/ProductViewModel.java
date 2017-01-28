package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;

import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;

/**
 * Developer Vladimir Mikhalev 27.10.2016
 */
public class ProductViewModel extends BaseViewModel {

    public final ObservableField<String> productName = new ObservableField<>("");
    public final ObservableField<String> imageUrl = new ObservableField<>("");
    public final ObservableField<String> description = new ObservableField<>("");
    public final ObservableInt price = new ObservableInt();
    public final ObservableInt count = new ObservableInt();
    public final ObservableBoolean favorite = new ObservableBoolean();
    public final ObservableFloat rating = new ObservableFloat();


    public void update(ProductRealm productRealm) {
        productName.set(productRealm.getProductName());
        if (!imageUrl.get().equals(productRealm.getImageUrl())) {
            imageUrl.set(productRealm.getImageUrl());
        }
        description.set(productRealm.getDescription());
        price.set(productRealm.getPrice());
        count.set(productRealm.getCount());
        favorite.set(productRealm.isFavorite());
    }
}
