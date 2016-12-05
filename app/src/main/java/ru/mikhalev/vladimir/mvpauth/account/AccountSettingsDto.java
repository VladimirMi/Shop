package ru.mikhalev.vladimir.mvpauth.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

import ru.mikhalev.vladimir.mvpauth.core.managers.PreferencesManager;

/**
 * Developer Vladimir Mikhalev, 04.12.2016.
 */
public class AccountSettingsDto {

    @SerializedName("orderNotification")
    @Expose
    private boolean orderNotification;

    @SerializedName("promoNotification")
    @Expose
    private boolean promoNotification;

    public AccountSettingsDto(boolean orderNotification, boolean promoNotification) {
        this.orderNotification = orderNotification;
        this.promoNotification = promoNotification;
    }

    public AccountSettingsDto(Map<String, Boolean> accountSettings) {
        this.orderNotification = accountSettings.get(PreferencesManager.ACCOUNT.NOTIFICATION_ORDER_KEY);
        this.promoNotification = accountSettings.get(PreferencesManager.ACCOUNT.NOTIFICATION_PROMO_KEY);
    }

    public boolean isOrderNotification() {
        return orderNotification;
    }

    public boolean isPromoNotification() {
        return promoNotification;
    }
}
