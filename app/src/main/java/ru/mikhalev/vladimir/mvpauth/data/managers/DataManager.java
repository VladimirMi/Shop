package ru.mikhalev.vladimir.mvpauth.data.managers;


import android.content.Context;

import com.f2prateek.rx.preferences.Preference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.App;
import ru.mikhalev.vladimir.mvpauth.core.base.RestCallTransformer;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.DataManagerComponent;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpauth.core.network.api.RestService;
import ru.mikhalev.vladimir.mvpauth.data.dto.Account;
import ru.mikhalev.vladimir.mvpauth.data.dto.Address;
import ru.mikhalev.vladimir.mvpauth.data.dto.Catalog;
import ru.mikhalev.vladimir.mvpauth.data.dto.ProductRes;
import ru.mikhalev.vladimir.mvpauth.utils.RawUtils;
import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class DataManager {
    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    RestService mRestService;
    @Inject
    Context mContext;


    private List<ProductRes> mMockProductResList = new ArrayList<>();
    private Account mMockAccount;

    public DataManager() {
        DaggerService.createDaggerComponent(DataManagerComponent.class,
                App.getAppComponent(),
                new LocaleModule(),
                new NetworkModule()).inject(this);
//        generateMockCatalog();
        generateMockAccount();
    }

    public String getLastProductUpdate() {
        return mPreferencesManager.getLastProductUpdate().get();
    }

    public Preference<String> getAuthTokenPref() {
        return mPreferencesManager.getAuthTokenPref();
    }

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }

    public Observable<ProductRes> getProductFromPosition(int position) {
        // TODO: 27-Oct-16 temp sample mock data fix me (maybe load from db)
        return Observable.just(mMockProductResList.get(position - 1));
    }

    public void updateProduct(ProductRes productRes) {
        // TODO: 27-Oct-16 update productRes count or status (something in productRes) save in DB
    }

    public Observable<ProductRes> getProductsFromNetwork() {
        return mRestService.getProductResponse(getLastProductUpdate())
                .compose(new RestCallTransformer<List<ProductRes>>())
                .doOnNext(productRes -> Timber.e("getProductList: " + Thread.currentThread().getName()))
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(productRes -> {
                    if (!productRes.isActive()) {
                        // TODO: 15.12.2016 delete from DB
                    }
                })
                .filter(ProductRes::isActive)
                .doOnNext(this::saveInDB);
    }

    public List<ProductRes> getProductsFromDB() {
        return null;
    }

    public List<ProductRes> getProductList() {
        return mMockProductResList;
    }

    private void saveInDB(ProductRes productRes) {

    }

    private void generateMockCatalog() {
        mMockProductResList = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.goods), Catalog.class).getGoods();
    }

    private void generateMockAccount() {
        mMockAccount = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.account), Account.class);
        saveProfileInfo(mMockAccount.getFullname(), mMockAccount.getPhone());
        saveAvatarPhoto(mMockAccount.getAvatar());
        saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY, mMockAccount.isOrderNotification());
        saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY, mMockAccount.isPromoNotification());
    }

    // TODO: 01.12.2016 implement real
    public Observable<Address> getAccountAddresses() {
        return Observable.from(mMockAccount.getAddresses());
    }

    public void updateOrInsertAddress(Address address) {
        if (mMockAccount.getAddresses().contains(address)) {
            mMockAccount.getAddresses().set(mMockAccount.getAddresses().indexOf(address), address);
        } else {
            mMockAccount.getAddresses().add(address);
        }
    }

    public Observable<Address> getAccountAddressFromPosition(int position) {
        return Observable.just(mMockAccount.getAddresses().get(position));
    }

    public void removeAddress(Observable<Address> address) {
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
