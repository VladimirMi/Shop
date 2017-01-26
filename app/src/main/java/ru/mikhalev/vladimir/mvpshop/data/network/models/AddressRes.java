package ru.mikhalev.vladimir.mvpshop.data.network.models;

/**
 * Developer Vladimir Mikhalev 21.01.2017
 */

public class AddressRes {

    private String id;
    private String name;
    private String street;
    private String house;
    private String apartment;
    private String floor;
    private String comment;
    private boolean favorite;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    public String getFloor() {
        return floor;
    }

    public String getComment() {
        return comment;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
