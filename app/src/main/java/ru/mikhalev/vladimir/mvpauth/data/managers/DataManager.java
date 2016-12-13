package ru.mikhalev.vladimir.mvpauth.data.managers;


import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.core.App;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.DataManagerComponent;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpauth.core.network.api.RestService;
import ru.mikhalev.vladimir.mvpauth.data.dto.Account;
import ru.mikhalev.vladimir.mvpauth.data.dto.Address;
import ru.mikhalev.vladimir.mvpauth.data.dto.Catalog;
import ru.mikhalev.vladimir.mvpauth.data.dto.Product;
import ru.mikhalev.vladimir.mvpauth.utils.RawUtils;
import rx.Observable;

public class DataManager {
    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    RestService mRestService;
    @Inject
    Context mContext;


    private List<Product> mMockProductList = new ArrayList<>();
    private Account mMockAccount;

    public DataManager() {
        DaggerService.createDaggerComponent(DataManagerComponent.class,
                App.getAppComponent(),
                new LocaleModule(),
                new NetworkModule()).inject(this);
        generateMockCatalog();
        generateMockAccount();
    }

    public boolean isAuthUser() {
//        return !mPreferencesManager.getAuthToken().equals(ConstantManager.INVALID_TOKEN);
        return false;
    }

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }

    public Observable<Product> getProductFromPosition(int position) {
        // TODO: 27-Oct-16 temp sample mock data fix me (maybe load from db)
        return Observable.just(mMockProductList.get(position - 1));
    }

    public void updateProduct(Product product) {
        // TODO: 27-Oct-16 update product count or status (something in product) save in DB
    }

    public Observable<Product> getProductList() {
        // TODO: 27-Oct-16 load product list from anywhere
        return Observable.from(mMockProductList);
    }

    private void generateMockCatalog() {
        mMockProductList = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.goods), Catalog.class).getGoods();
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
