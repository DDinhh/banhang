package com.example.banhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhang.R;


public class XacthucActivity extends AppCompatActivity {
    Button b1,b2;
    Toolbar toolbardt;
    TextView textView;
    EditText e1,e2;
    String ngaysinh;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xacthuc);
        Anhxa();
        sharedPreferences=getSharedPreferences(ManhinhchoActivity.SHARE_PREF,MODE_PRIVATE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!e1.getText().toString().trim().isEmpty()&&!e2.getText().toString().trim().isEmpty()&&!textView.getText().toString().trim().isEmpty())
                {
                    for(int i=0;i<ManhinhchoActivity.listKhachhang.size();i++)

                    {

                        if(e1.getText().toString().trim().toLowerCase().equals(ManhinhchoActivity.listKhachhang.get(i).getName().toLowerCase())&&
                                e2.getText().toString().trim().equals(ManhinhchoActivity.listKhachhang.get(i).getAccount())&&
                                ngaysinh.equals(ManhinhchoActivity.listKhachhang.get(i).getDate())
                        )
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(XacthucActivity.this);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Mật khẩu: "+ManhinhchoActivity.listKhachhang.get(i).getPassword());
                            builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    finish();
                                }
                            });
                            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.show();
                        }else{

                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbardt = findViewById(R.id.toolbardangki);
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        b1=findViewById(R.id.buttonxacnhan);
        b2=findViewById(R.id.buttonhuy);
        e1=findViewById(R.id.edittextname);
        e2=findViewById(R.id.edittextdangkitaikhoan);
        textView=findViewById(R.id.edittextngaysinh);
    }private void showDialog()
    {
        Dialog dialog = new Dialog(XacthucActivity.this);
        dialog.setContentView(R.layout.dialog_calendar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        CalendarView c=dialog.findViewById(R.id.calendar);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                ngaysinh=(dayOfMonth)+"/"+(month+1)+"/"+year;
                textView.setText(""+ngaysinh);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}