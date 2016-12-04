package ru.mikhalev.vladimir.mvpauth.account;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.BR;
import ru.mikhalev.vladimir.mvpauth.address.AddressViewModel;
import ru.mikhalev.vladimir.mvpauth.core.managers.PreferencesManager;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountViewModel extends BaseObservable {
    private int id;

    @SerializedName("fullname")
    @Expose
    @Bindable
    private String fullname;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("orderNotification")
    @Expose
    private boolean orderNotification;

    @SerializedName("promoNotification")
    @Expose
    private boolean promoNotification;

    @SerializedName("addresses")
    @Expose
    private ArrayList<AddressViewModel> addresses;

    public AccountViewModel(Map<String, String> accountProfileInfo, ArrayList<AddressViewModel> accountAddresses, Map<String, Boolean> accountSettings) {
        this.fullname = accountProfileInfo.get(PreferencesManager.ACCOUNT.FULL_NAME_KEY);
        this.avatar = accountProfileInfo.get(PreferencesManager.ACCOUNT.AVATAR_KEY);
        this.phone = accountProfileInfo.get(PreferencesManager.ACCOUNT.PHONE_KEY);
        this.orderNotification = accountSettings.get(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY);
        this.promoNotification = accountSettings.get(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY);
        this.addresses = accountAddresses;
    }

    //region ==================== Getters and setters ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
        notifyPropertyChanged(BR.fullname);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isOrderNotification() {
        return orderNotification;
    }

    public void setOrderNotification(boolean orderNotification) {
        this.orderNotification = orderNotification;
    }

    public boolean isPromoNotification() {
        return promoNotification;
    }

    public void setPromoNotification(boolean promoNotification) {
        this.promoNotification = promoNotification;
    }

    public ArrayList<AddressViewModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<AddressViewModel> addresses) {
        this.addresses = addresses;
    }

    //endregion
}
