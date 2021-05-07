package com.example.banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    Button btndangnhap;
    TextView btndangki, tvquenmatkhau;
    EditText edttk, edtmk;
    String taikhoan;
    String matkhau;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<Sinhvien> mangsinhvien=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);
        AnhXa();
        sharedPreferences = getSharedPreferences(ManhinhchoActivity.SHARE_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        tvquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),XacthucActivity.class));
            }
        });
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this, DangkiActivity.class);
                startActivity(intent);
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edttk.getText().toString();
                matkhau = edtmk.getText().toString();

                if (taikhoan.length() > 0 && matkhau.length() > 0) {
                    DataClient dataClient = APIUtils.getData();
                    Call<List<Sinhvien>> callback = dataClient.logindata(taikhoan, matkhau);
                    callback.enqueue(new Callback<List<Sinhvien>>() {
                        @Override
                        public void onResponse(Call<List<Sinhvien>> call, Response<List<Sinhvien>> response) {
                            mangsinhvien = (ArrayList<Sinhvien>) response.body();
                            if (mangsinhvien.size() > 0) {
                                for (int i = 0; i < mangsinhvien.size(); i++) {
                                    editor.putString(ManhinhchoActivity.ID, mangsinhvien.get(i).getId());
                                    editor.putString(ManhinhchoActivity.NAME, mangsinhvien.get(i).getTen());
                                    editor.putString(ManhinhchoActivity.ACCOUNT, mangsinhvien.get(i).getTaikhoan());
                                    editor.putString(ManhinhchoActivity.PASSWORD, mangsinhvien.get(i).getMatkhau());
                                    editor.putString(ManhinhchoActivity.DATE, mangsinhvien.get(i).getNgaysinh());
                                    editor.putString(ManhinhchoActivity.ADDRESS, mangsinhvien.get(i).getDiachi());
                                    editor.apply();
                                }
                                Log.e("AA",mangsinhvien.size()+"");
                                Intent intent = new Intent(DangnhapActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Lỗi",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Sinhvien>> call, Throwable t) {
                            Toast.makeText(DangnhapActivity.this, "Tài khoản / mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(DangnhapActivity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        tvquenmatkhau = findViewById(R.id.textviewquenmatkhau);
        SpannableString ss = new SpannableString(tvquenmatkhau.getText().toString());
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ss.setSpan(underlineSpan, 0, tvquenmatkhau.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvquenmatkhau.setText(ss);
        btndangnhap = findViewById(R.id.buttondangnhap);
        btndangki = findViewById(R.id.buttondangki);
        edtmk = findViewById(R.id.edittextmatkhau);
        edttk = findViewById(R.id.edittexttaikhoan);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}