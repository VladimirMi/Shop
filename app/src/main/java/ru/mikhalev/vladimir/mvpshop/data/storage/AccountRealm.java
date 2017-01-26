package ru.mikhalev.vladimir.mvpshop.data.storage;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;

/**
 * Developer Vladimir Mikhalev 21.01.2017
 */

public class AccountRealm extends RealmObject {

    @PrimaryKey
    private int id;
    private String fullName;
    private String phone;
    private String avatar;
    private boolean orderNotification;
    private boolean promoNotification;
    private RealmList<AddressRealm> addressRealms = new RealmList<>();

    public AccountRealm() {
    }

    public AccountRealm(AccountRes accountRes) {
        this.id = accountRes.getId();
        this.fullName = accountRes.getFullName();
        this.phone = accountRes.getPhone();
        this.avatar = accountRes.getAvatar();
        this.orderNotification = accountRes.isOrderNotification();
        this.promoNotification = accountRes.isPromoNotification();
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isOrderNotification() {
        return orderNotification;
    }

    public boolean isPromoNotification() {
        return promoNotification;
    }

    public RealmList<AddressRealm> getAddressRealms() {
        return addressRealms;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setOrderNotification(boolean orderNotification) {
        this.orderNotification = orderNotification;
    }

    public void setPromoNotification(boolean promoNotification) {
        this.promoNotification = promoNotification;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
