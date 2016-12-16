package ru.mikhalev.vladimir.mvpauth.catalog;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.data.dto.ProductRes;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel extends BaseModel {

    public List<ProductRes> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAuth() {
//        return mDataManager.getAuthTokenPref();
        return false;
    }

    public void updateProduct(ProductRes productRes) {
        mDataManager.updateProduct(productRes);
    }

    public Observable<ProductRes> getProductFromPosition(int position) {
        return mDataManager.getProductFromPosition(position);
    }

    @RxLogObservable
    public Observable<ProductRes> fromNetwork() {
        return mDataManager.getProductsFromNetwork();
    }

    @RxLogObservable
    public Observable<ProductRes> fromDisk() {
        return Observable.defer(() -> {
            List<ProductRes> diskData = mDataManager.getProductsFromDB();
            return diskData == null ?
                    Observable.empty() :
                    Observable.from(diskData);
        });
    }
}
