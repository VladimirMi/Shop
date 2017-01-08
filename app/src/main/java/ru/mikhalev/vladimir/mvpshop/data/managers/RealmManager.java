package ru.mikhalev.vladimir.mvpshop.data.managers;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpshop.data.network.models.CommentRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.ProductRes;
import ru.mikhalev.vladimir.mvpshop.data.storage.Comment;
import ru.mikhalev.vladimir.mvpshop.data.storage.Product;
import rx.Observable;

/**
 * Developer Vladimir Mikhalev, 06.01.2017.
 */

public class RealmManager {

    public void saveProductInDB(Product product) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.copyToRealmOrUpdate(product));
        realm.close();
    }

    public void saveProductInDB(ProductRes productRes) {
        Product product = new Product(productRes);

        if (!productRes.getComments().isEmpty()) {
            Observable.from(productRes.getComments())
                    .doOnNext(commentRes -> {
                        if (!commentRes.isActive()) {
                            deleteFromDB(Comment.class, commentRes.getId());
                        }
                    })
                    .filter(CommentRes::isActive)
                    .map(Comment::new)
                    .subscribe(comment -> product.getComments().add(comment));
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


    public Observable<RealmResults<Product>> getProductsFromDB() {
        return Realm.getDefaultInstance().where(Product.class)
                .findAllAsync()
                .asObservable()
                .filter(RealmResults::isLoaded);
    }
}
