package com.example.banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banhang.R;
import com.example.banhang.Retrofit2.APIUtils;
import com.example.banhang.Retrofit2.DataClient;
import com.example.banhang.Retrofit2.Sinhvien;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangnhapActivity extends AppCompatActivity {
    Button btndangki,btndangnhap;
    EditText edttk,edtmk;
    String taikhoan;
    String matkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        AnhXa();
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this,DangkiActivity.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edttk.getText().toString();
                matkhau = edtmk.getText().toString();

                if(taikhoan.length()>0 && matkhau.length()>0){
                    DataClient dataClient = APIUtils.getData();
                    Call<List<Sinhvien>> callback = dataClient.logindata(taikhoan,matkhau);
                    callback.enqueue(new Callback<List<Sinhvien>>() {
                        @Override
                        public void onResponse(Call<List<Sinhvien>> call, Response<List<Sinhvien>> response) {
                            ArrayList<Sinhvien> mangsinhvien = (ArrayList<Sinhvien>) response.body();
                            if(mangsinhvien.size()>0){
                                Intent intent = new Intent(DangnhapActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Sinhvien>> call, Throwable t) {
                            Toast.makeText(DangnhapActivity.this,"Khong co tai khoan",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private void AnhXa() {
        btndangnhap = findViewById(R.id.buttondangnhap);
        btndangki = findViewById(R.id.buttondangki);
        edtmk = findViewById(R.id.edittextmatkhau);
        edttk = findViewById(R.id.edittexttaikhoan);
    }
}