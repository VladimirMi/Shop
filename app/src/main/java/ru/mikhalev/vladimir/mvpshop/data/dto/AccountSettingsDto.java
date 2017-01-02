package ru.mikhalev.vladimir.mvpshop.data.dto;

import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountViewModel;

/**
 * Developer Vladimir Mikhalev, 28.12.2016.
 */

public class AccountSettingsDto {
    private boolean orderNotification;
    private boolean promoNotification;

    public AccountSettingsDto(boolean orderNotification, boolean promoNotification) {
        this.orderNotification = orderNotification;
        this.promoNotification = promoNotification;
    }

    public AccountSettingsDto(AccountRes accountRes) {
        this.orderNotification = accountRes.isOrderNotification();
        this.promoNotification = accountRes.isPromoNotification();
    }

    public AccountSettingsDto(AccountViewModel viewModel) {
        this.orderNotification = viewModel.isOrderNotification();
        this.promoNotification = viewModel.isPromoNotification();
    }

    public boolean isOrderNotification() {
        return orderNotification;
    }

    public boolean isPromoNotification() {
        return promoNotification;
    }
}
