package ru.mikhalev.vladimir.mvpauth.address;

import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.mikhalev.vladimir.mvpauth.BR;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseViewModel;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AddressViewModel extends BaseViewModel {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("street")
    @Expose
    private String street;

    @SerializedName("house")
    @Expose
    private String house;

    @SerializedName("apartment")
    @Expose
    private String apartment;

    @SerializedName("floor")
    @Expose
    private String floor;

    @SerializedName("comment")
    @Expose
    private String comment;

    private boolean favorite;

    public AddressViewModel(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("ул. %s  %s - %s, %s этаж", street, house, apartment, floor);
    }

    //region ==================== Getters and setters ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
        notifyPropertyChanged(BR.street);
    }

    @Bindable
    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
        notifyPropertyChanged(BR.house);
    }

    @Bindable
    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
        notifyPropertyChanged(BR.apartment);
    }

    @Bindable
    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
        notifyPropertyChanged(BR.floor);
    }

    @Bindable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
        notifyPropertyChanged(BR.comment);
    }

    //endregion
}
