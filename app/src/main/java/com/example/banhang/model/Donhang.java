package com.example.banhang.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Donhang implements Parcelable {
    private int id;
    private String name;
    private String phone;
    private String address;

    private String date;
    private String price;
    private String note;
    private int stt;

    public Donhang() {
    }

    public Donhang(int id, String name, String phone, String address, String date, String price, String note, int stt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.price = price;
        this.note = note;
        this.stt = stt;
    }

    protected Donhang(Parcel in) {
        id = in.readInt();
        name = in.readString();
        phone = in.readString();
        address = in.readString();
        date = in.readString();
        price = in.readString();
        note = in.readString();
        stt = in.readInt();
    }

    public static final Creator<Donhang> CREATOR = new Creator<Donhang>() {
        @Override
        public Donhang createFromParcel(Parcel in) {
            return new Donhang(in);
        }

        @Override
        public Donhang[] newArray(int size) {
            return new Donhang[size];
        }
    };

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(address);
        parcel.writeString(date);
        parcel.writeString(price);
        parcel.writeString(note);
        parcel.writeInt(stt);
    }
}
