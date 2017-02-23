package ru.mikhalev.vladimir.mvpshop.features.catalog;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpshop.core.BaseModel;
import ru.mikhalev.vladimir.mvpshop.data.jobs.SendMessageJob;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import rx.Observable;
import rx.Subscription;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 29.10.2016.
 */

public class CatalogModel extends BaseModel {

    public boolean isUserAuth() {
//        return mDataManager.getAuthToken();
        return true;
    }

    public Observable<RealmResults<ProductRealm>> getProductsObs() {
        Timber.e("getProductsObs: ");
        return mDataManager.getProductsFromDB();
    }

    public void saveProduct(ProductRealm product) {
        mDataManager.saveProductInDB(product);
    }

    public Observable<ProductRealm> getProductObs(String productId) {
        return mDataManager.getProductFromDB(productId);
    }

    public Observable<AccountRealm> getAccountObs() {
        return mDataManager.getAccountFromDB();
    }

    public void sendMessage(String productId, CommentRealm commentRealm) {
        SendMessageJob job = new SendMessageJob(productId, commentRealm);
        mJobManager.addJobInBackground(job);
    }
}
