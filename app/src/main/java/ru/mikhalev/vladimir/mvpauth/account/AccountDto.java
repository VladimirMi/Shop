package ru.mikhalev.vladimir.mvpauth.account;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ru.mikhalev.vladimir.mvpauth.address.AddressDto;

/**
 * Developer Vladimir Mikhalev 29.11.2016
 */

public class AccountDto implements Parcelable {
    private int id;
    private String fullname;
    private String avatar;
    private String phone;
    private boolean orderNotification;
    private boolean promoNotification;
    private ArrayList<AddressDto> addresses;

    public AccountDto(int id, String fullname, String avatar, String phone, boolean orderNotification, boolean promoNotification, ArrayList<AddressDto> addresses) {
        this.id = id;
        this.fullname = fullname;
        this.avatar = avatar;
        this.phone = phone;
        this.orderNotification = orderNotification;
        this.promoNotification = promoNotification;
        this.addresses = addresses;
    }

    //region ==================== Getters and setters ========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isOrderNotification() {
        return orderNotification;
    }

    public void setOrderNotification(boolean orderNotification) {
        this.orderNotification = orderNotification;
    }

    public boolean isPromoNotification() {
        return promoNotification;
    }

    public void setPromoNotification(boolean promoNotification) {
        this.promoNotification = promoNotification;
    }

    public ArrayList<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<AddressDto> addresses) {
        this.addresses = addresses;
    }

    //endregion

    //region ==================== Parcelable ========================

    protected AccountDto(Parcel in) {
        id = in.readInt();
        fullname = in.readString();
        avatar = in.readString();
        phone = in.readString();
        orderNotification = in.readByte() != 0;
        promoNotification = in.readByte() != 0;
        addresses = in.createTypedArrayList(AddressDto.CREATOR);
    }

    public static final Creator<AccountDto> CREATOR = new Creator<AccountDto>() {
        @Override
        public AccountDto createFromParcel(Parcel in) {
            return new AccountDto(in);
        }

        @Override
        public AccountDto[] newArray(int size) {
            return new AccountDto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(fullname);
        parcel.writeString(avatar);
        parcel.writeString(phone);
        parcel.writeByte((byte) (orderNotification ? 1 : 0));
        parcel.writeByte((byte) (promoNotification ? 1 : 0));
        parcel.writeTypedList(addresses);
    }

    //endregion
}
