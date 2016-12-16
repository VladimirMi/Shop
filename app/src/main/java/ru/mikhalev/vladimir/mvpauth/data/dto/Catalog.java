package ru.mikhalev.vladimir.mvpauth.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kenrube
 * @since 08.11.16
 */

public class Catalog {

    @SerializedName("goods")
    @Expose
    private List<ProductRes> goods = new ArrayList<>();

    public List<ProductRes> getGoods() {
        return goods;
    }
}
