package ru.mikhalev.vladimir.mvpshop.data.managers;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.CommentRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.ProductRes;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import rx.Observable;
import timber.log.Timber;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */

public class RealmManager {

    public void saveProductInDB(ProductRealm product) {
        Realm realm = Realm.getDefaultInstance();
        if (product.isManaged()) {
            realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(product));
        } else {
            realm.executeTransactionAsync(realm1 -> realm1.insertOrUpdate(product));
        }
        realm.close();
    }

    public void saveProductInDB(ProductRes productRes) {
        if (!productRes.isActive()) {
            deleteFromDB(ProductRealm.class, productRes.getId());
            return;
        }
        ProductRealm product = new ProductRealm(productRes);

        if (!productRes.getComments().isEmpty()) {
            Observable.from(productRes.getComments())
                    .doOnNext(commentRes -> {
                        if (!commentRes.isActive()) {
                            deleteFromDB(CommentRealm.class, commentRes.getId());
                        }
                    })
                    .filter(CommentRes::isActive)
                    .map(CommentRealm::new)
                    .subscribe(comment -> product.getCommentRealms().add(comment));
        }

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(product));
        realm.close();
    }

    public void deleteFromDB(Class<? extends RealmObject> realmClass, String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.where(realmClass)
                .equalTo("id", id)
                .findAll()
                .deleteAllFromRealm());
        realm.close();
    }


    public Observable<ProductRealm> getProductFromDB(String productId) {
        Realm realm = Realm.getDefaultInstance();
        Observable<ProductRealm> obs = realm.where(ProductRealm.class)
                .equalTo("id", productId)
                .findFirstAsync()
                .<ProductRealm>asObservable()
                .filter(productRealm -> productRealm.isLoaded())
                .map(realm::copyFromRealm)
                .doOnEach(notification -> {
                    Timber.e("getProductFromDB: ");
                });
        realm.close();
        return obs;
    }

    public Observable<RealmResults<ProductRealm>> getProductsFromDB() {
        return Realm.getDefaultInstance().where(ProductRealm.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded);
    }

    public void saveAccountInDB(AccountRealm accountRealm) {
        Realm realm = Realm.getDefaultInstance();
        if (accountRealm.isManaged()) {
            realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(accountRealm));
        } else {
            realm.executeTransactionAsync(realm1 -> realm1.insertOrUpdate(accountRealm));
        }
        realm.close();
    }

    public void saveAccountInDB(AccountRes accountRes) {
        AccountRealm accountRealm = new AccountRealm(accountRes);

        if (!accountRes.getAddresses().isEmpty()) {
            Observable.from(accountRes.getAddresses())
                    .map(AddressRealm::new)
                    .subscribe(addressRealm -> accountRealm.getAddressRealms().add(addressRealm));
        }

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(accountRealm));
        realm.close();
    }

    public Observable<AccountRealm> getAccount() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(AccountRealm.class)
                .findFirstAsync()
                .<AccountRealm>asObservable()
                .filter(accountRealm -> accountRealm.isLoaded())
                .filter(accountRealm -> accountRealm.isValid())
                .map(realm::copyFromRealm);
    }
}
