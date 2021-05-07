package com.example.banhang.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banhang.R;
import com.example.banhang.model.Khachhang;
import com.example.banhang.ultil.CheckConnection;
import com.example.banhang.ultil.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class DangkiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    EditText edittextusername, editextpassword;
    Button btnhuy, btnxacnhan;
    EditText edname, edaddress;
    TextView eddate;

    String taikhoan;
    String matkhau, hoten, diachi, ngaysinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        AnhXa();
        eddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                taikhoan = edittextusername.getText().toString().trim();
                matkhau = editextpassword.getText().toString().trim();
                hoten = edname.getText().toString().trim();
                diachi = edaddress.getText().toString().trim();
                ngaysinh = eddate.getText().toString().trim();
                if (taikhoan.length() > 0 && matkhau.length() > 0 && hoten.length() > 0 && diachi.length() > 0 && ngaysinh.length() > 0) {
                    for(int i=0;i<ManhinhchoActivity.listKhachhang.size();i++)
                    {

                        if(!taikhoan.equals(ManhinhchoActivity.listKhachhang.get(i).getAccount()))
                        {
                            RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.Duongdandangky, new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("1")) {
                                        Toast.makeText(getApplicationContext(),"Đăng ký tài khoản thành công",Toast.LENGTH_SHORT).show();
                                        ManhinhchoActivity.listKhachhang.clear();
                                        getKhachhang();
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("ten", hoten);
                                    hashMap.put("tk", taikhoan);

                                    hashMap.put("mk", matkhau);
                                    hashMap.put("ngaysinh", ngaysinh);
                                    hashMap.put("diachi", diachi);

                                    return hashMap;
                                }
                            };
                            requestQueue1.add(stringRequest1);
                        }
                    }

                } else {
                    Toast.makeText(DangkiActivity.this, "Hãy nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void getKhachhang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdankhachhang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){

                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id=jsonObject.getInt("id");
                            String account = jsonObject.getString("tk");
                            String password = jsonObject.getString("mk");
                            String name = jsonObject.getString("ten");
                            String address = jsonObject.getString("diachi");
                            String date = jsonObject.getString("ngaysinh");
                            ManhinhchoActivity.listKhachhang.add(new Khachhang(id,account,password,name,address,date));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void AnhXa() {

        toolbardt = findViewById(R.id.toolbardangki);
        setSupportActionBar(toolbardt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbardt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editextpassword = findViewById(R.id.edittextdangkimatkhau);
        edittextusername = findViewById(R.id.edittextdangkitaikhoan);
        edaddress = findViewById(R.id.edittextdiachi);
        edname = findViewById(R.id.edittextname);
        eddate = findViewById(R.id.edittextngaysinh);
        btnhuy = findViewById(R.id.buttonhuy);
        btnxacnhan = findViewById(R.id.buttonxacnhan);
    }

    private void showDialog() {
        Dialog dialog = new Dialog(DangkiActivity.this);
        dialog.setContentView(R.layout.dialog_calendar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        CalendarView c = dialog.findViewById(R.id.calendar);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                ngaysinh = (dayOfMonth) + "/" + (month + 1) + "/" + year;
                eddate.setText(ngaysinh);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}