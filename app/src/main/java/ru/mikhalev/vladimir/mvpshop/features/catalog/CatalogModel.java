package ru.mikhalev.vladimir.mvpshop.features.catalog;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpshop.core.BaseModel;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import rx.Observable;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel extends BaseModel {

    public boolean isUserAuth() {
//        return mDataManager.getAuthToken();
        return false;
    }

//    public Observable<ProductDto> getProductFromPosition(int position) {
//        return mDataManager.getProductFromPosition(position);
//    }

    public Observable<RealmResults<ProductRealm>> getProductsObs() {
        Timber.e("getProductsObs: ");
        return mDataManager.getProductsFromDB();
    }

    public void saveProduct(ProductRealm product) {
        Realm realm = Realm.getDefaultInstance();
        mDataManager.saveProductInDB(product);
        realm.close();
    }

    public Observable<ProductRealm> getProductObs(String productId) {
        return mDataManager.getProductFromDB(productId);
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
