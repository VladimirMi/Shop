package ru.mikhalev.vladimir.mvpauth.core.managers;


import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.mikhalev.vladimir.mvpauth.R;
import ru.mikhalev.vladimir.mvpauth.account.AccountViewModel;
import ru.mikhalev.vladimir.mvpauth.address.AddressViewModel;
import ru.mikhalev.vladimir.mvpauth.catalog.Catalog;
import ru.mikhalev.vladimir.mvpauth.catalog.ProductViewModel;
import ru.mikhalev.vladimir.mvpauth.core.App;
import ru.mikhalev.vladimir.mvpauth.core.di.DaggerService;
import ru.mikhalev.vladimir.mvpauth.core.di.components.DataManagerComponent;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.LocaleModule;
import ru.mikhalev.vladimir.mvpauth.core.di.modules.NetworkModule;
import ru.mikhalev.vladimir.mvpauth.core.network.api.RestService;
import ru.mikhalev.vladimir.mvpauth.core.utils.RawUtils;

public class DataManager {
    private static final String TAG = "DataManager";
    @Inject
    PreferencesManager mPreferencesManager;
    @Inject
    RestService mRestService;
    @Inject
    Context mContext;


    private List<ProductViewModel> mMockProductList = new ArrayList<>();
    private AccountViewModel mockAccount;

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
        return true;
    }

    public void loginUser(String email, String password) {
        // TODO: 10/22/16 implement auth
    }

    public ProductViewModel getProductById(int productId) {
        // TODO: 27-Oct-16 temp sample mock data fix me (maybe load from db)
        return mMockProductList.get(productId-1);
    }

    public void updateProduct(ProductViewModel product) {
        // TODO: 27-Oct-16 update product count or status (something in product) save in DB
    }

    public List<ProductViewModel> getProductList() {
        // TODO: 27-Oct-16 load product list from anywhere
        return mMockProductList;
    }

    private void generateMockCatalog() {
        mMockProductList = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.goods), Catalog.class).getGoods();
    }

    private void generateMockAccount() {
        mockAccount = new Gson().fromJson(RawUtils.getJson(mContext, R.raw.account), AccountViewModel.class);
        saveProfileInfo(mockAccount.getFullname(), mockAccount.getPhone());
        saveAvatarPhoto(mockAccount.getAvatar());
        saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY, mockAccount.isOrderNotification());
        saveAccountSetting(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY, mockAccount.isPromoNotification());
    }

    // TODO: 01.12.2016 implement real
    public ArrayList<AddressViewModel> getAccountAddresses() {
        return mockAccount.getAddresses();
    }

    public void updateOrInsertAddress(AddressViewModel address) {
        if (mockAccount.getAddresses().contains(address)) {
            mockAccount.getAddresses().set(mockAccount.getAddresses().indexOf(address), address);
        } else {
            mockAccount.getAddresses().add(address);
        }
    }

    public void removeAddress(AddressViewModel addressViewModel) {
        mockAccount.getAddresses().remove(addressViewModel);
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
