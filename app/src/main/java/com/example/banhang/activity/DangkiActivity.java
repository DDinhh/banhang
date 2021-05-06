package com.example.banhang.activity;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    ImageView imgdangki;
    EditText edittextusername,editextpassword;
    Button btnhuy,btnxacnhan;
    int Request_code_image = 123;
    String realpath = "";
    String taikhoan;
    String matkhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        AnhXa();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangkiActivity.this,DangnhapActivity.class);
                startActivity(intent);
            }
        });
        imgdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_code_image);
            }
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = edittextusername.getText().toString();
                matkhau = editextpassword.getText().toString();

                if(taikhoan.length()>0 && matkhau.length()>0){
                    File file = new File(realpath);
                    String file_path = file.getAbsolutePath();
                    String[] mangtenfile = file_path.split("\\.");

                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"),file);

                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);
                    DataClient dataClient = APIUtils.getData();
                    retrofit2.Call<String> callback = dataClient.UploadPhoto(body);
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                            if(response != null){
                                String message = response.body();
                                if(message.length()>0){
                                    DataClient insertData = APIUtils.getData();
                                    Call<String> callback = insertData.InsertData(taikhoan,matkhau,APIUtils.Base_url + "image/" + message);
                                    callback.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, Response<String> response) {
                                            String result = response.body();
                                            if(result.equals("Success")){
                                                Toast.makeText(DangkiActivity.this,"Them thanh cong",Toast.LENGTH_SHORT).show();
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
                            Log.d("BBB",t.getMessage());
                        }
                    });
                }else{
                    Toast.makeText(DangkiActivity.this,"Hay nhap du thong tin",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Request_code_image && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgdangki.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AnhXa() {
        imgdangki = findViewById(R.id.imageviewdangki);
        editextpassword = findViewById(R.id.edittextdangkimatkhau);
        edittextusername = findViewById(R.id.edittextdangkitaikhoan);
        btnhuy = findViewById(R.id.buttonhuy);
        btnxacnhan = findViewById(R.id.buttonxacnhan);
    }
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}