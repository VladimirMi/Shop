package ru.mikhalev.vladimir.mvpshop.data.storage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.mikhalev.vladimir.mvpshop.data.network.models.AddressRes;

/**
 * Developer Vladimir Mikhalev 21.01.2017
 */
public class AddressRealm extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String street;
    private String house;
    private String apartment;
    private String floor;
    private String comment;
    private boolean favorite;

    public AddressRealm() {
    }

    public AddressRealm(String id) {
        this.id = id;
    }

    public AddressRealm(AddressRes addressRes) {
        this.id = addressRes.getId();
        this.name = addressRes.getName();
        this.street = addressRes.getStreet();
        this.house = addressRes.getHouse();
        this.apartment = addressRes.getApartment();
        this.floor = addressRes.getFloor();
        this.comment = addressRes.getComment();
        this.favorite = addressRes.isFavorite();
    }

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

    @Override
    public String toString() {
        return String.format("ул. %s  %s - %s, %s этаж", street, house, apartment, floor);
    }
}
