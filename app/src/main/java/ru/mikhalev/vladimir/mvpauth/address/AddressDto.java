package ru.mikhalev.vladimir.mvpauth.address;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AddressDto implements Parcelable {
    private int id;
    private String name;

    @SerializedName("street")
    @Expose
    private String street;

    private String house;
    private String apartment;
    private int floor;

    @SerializedName("comment")
    @Expose
    private String comment;

    private boolean favorite;

    public AddressDto(int id, String name, String street, String house, String apartment, int floor, String comment) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.floor = floor;
        this.comment = comment;
    }

    //region ==================== Getters and setters ========================

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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //endregion

    //region ==================== Parcelable ========================

    protected AddressDto(Parcel in) {
        id = in.readInt();
        name = in.readString();
        street = in.readString();
        house = in.readString();
        apartment = in.readString();
        floor = in.readInt();
        comment = in.readString();
        favorite = in.readByte() != 0;
    }

    public static final Creator<AddressDto> CREATOR = new Creator<AddressDto>() {
        @Override
        public AddressDto createFromParcel(Parcel in) {
            return new AddressDto(in);
        }

        @Override
        public AddressDto[] newArray(int size) {
            return new AddressDto[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(street);
        parcel.writeString(house);
        parcel.writeString(apartment);
        parcel.writeInt(floor);
        parcel.writeString(comment);
        parcel.writeByte((byte) (favorite ? 1 : 0));
    }

    //endregion
}
