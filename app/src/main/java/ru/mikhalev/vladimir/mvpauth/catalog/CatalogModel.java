package ru.mikhalev.vladimir.mvpauth.catalog;

import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpauth.core.layers.model.BaseModel;
import ru.mikhalev.vladimir.mvpauth.data.storage.Product;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel extends BaseModel {

    public boolean isUserAuth() {
//        return mDataManager.getAuthToken();
        return false;
    }

    public void updateProduct(Product product) {
        mDataManager.saveProductInDB(product);
    }

//    public Observable<ProductDto> getProductFromPosition(int position) {
//        return mDataManager.getProductFromPosition(position);
//    }

    public Observable<RealmResults<Product>> getProductsObs() {
        return mDataManager.getProductsFromDB();
    }

//    @RxLogObservable
//    public Observable<ProductRes> fromNetwork() {
//        return mDataManager.getProductsFromNetwork();
//    }
//
//    @RxLogObservable
//    public Observable<ProductDto> fromDisk() {
//        return Observable.defer(() -> {
//            List<ProductDto> diskData = mDataManager.getProductsFromDB();
//            return diskData == null ?
//                    Observable.empty() :
//                    Observable.from(diskData);
//        });
//    }
}
