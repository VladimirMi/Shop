package ru.mikhalev.vladimir.mvpshop.features.account;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ru.mikhalev.vladimir.mvpshop.BR;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.AddressRealm;
import ru.mikhalev.vladimir.mvpshop.utils.RealmUtils;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountViewModel extends BaseObservable {

    @IntDef({
            STATE.PREVIEW,
            STATE.EDIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        int PREVIEW = 0;
        int EDIT = 1;
    }

    private AccountRealm mAccountRealm;
    @Bindable private String fullName;
    @Bindable private String phone;
    @Bindable private String avatar;
    @Bindable private boolean promoNotification;
    @Bindable private boolean orderNotification;
    @Bindable private @STATE int viewState;

    public AccountViewModel(AccountRealm accountRealm) {
        update(accountRealm);
    }

    public void update(AccountRealm accountRealm) {
        setFullName(accountRealm.getFullName());
        setPhone(accountRealm.getPhone());
        setAvatar(accountRealm.getAvatar());
        setOrderNotification(accountRealm.isOrderNotification());
        setPromoNotification(accountRealm.isPromoNotification());
    }

    public AccountRealm getAccountRealm() {
        return mAccountRealm;
    }

    private void save() {
        RealmUtils.executeTransactionAsync(realm -> {
            realm.insertOrUpdate(mAccountRealm);
        });
    }

    public void changeState() {
        if (viewState == AccountViewModel.STATE.EDIT) {
            setViewState(AccountViewModel.STATE.PREVIEW);
            save();
        } else {
            setViewState(AccountViewModel.STATE.EDIT);
        }
    }

    public void removeAddress(AddressRealm addressRealm) {
        mAccountRealm.getAddressRealms().remove(addressRealm);
        save();
    }

    public void setFullName(String fullName) {
        if (!this.fullName.equals(fullName)) {
            this.fullName = fullName;
            mAccountRealm.setFullName(fullName);
            notifyPropertyChanged(BR.fullName);
        }
    }

    public void setPhone(String phone) {
        if (!this.phone.equals(phone)) {
            this.phone = phone;
            mAccountRealm.setPhone(phone);
        }
    }

    public void setAvatar(String avatar) {
        if (!this.avatar.equals(avatar)) {
            this.avatar = avatar;
            mAccountRealm.setAvatar(avatar);
        }
    }

    public void setPromoNotification(boolean promoNotification) {
        if (this.promoNotification != promoNotification) {
            this.promoNotification = promoNotification;
            mAccountRealm.setPromoNotification(promoNotification);
            save();
        }
    }

    public void setOrderNotification(boolean orderNotification) {
        if (this.orderNotification != orderNotification) {
            this.orderNotification = orderNotification;
            mAccountRealm.setOrderNotification(orderNotification);
            save();
        }
    }

    public void setViewState(@STATE int viewState) {
        this.viewState = viewState;
        notifyPropertyChanged(BR.viewState);
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

    public boolean isPromoNotification() {
        return promoNotification;
    }

    public boolean isOrderNotification() {
        return orderNotification;
    }

    public
    @STATE
    int getViewState() {
        return viewState;
    }
}
