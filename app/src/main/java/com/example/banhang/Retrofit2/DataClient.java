package com.example.banhang.Retrofit2;


import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    @Multipart
    @POST("uploadhinhanh.php")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);


    @FormUrlEncoded
    @POST("insert.php")
    Call<String> InsertData(@Field("taikhoan") String taikhoan,
                            @Field("matkhau") String matkhau,
                            @Field("hinhanh") String hinhanh);

    @FormUrlEncoded
    @POST("login.php")
    Call<List<Sinhvien>> logindata(@Field("taikhoan") String taikhoan,
                                   @Field("matkhau") String matkhau);
}
