package ru.mikhalev.vladimir.mvpauth.account;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.BR;
import ru.mikhalev.vladimir.mvpauth.address.AddressDto;
import ru.mikhalev.vladimir.mvpauth.core.managers.PreferencesManager;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountDto extends BaseObservable {
    private int id;

    @SerializedName("fullname")
    @Expose
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
    private ArrayList<AddressDto> addresses;

    public AccountDto(Map<String, String> accountProfileInfo, Map<String, Boolean> accountSettings) {
        this.fullname = accountProfileInfo.get(PreferencesManager.ACCOUNT.FULL_NAME_KEY);
        this.avatar = accountProfileInfo.get(PreferencesManager.ACCOUNT.AVATAR_KEY);
        this.phone = accountProfileInfo.get(PreferencesManager.ACCOUNT.PHONE_KEY);
        this.orderNotification = accountSettings.get(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY);
        this.promoNotification = accountSettings.get(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY);
    }

    //region ==================== Getters and setters ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
        notifyPropertyChanged(BR.fullname);
    }

    @Bindable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyPropertyChanged(BR.avatar);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public boolean isOrderNotification() {
        return orderNotification;
    }

    public void setOrderNotification(boolean orderNotification) {
        this.orderNotification = orderNotification;
        notifyPropertyChanged(BR.orderNotification);
    }

    @Bindable
    public boolean isPromoNotification() {
        return promoNotification;
    }

    public void setPromoNotification(boolean promoNotification) {
        this.promoNotification = promoNotification;
        notifyPropertyChanged(BR.promoNotification);
    }

    public ArrayList<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<AddressDto> addresses) {
        this.addresses = addresses;
    }

    //endregion
}
