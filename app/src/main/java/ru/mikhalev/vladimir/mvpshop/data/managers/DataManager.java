package ru.mikhalev.vladimir.mvpshop.data.managers;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;

import java.util.Map;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.App;
import ru.mikhalev.vladimir.mvpshop.data.network.RestCallTransformer;
import ru.mikhalev.vladimir.mvpshop.data.network.api.RestService;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AddressRes;
import ru.mikhalev.vladimir.mvpshop.data.storage.Product;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.components.DataManagerComponent;
import ru.mikhalev.vladimir.mvpshop.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpshop.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpshop.utils.RawUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataManager {
    @SuppressLint("StaticFieldLeak")
    private static DataManager instance = new DataManager();

    @Inject PreferencesManager mPreferencesManager;
    @Inject RestService mRestService;
    @Inject Context mContext;

    private AccountRes mMockAccount;
    private Realm mRealm;

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
        mRealm = Realm.getDefaultInstance();
        generateMockAccount();
    }

    public String getLastProductUpdate() {
        return mPreferencesManager.getLastProductUpdate();
    }

    public String getAuthToken() {
        return mPreferencesManager.getAuthToken();
    }

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }

//    public Observable<ProductDto> getProductFromPosition(int position) {
//        // TODO: 27-Oct-16 temp sample mock data fix me (maybe load from db)
//        return Observable.just(mMockProductList.get(position - 1));
//    }


    public void updateProductsFromNetwork() {
        mRestService.getProductResponse(getLastProductUpdate())
                .compose(new RestCallTransformer<>())
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(productRes -> {
                    Product product = new Product(productRes);
                    if (productRes.isActive()) {
                        saveProductInDB(product);
                    } else {
                        deleteProductFromDB(product);
                    }
                })
                .subscribe();
    }

    public void saveProductInDB(Product product) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(product));
    }

    public void deleteProductFromDB(Product product) {
        mRealm.executeTransaction(realm -> {
            realm.where(Product.class)
                    .equalTo("id", product.getId())
                    .findAll()
                    .deleteAllFromRealm();
        });
    }


    public Observable<RealmResults<Product>> getProductsFromDB() {
        return mRealm.where(Product.class)
                .findAllSorted("id")
                .asObservable();
    }

    private void generateMockAccount() {
        mMockAccount = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.account), AccountRes.class);
        saveProfileInfo(mMockAccount.getFullname(), mMockAccount.getPhone());
        saveAvatarPhoto(mMockAccount.getAvatar());
        saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY, mMockAccount.isOrderNotification());
        saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY, mMockAccount.isPromoNotification());
    }

    // TODO: 01.12.2016 implement real
    public Observable<AddressRes> getAccountAddresses() {
        return Observable.from(mMockAccount.getAddresses());
    }

    public void updateOrInsertAddress(AddressRes address) {
        if (mMockAccount.getAddresses().contains(address)) {
            mMockAccount.getAddresses().set(mMockAccount.getAddresses().indexOf(address), address);
        } else {
            mMockAccount.getAddresses().add(address);
        }
    }

    public Observable<AddressRes> getAccountAddressFromPosition(int position) {
        return Observable.just(mMockAccount.getAddresses().get(position));
    }

    public void removeAddress(Observable<AddressRes> address) {
        mMockAccount.getAddresses().remove(address);
    }

    public Map<String, Boolean> getAccountSettings() {
        return mPreferencesManager.getAccountSettings();
    }

    public void saveAccountSetting(String notificationPromoKey, boolean isChecked) {
        mPreferencesManager.saveAccountSetting(notificationPromoKey, isChecked);
    }

    public Map<String, String> getAccountProfileInfo() {
        return mPreferencesManager.getUserAccountProfileInfo();
    }

    public void saveProfileInfo(String name, String phone) {
        mPreferencesManager.saveProfileInfo(name, phone);
    }


    public void saveAvatarPhoto(String photoPath) {
        mPreferencesManager.saveAvatarPhoto(photoPath);
    }
}
