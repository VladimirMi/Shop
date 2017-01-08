package ru.mikhalev.vladimir.mvpshop.data.managers;


import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.App;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountAddressDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountProfileDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountSettingsDto;
import ru.mikhalev.vladimir.mvpshop.data.network.RestCallTransformer;
import ru.mikhalev.vladimir.mvpshop.data.network.api.RestService;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;
import ru.mikhalev.vladimir.mvpshop.data.network.models.ProductRes;
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
    @Inject RealmManager mRealmManager;
    @Inject RestService mRestService;
    @Inject Context mContext;
    private Realm mRealm;


    private ArrayList<AccountAddressDto> mAddressDtoList;

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

    private void generateMockAccount() {
        AccountRes accountRes = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.account), AccountRes.class);
        AccountProfileDto profileDto = new AccountProfileDto(accountRes);
        AccountSettingsDto settingsDto = new AccountSettingsDto(accountRes);
        mAddressDtoList = accountRes.getAddresses();
        saveAccountProfile(profileDto);
        saveAccountSettings(settingsDto);
        saveAccountAddresses(mAddressDtoList);
    }

    //region =============== Network ==============

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }

    public void updateProductsFromNetwork() {
        mRestService.getProductResponse(getLastProductUpdate())
                .compose(new RestCallTransformer<>())
                .flatMap(Observable::from)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(productRes -> {
                    Product product = new Product(productRes);
                    if (!productRes.isActive()) {
                        saveProductInDB(product);
                    } else {
                        deleteProductFromDB(product);
                    }
                })
                .filter(ProductRes::isActive)
                .subscribe(mRealmManager::saveProductInDB);
    }

    //endregion


    //region =============== DataBase ==============

    public void saveProductInDB(Product product) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(product));
    }

    public void deleteProductFromDB(Product product) {
        mRealm.executeTransaction(realm -> realm.where(Product.class)
                .equalTo("id", product.getId())
                .findAll()
                .deleteAllFromRealm());
    }


    public Observable<RealmResults<Product>> getProductsFromDB() {
        return mRealm.where(Product.class)
                .findAllSorted("id")
                .asObservable();
    }

    //endregion


    //region =============== Shared Preferences ==============

    public String getLastProductUpdate() {
        return mPreferencesManager.getLastProductUpdate();
    }

    public String getAuthToken() {
        return mPreferencesManager.getAuthToken();
    }

    public void saveAccountProfile(AccountProfileDto profileDto) {
        mPreferencesManager.saveAccountProfile(profileDto);
    }

    public AccountProfileDto getAccountProfile() {
        return mPreferencesManager.getAccountProfile();
    }

    public void saveAccountSettings(AccountSettingsDto settingsDto) {
        mPreferencesManager.saveAccountSettings(settingsDto);
    }

    public AccountSettingsDto getAccountSettings() {
        return mPreferencesManager.getAccountSettings();
    }

    public void saveAccountAddresses(List<AccountAddressDto> addressDtoList) {
        mPreferencesManager.saveAccountAddresses(addressDtoList);
    }

    public Observable<AccountAddressDto> getAccountAddresses() {
        return Observable.from(mAddressDtoList);
    }

    public void updateOrInsertAddress(AccountAddressDto address) {
        if (mAddressDtoList.contains(address)) {
            mAddressDtoList.set(mAddressDtoList.indexOf(address), address);
        } else {
            mAddressDtoList.add(address);
        }
        saveAccountAddresses(mAddressDtoList);
    }

    public AccountAddressDto getAccountAddressFromPosition(int position) {
        return mAddressDtoList.get(position);
    }

    public void removeAddress(AccountAddressDto address) {
        mAddressDtoList.remove(address);
        saveAccountAddresses(mAddressDtoList);
    }

    //endregion


//    public Observable<ProductDto> getProductFromPosition(int position) {
//        // TODO: 27-Oct-16 temp sample mock data fix me (maybe load from db)
//        return Observable.just(mMockProductList.get(position - 1));
//    }
}
