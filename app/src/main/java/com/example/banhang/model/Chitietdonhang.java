package com.example.banhang.model;

public class Chitietdonhang {
    private int id;
    private String hinhanh;
    private String ten;
    private String soluong;
    private String gia;

    public Chitietdonhang(int id, String hinhanh, String ten,String soluong, String gia) {
        this.id = id;
        this.hinhanh = hinhanh;
        this.ten=ten;
        this.soluong = soluong;
        this.gia = gia;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
