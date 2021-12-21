package com.example.banhang.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banhang.R;
import com.example.banhang.adapter.GiohangAdapter;
import com.example.banhang.model.Giohang;
import com.example.banhang.ultil.CheckConnection;
import com.example.banhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GiohangActivity extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhtoan, btntieptucmua;
    Toolbar toolbargiohang;
    GiohangAdapter giohangAdapter;
    SharedPreferences sharedPreferences;
    public static long tongtien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        AnhXa();
        sharedPreferences = getSharedPreferences(ManhinhchoActivity.SHARE_PREF, MODE_PRIVATE);
        AcitonToolbar();
        CheckData();
        EventUltil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size() > 0) {

                    showDialog();

                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Giỏ hàng của bạn chưa có sản phẩm");
                }
            }
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog(GiohangActivity.this);
        dialog.setContentView(R.layout.dialog_xacnhan);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        EditText editText=dialog.findViewById(R.id.e1);
        Button t1 = dialog.findViewById(R.id.buttonxacnhan);
        Button t2 = dialog.findViewById(R.id.buttonhuy);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = sharedPreferences.getString(ManhinhchoActivity.NAME,"");
                final String sdt = sharedPreferences.getString(ManhinhchoActivity.ACCOUNT,"");
                final String note = editText.getText().toString().trim();
                DateFormat df = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                String date = df.format(Calendar.getInstance().getTime());
                final String diachi = sharedPreferences.getString(ManhinhchoActivity.ADDRESS,"");
                if(ten.length()>0 && sdt.length()>0 ){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdandonhangmoi, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.manggiohang.clear();
                                            dialog.dismiss();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Xác nhận đơn hàng thành công");
                                            finish();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng bị lỗi");
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
                                        JSONArray jsonArray = new JSONArray();

                                        for(int i=0;i<MainActivity.manggiohang.size();i++){
                                            JSONObject jsonObject= new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("hinhanhsanpham",MainActivity.manggiohang.get(i).getHinhsp());
                                                jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String > hashMap = new HashMap<>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
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
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);

                            hashMap.put("diachi",diachi);
                            hashMap.put("ngaydathang",date);
                            hashMap.put("tongtien", String.valueOf(tongtien));

                            hashMap.put("ghichu",note);
                            hashMap.put("trangthai", String.valueOf(0));
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Hãy kiểm tra lại dữ liệu");
                }
            }

        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void CatchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.manggiohang.size() <= 0) {
                            txtthongbao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.manggiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.manggiohang.size() <= 0) {
                                txtthongbao.setVisibility(View.VISIBLE);
                            } else {
                                txtthongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        tongtien = 0;
        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
            tongtien += MainActivity.manggiohang.get(i).getGiasp();

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "Đ");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0) {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        } else {
            giohangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void AcitonToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvgiohang = findViewById(R.id.listviewgiohang);
        txtthongbao = findViewById(R.id.textviewthongbao);
        txttongtien = findViewById(R.id.textviewtongtien);
        btnthanhtoan = findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua = findViewById(R.id.buttontieptucmuahang);
        toolbargiohang = findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(GiohangActivity.this, MainActivity.manggiohang);
        lvgiohang.setAdapter(giohangAdapter);
    }
}