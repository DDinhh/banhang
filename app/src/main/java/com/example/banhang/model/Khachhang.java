package com.example.banhang.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Khachhang  implements Parcelable {
    private  int id;
    private  String account;
    private String password;
    private String name;

    private String address;
    private String date;

    public Khachhang(int id, String account, String password, String name, String address, String date) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.name = name;
        this.address = address;
        this.date = date;
    }

    protected Khachhang(Parcel in) {
        id = in.readInt();
        account = in.readString();
        password = in.readString();
        name = in.readString();
        address = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(account);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Khachhang> CREATOR = new Creator<Khachhang>() {
        @Override
        public Khachhang createFromParcel(Parcel in) {
            return new Khachhang(in);
        }

        @Override
        public Khachhang[] newArray(int size) {
            return new Khachhang[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
