package com.example.banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.banhang.R;
import com.example.banhang.model.Khachhang;
import com.example.banhang.model.Sanpham;
import com.example.banhang.ultil.CheckConnection;
import com.example.banhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManhinhchoActivity extends AppCompatActivity {
    public static final String SHARE_PREF="share";
    public static final String ID="id";
    public static final String ACCOUNT="account";
    public static final String PASSWORD="password";
    public static final String DATE="date";
    public static final String NAME="name";
    public static final String ADDRESS="email";
    public static final ArrayList<Khachhang> listKhachhang=new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhcho);
        imageView = findViewById(R.id.imagecho);
        sharedPreferences=getSharedPreferences(SHARE_PREF,MODE_PRIVATE);

        getKhachhang();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            new Thread(new Runnable() {
                @Override
                public void run() {
                     try{
                         Thread.sleep(2000);
                         if(sharedPreferences.getString(ACCOUNT,"").isEmpty())
                         {
                             Intent intent = new Intent(getApplicationContext(),DangnhapActivity.class);
                             startActivity(intent);
                         }else
                         {
                             startActivity(new Intent(getApplicationContext(),MainActivity.class));
                         }
                         finish();
                     }catch (Exception e){
                         e.printStackTrace();
                     }
                }
            }).start();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");

        }
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
                            listKhachhang.add(new Khachhang(id,account,password,name,address,date));
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
}