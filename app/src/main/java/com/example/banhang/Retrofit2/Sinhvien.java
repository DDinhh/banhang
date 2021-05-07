package com.example.banhang.Retrofit2;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sinhvien {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tk")
    @Expose
    private String taikhoan;
    @SerializedName("mk")
    @Expose
    private String matkhau;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("ngaysinh")
    @Expose
    private String ngaysinh;
    @SerializedName("diachi")
    @Expose
    private String diachi;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}