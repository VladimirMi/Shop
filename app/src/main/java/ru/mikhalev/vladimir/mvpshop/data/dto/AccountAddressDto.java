package ru.mikhalev.vladimir.mvpshop.data.dto;

import ru.mikhalev.vladimir.mvpshop.features.address.AddressViewModel;

/**
 * Developer Vladimir Mikhalev, 13.12.2016.
 */
public class AccountAddressDto {
    private int id;
    private String name;
    private String street;
    private String house;
    private String apartment;
    private String floor;
    private String comment;
    private boolean favorite;

    public AccountAddressDto(AddressViewModel addressViewModel) {
        this.id = addressViewModel.getId();
        this.name = addressViewModel.getName();
        this.street = addressViewModel.getStreet();
        this.house = addressViewModel.getHouse();
        this.apartment = addressViewModel.getApartment();
        this.floor = addressViewModel.getFloor();
        this.comment = addressViewModel.getComment();
    }

    public AccountAddressDto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
