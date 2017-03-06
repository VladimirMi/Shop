package ru.mikhalev.vladimir.mvpshop.data.network.models;

import com.google.gson.annotations.SerializedName;

/**
 * Developer Vladimir Mikhalev, 06.03.2017.
 */

public class LoginRes {
    @SerializedName("_id")
    String id;
    String token;
}
