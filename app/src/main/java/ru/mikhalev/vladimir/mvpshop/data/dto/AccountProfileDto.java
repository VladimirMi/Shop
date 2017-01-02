package ru.mikhalev.vladimir.mvpshop.data.dto;

import ru.mikhalev.vladimir.mvpshop.data.network.models.AccountRes;
import ru.mikhalev.vladimir.mvpshop.features.account.AccountViewModel;

/**
 * Developer Vladimir Mikhalev, 28.12.2016.
 */

public class AccountProfileDto {
    private String fullname;
    private String avatar;
    private String phone;

    public AccountProfileDto(String fullname, String phone, String avatar) {
        this.fullname = fullname;
        this.phone = phone;
        this.avatar = avatar;
    }

    public AccountProfileDto(AccountRes accountRes) {
        this.fullname = accountRes.getFullname();
        this.phone = accountRes.getPhone();
        this.avatar = accountRes.getAvatar();
    }

    public AccountProfileDto(AccountViewModel viewModel) {
        this.fullname = viewModel.getFullname();
        this.phone = viewModel.getPhone();
        this.avatar = viewModel.getAvatar();
    }

    public String getFullname() {
        return fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPhone() {
        return phone;
    }
}
