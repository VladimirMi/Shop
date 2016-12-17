package ru.mikhalev.vladimir.mvpauth.data.managers;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.catalog.ProductDto;
import ru.mikhalev.vladimir.mvpauth.core.App;
import ru.mikhalev.vladimir.mvpauth.core.base.RestCallTransformer;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.DataManagerComponent;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpauth.data.dto.Account;
import ru.mikhalev.vladimir.mvpauth.data.dto.Address;
import ru.mikhalev.vladimir.mvpauth.data.dto.ProductLocalInfo;
import ru.mikhalev.vladimir.mvpauth.data.dto.ProductRes;
import ru.mikhalev.vladimir.mvpauth.data.network.api.RestService;
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


    private List<ProductDto> mMockProductList = new ArrayList<>();
    private Account mMockAccount;

    public DataManager() {
        DaggerService.createDaggerComponent(DataManagerComponent.class,
                App.getAppComponent(),
                new LocaleModule(),
                new NetworkModule()).inject(this);
        generateMockCatalog();
        generateMockAccount();
    }

    public String getLastProductUpdate() {
        return mPreferencesManager.getLastProductUpdate();
    }

    public String getAuthTokenPref() {
        return mPreferencesManager.getAuthTokenPref();
    }

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }

    public Observable<ProductDto> getProductFromPosition(int position) {
        // TODO: 27-Oct-16 temp sample mock data fix me (maybe load from db)
        return Observable.just(mMockProductList.get(position - 1));
    }

    public void updateProduct(ProductRes productRes) {
        // TODO: 27-Oct-16 update productRes count or status (something in productRes) save in DB
    }

    public Observable<ProductRes> getProductsFromNetwork() {
        return mRestService.getProductResponse(getLastProductUpdate())
                .compose(new RestCallTransformer<>())
                .flatMap(Observable::from)
                .doOnNext(productRes -> Timber.e("getProductsFromNetwork: " + productRes.getProductName()))
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

    public List<ProductDto> getProductsFromDB() {
        return mMockProductList;
    }

    private void saveInDB(ProductRes productRes) {

    }

    private void generateMockCatalog() {
        Type listType = new TypeToken<List<ProductRes>>() {
        }.getType();
        List<ProductRes> mockProducts = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.goods), listType);
        Observable<ProductRes> mockProductsObs = Observable.from(mockProducts);
        Observable<ProductLocalInfo> local = mockProductsObs.flatMap(this::getProductLocalInfoObs);
        Observable.zip(mockProductsObs, local, ProductDto::new)
                .subscribe(mMockProductList::add);
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

    public Observable<ProductLocalInfo> getProductLocalInfoObs(ProductRes productRes) {
        return Observable.just(mPreferencesManager.getProductLocalInfo(productRes.getRemoteId()))
                .flatMap(productLocalInfo -> productLocalInfo == null ?
                        Observable.just(new ProductLocalInfo(productRes.getRemoteId())) :
                        Observable.just(productLocalInfo));
    }
}
