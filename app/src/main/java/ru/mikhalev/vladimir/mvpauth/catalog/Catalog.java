package ru.mikhalev.vladimir.mvpauth.catalog;

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
    private List<ProductViewModel> goods = new ArrayList<>();

    public List<ProductViewModel> getGoods() {
        return goods;
    }
}
