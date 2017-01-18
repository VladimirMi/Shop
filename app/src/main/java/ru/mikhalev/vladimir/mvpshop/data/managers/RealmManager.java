package ru.mikhalev.vladimir.mvpshop.data.managers;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpshop.data.network.models.CommentRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.ProductRes;
import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import rx.Observable;

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

    private void deleteFromDB(Class<? extends RealmObject> realmClass, String id) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.where(realmClass)
                .equalTo("id", id)
                .findAll()
                .deleteAllFromRealm());
        realm.close();
    }


    public Observable<RealmResults<ProductRealm>> getProductsFromDB() {
        // TODO: 18.01.2017 close?
        return Realm.getDefaultInstance().where(ProductRealm.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded);
    }
}
