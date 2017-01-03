package ru.mikhalev.vladimir.mvpshop.features.account;

import android.databinding.Bindable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import ru.mikhalev.vladimir.mvpshop.BR;
import ru.mikhalev.vladimir.mvpshop.core.BaseViewModel;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountProfileDto;
import ru.mikhalev.vladimir.mvpshop.data.dto.AccountSettingsDto;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountViewModel extends BaseViewModel {

    @IntDef({STATE.PREVIEW, STATE.EDIT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface STATE {
        int PREVIEW = 0;
        int EDIT = 1;
    }

    private int id;
    private String fullname;
    private String phone;
    private String avatar;
    private boolean orderNotification;
    private boolean promoNotification;
    private int viewState = STATE.PREVIEW;

    public AccountViewModel(AccountProfileDto accountProfile, AccountSettingsDto accountSettings) {
        this.fullname = accountProfile.getFullName();
        this.phone = accountProfile.getPhone();
        this.avatar = accountProfile.getAvatar();
        this.orderNotification = accountSettings.isOrderNotification();
        this.promoNotification = accountSettings.isPromoNotification();
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

    @Bindable
    public
    @STATE
    int getViewState() {
        return viewState;
    }

    public void setViewState(@STATE int viewState) {
        this.viewState = viewState;
        notifyPropertyChanged(BR.viewState);
    }

    public void update(AccountViewModel viewModel) {
        if (!fullname.equals(viewModel.getFullname())) {
            setFullname(viewModel.getFullname());
        }
        if (!phone.equals(viewModel.getPhone())) {
            setPhone(viewModel.getPhone());
        }
        if (!avatar.equals(viewModel.getAvatar())) {
            setAvatar(viewModel.getAvatar());
        }
        if (orderNotification != viewModel.isOrderNotification()) {
            setOrderNotification(viewModel.isOrderNotification());
        }
        if (promoNotification != viewModel.isPromoNotification()) {
            setPromoNotification(viewModel.isPromoNotification());
        }
        if (viewState != viewModel.getViewState()) {
            setViewState(viewModel.getViewState());
        }
    }

    //endregion
}
