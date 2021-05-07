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


public class DangnhapActivity extends AppCompatActivity {
    Button btndangnhap,btnhuy;
    TextView btndangki, tvquenmatkhau;
    EditText edttk, edtmk;
    String taikhoan;
    String matkhau;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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
                taikhoan = edttk.getText().toString().trim();
                matkhau = edtmk.getText().toString().trim();

                if (taikhoan.length() > 0 && matkhau.length() > 0) {
                    for(int i=0;i<ManhinhchoActivity.listKhachhang.size();i++) {
                        if(taikhoan.equals(ManhinhchoActivity.listKhachhang.get(i).getAccount())&&
                        matkhau.equals(ManhinhchoActivity.listKhachhang.get(i).getPassword())) {
                            editor.putInt(ManhinhchoActivity.ID, ManhinhchoActivity.listKhachhang.get(i).getId());
                            editor.putString(ManhinhchoActivity.NAME, ManhinhchoActivity.listKhachhang.get(i).getName());
                            editor.putString(ManhinhchoActivity.ACCOUNT, ManhinhchoActivity.listKhachhang.get(i).getAccount());
                            editor.putString(ManhinhchoActivity.PASSWORD, ManhinhchoActivity.listKhachhang.get(i).getPassword());
                            editor.putString(ManhinhchoActivity.DATE, ManhinhchoActivity.listKhachhang.get(i).getDate());
                            editor.putString(ManhinhchoActivity.ADDRESS, ManhinhchoActivity.listKhachhang.get(i).getAddress());
                            editor.apply();
                            Toast.makeText(DangnhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    }
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
        btnhuy=findViewById(R.id.buttonhuy);
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}