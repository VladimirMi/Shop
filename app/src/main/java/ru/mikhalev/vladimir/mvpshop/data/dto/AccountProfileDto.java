package ru.mikhalev.vladimir.mvpshop.data.dto;

import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountViewModel;

/**
 * Developer Vladimir Mikhalev, 28.12.2016.
 */

public class AccountProfileDto {
    private String fullName;
    private String avatar;
    private String phone;

    public AccountProfileDto(String fullName, String phone, String avatar) {
        this.fullName = fullName;
        this.phone = phone;
        this.avatar = avatar;
    }

    public AccountProfileDto(AccountRes accountRes) {
        this.fullName = accountRes.getFullName();
        this.phone = accountRes.getPhone();
        this.avatar = accountRes.getAvatar();
    }

    public AccountProfileDto(AccountViewModel viewModel) {
        this.fullName = viewModel.getFullName();
        this.phone = viewModel.getPhone();
        this.avatar = viewModel.getAvatar();
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPhone() {
        return phone;
    }
}
