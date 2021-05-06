package com.example.banhang.Retrofit2;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Sinhvien {

@SerializedName("Id")
@Expose
private String id;
@SerializedName("Taikhoan")
@Expose
private String taikhoan;
@SerializedName("Matkhau")
@Expose
private String matkhau;
@SerializedName("Hinhanh")
@Expose
private String hinhanh;

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

public String getHinhanh() {
return hinhanh;
}

public void setHinhanh(String hinhanh) {
this.hinhanh = hinhanh;
}

}