package ru.mikhalev.vladimir.mvpshop.data.managers;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Inject;

import io.realm.RealmObject;
import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.App;
import ru.mikhalev.vladimir.mvpshop.data.network.RestCallTransformer;
import ru.mikhalev.vladimir.mvpshop.data.network.api.RestService;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.components.DataManagerComponent;
import ru.mikhalev.vladimir.mvpshop.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpshop.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpshop.utils.RawUtils;
import rx.Observable;

public class DataManager {
    @SuppressLint("StaticFieldLeak")
    private static DataManager instance = new DataManager();

    @Inject PreferencesManager mPreferencesManager;
    @Inject RealmManager mRealmManager;
    @Inject RestService mRestService;
    @Inject Context mContext;

    public static DataManager getInstance() {
        return instance;
    }

    public PreferencesManager getPreferencesManager() {
        return mPreferencesManager;
    }

    private DataManager() {
        DaggerService.createDaggerComponent(DataManagerComponent.class,
                App.getAppComponent(),
                new LocaleModule(),
                new NetworkModule()).inject(this);
        generateMockAccount();
    }

    private void generateMockAccount() {
        AccountRes accountRes = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.account), AccountRes.class);
        mRealmManager.saveAccountInDB(accountRes);
    }

    //region =============== Network ==============

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }

    public void updateProductsFromNetwork() {
        mRestService.getProductResponse(getLastProductUpdate())
                .compose(new RestCallTransformer<>())
                .flatMap(Observable::from)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mRealmManager::saveProductInDB, Throwable::printStackTrace);
    }

    //endregion


    //region =============== DataBase ==============

    public void saveProductInDB(ProductRealm product) {
        mRealmManager.saveProductInDB(product);
    }

    public Observable<ProductRealm> getProductFromDB(String productId) {
        return mRealmManager.getProductFromDB(productId);
    }

    public Observable<RealmResults<ProductRealm>> getProductsFromDB() {
        return mRealmManager.getProductsFromDB();
    }

    public void saveAccountInDB(AccountRealm accountRealm) {
        mRealmManager.saveAccountInDB(accountRealm);
    }

    public Observable<AccountRealm> getAccountFromDB() {
        return mRealmManager.getAccount();
    }

    public void deleteFromDB(Class<? extends RealmObject> realmClass, String id) {
        mRealmManager.deleteFromDB(realmClass, id);
    }


    //endregion

    //region =============== Shared Preferences ==============

    public String getLastProductUpdate() {
        return mPreferencesManager.getLastProductUpdate();
    }


    public String getAuthToken() {
        return mPreferencesManager.getAuthToken();
    }

    //endregion
}
