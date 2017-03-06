package ru.mikhalev.vladimir.mvpshop.data.managers;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.MultipartBody;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.App;
import ru.mikhalev.vladimir.mvpshop.data.network.RestCallTransformer;
import ru.mikhalev.vladimir.mvpshop.data.network.api.RestService;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AvatarUrlRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.CommentRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.LoginReq;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.components.DataManagerComponent;
import ru.mikhalev.vladimir.mvpshop.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpshop.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpshop.utils.AppConfig;
import ru.mikhalev.vladimir.mvpshop.utils.RawUtils;
import rx.Observable;
import timber.log.Timber;

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

    public Context getContext() {
        return mContext;
    }

    private DataManager() {
        DaggerService.createDaggerComponent(DataManagerComponent.class,
                App.getAppComponent(),
                new LocaleModule(),
                new NetworkModule()).inject(this);
    }

    private void generateMockAccount() {
        AccountRes accountRes = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.account), AccountRes.class);
        mRealmManager.saveAccountInDB(accountRes);
        saveToken("test_token");
    }

    //region =============== Network ==============

    public void loginUser(String email, String password) {
//        mRestService.loginUser(new LoginReq(email, password));
        generateMockAccount();
    }

    public void updateProductsFromNetwork() {
        mRestService.getProductResponse(getLastProductUpdate())
                .compose(new RestCallTransformer<>())
                .flatMap(Observable::from)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(errorObservable -> errorObservable
                        .zipWith(Observable.range(1, AppConfig.RETRY_REQUEST_COUNT), (throwable, retryCount) -> retryCount)
                        .doOnNext(retryCount -> Timber.e("Local update retry count : $d, %s", retryCount, new Date().toString()))
                        .map(retryCount -> ((long) (AppConfig.RETRY_REQUEST_BASE_DELAY * Math.pow(Math.E, retryCount))))
                        .doOnNext(delay -> Timber.e("Local update delay: %l", delay))
                        .flatMap(delay -> Observable.timer(delay, TimeUnit.MILLISECONDS))
                )
                .subscribe(mRealmManager::saveProductInDB, Throwable::printStackTrace);
    }

    public Observable<CommentRes> sendComment(String productId, CommentRes commentRes) {
        return mRestService.sendComment(productId, commentRes);
    }

    public Observable<AvatarUrlRes> uploadAvatar(MultipartBody.Part bodyPart) {
        return mRestService.uploadUserAvatar(bodyPart);
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

    public void saveToken(String token) {
        mPreferencesManager.saveToken(token);
    }

    public String getToken() {
        return mPreferencesManager.getToken();
    }

    public String getLastProductUpdate() {
        return mPreferencesManager.getLastProductUpdate();
    }
    //endregion
}
