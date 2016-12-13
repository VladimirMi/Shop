package ru.mikhalev.vladimir.mvpauth.address;

import android.databinding.Bindable;

import ru.mikhalev.vladimir.mvpauth.BR;
import ru.mikhalev.vladimir.mvpauth.core.base.BaseViewModel;
import ru.mikhalev.vladimir.mvpauth.data.dto.Address;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AddressViewModel extends BaseViewModel {
    private int id;
    private String name;
    private String street;
    private String house;
    private String apartment;
    private String floor;
    private String comment;
    private boolean favorite;

    public AddressViewModel(Address address) {
        this.name = address.getName();
        this.street = address.getStreet();
        this.house = address.getHouse();
        this.apartment = address.getApartment();
        this.floor = address.getFloor();
        this.comment = address.getComment();
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
