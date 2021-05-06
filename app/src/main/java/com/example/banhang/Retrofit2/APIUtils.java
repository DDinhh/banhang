package com.example.banhang.Retrofit2;

public class APIUtils {
    public static final String Base_url = "http://dinhvan999.000webhostapp.com/Quanlisinhvien/";
    public static DataClient getData(){
        return RetrofitClient.getClient(Base_url).create(DataClient.class);
    }
}
