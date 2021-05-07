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

import com.example.banhang.R;
import com.example.banhang.Retrofit2.APIUtils;
import com.example.banhang.Retrofit2.DataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangkiActivity extends AppCompatActivity {
    Toolbar toolbardt;
    EditText edittextusername, editextpassword;
    Button btnhuy, btnxacnhan;
    EditText edname, edaddress;
    TextView eddate;
    String realpath = "";
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
                    File file = new File(realpath);
                    String file_path = file.getAbsolutePath();
                    String[] mangtenfile = file_path.split("\\.");

                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);

                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
                    DataClient dataClient = APIUtils.getData();
                    retrofit2.Call<String> callback = dataClient.UploadPhoto(body);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                            if (response != null) {
                                String message = response.body();
                                if (message.length() > 0) {
                                    DataClient insertData = APIUtils.getData();
                                    Call<String> callback = insertData.InsertData(taikhoan, matkhau, APIUtils.Base_url + "image/" + message);
                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String result = response.body();
                                            if (result.equals("Success")) {
                                                Toast.makeText(DangkiActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<String> call, Throwable t) {
                            Log.d("BBB", t.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(DangkiActivity.this, "Hay nhap du thong tin", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
    private void showDialog()
    {
        Dialog dialog = new Dialog(DangkiActivity.this);
        dialog.setContentView(R.layout.dialog_calendar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        CalendarView c=dialog.findViewById(R.id.calendar);
        c.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                ngaysinh=(month+1)+"/"+month+"/"+year;
                eddate.setText(ngaysinh);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}