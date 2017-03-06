package ru.mikhalev.vladimir.mvpshop.data.network.models;

/**
 * Developer Vladimir Mikhalev, 06.03.2017.
 */

public class LoginReq {
    String login;
    String password;

    public LoginReq(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
