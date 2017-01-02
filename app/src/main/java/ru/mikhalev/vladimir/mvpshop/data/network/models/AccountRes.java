package ru.mikhalev.vladimir.mvpshop.data.network.models;

import java.util.ArrayList;

import ru.mikhalev.vladimir.mvpshop.data.dto.AccountAddressDto;

/**
 * Developer Vladimir Mikhalev, 13.12.2016.
 */

public class AccountRes {
    private int id;
    private String fullname;
    private String phone;
    private String avatar;
    private boolean orderNotification;
    private boolean promoNotification;
    private ArrayList<AccountAddressDto> addresses;

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

    public ArrayList<AccountAddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<AccountAddressDto> addresses) {
        this.addresses = addresses;
    }
}
