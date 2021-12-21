package com.example.banhang.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banhang.R;
import com.example.banhang.adapter.ChitietdonhangAdapter;
import com.example.banhang.model.Chitietdonhang;
import com.example.banhang.model.Sanpham;
import com.example.banhang.ultil.CheckConnection;
import com.example.banhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChitietdonhangActivity extends AppCompatActivity {
    Toolbar toolbarChitiet;
    RecyclerView recyclerView;
    TextView textView,tv_oke;
    ArrayList<Chitietdonhang> arrayList=new ArrayList<>();
    ChitietdonhangAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdonhang);
        Anhxa();
        GetData(getIntent().getIntExtra("madonhang",0));

        if(getIntent().getIntExtra("trangthai",0)==0)
        {
            textView.setVisibility(View.VISIBLE);
            tv_oke.setVisibility(View.VISIBLE);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogEx(2);

                }
            });
            tv_oke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogEx(1);
                }
            });

        }

    }

    private void showDialogEx(int number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChitietdonhangActivity.this);
        builder.setTitle("Thông báo");
        if(number==2) {
            builder.setMessage("Bạn có muốn hủy đơn hàng này không");
        }else{
            builder.setMessage("Xác nhận  đã nhận đơn hàng này");
        }
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(number==2) {
                    UpdateData(getIntent().getIntExtra("madonhang", 0),Server.Duongdancapnhatdonhang);
                }else{
                    UpdateData(getIntent().getIntExtra("madonhang", 0),Server.Duongdancapnhatdonhangthanhcong);
                }
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void Anhxa() {
        tv_oke=findViewById(R.id.tv_oke);
        toolbarChitiet = findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recycler_oder);
        textView=findViewById(R.id.tv_cancel);
        textView.setVisibility(View.GONE);
        tv_oke.setVisibility(View.GONE);
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }
    private void GetData(int madonhang) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdanchitiet;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tenlaptop = "";
                String Gialaptop = "";
                String Hinhanh= "";
                String Soluong = "";

                if(response != null && response.length() != 2){

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenlaptop = jsonObject.getString("tensanpham");
                            Gialaptop = jsonObject.getString("giasanpham");
                            Hinhanh = jsonObject.getString("hinhanhsanpham");
                            Soluong = jsonObject.getString("soluongsanpham");

                            arrayList.add(new Chitietdonhang(id,Hinhanh,Tenlaptop,Soluong,Gialaptop));
                            Log.e("asa",Hinhanh);
                        }
                        adapter=new ChitietdonhangAdapter(arrayList,getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.e("asa","xvcx");
                          }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("madonhang",String.valueOf(madonhang));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void UpdateData(int id, String duongdan) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response .equals("1")){
                    if(duongdan.equals(Server.Duongdancapnhatdonhang)) {
                        Toast.makeText(getApplicationContext(), "Đã hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Xác nhận đơn hàng thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("id",String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}