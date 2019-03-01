package com.aquaheyseller.network_call.request_model;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String latitude;
    private String longitude;


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public static Creator<Address> getCREATOR() {
        return CREATOR;
    }

    public Address(String address, String city, String state, String pincode, String latitude, String longitude) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Address(Parcel in) {
        address = in.readString();
        city = in.readString();
        state = in.readString();
        pincode = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(pincode);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}