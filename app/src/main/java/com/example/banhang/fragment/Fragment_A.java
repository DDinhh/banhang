package com.example.banhang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banhang.R;
import com.example.banhang.adapter.DonHangAdapter;
import com.example.banhang.model.Donhang;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_A extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private TextView textviewthongbao;
    private ArrayList<Donhang> products;
    private DonHangAdapter adapter;
    String tk;
    int trangthai;
    String localServer;

    public Fragment_A(int trangthai,String tk, String localServer) {
        this.trangthai = trangthai;
        this.tk=tk;
        this.localServer = localServer;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_fragment);
        textviewthongbao=view.findViewById(R.id.textviewthongbao);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        getData(trangthai,tk);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
    }

    private void getData(int trangthai,String tk) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = localServer ;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONArray json = new JSONArray(response);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject object = json.getJSONObject(i);
                            int id = object.getInt("id");
                            String name = object.getString("tenkhachhang");
                            String phone = object.getString("sodienthoai");
                            String address = object.getString("diachi");
                            String date=object.getString("ngaydathang");
                            String price=object.getString("tongtien");
                            String note=object.getString("ghichu");
                            int stt= object.getInt("trangthai");
                            products.add(new Donhang(id,name,phone,address,date,price,note,stt));
                        }
                    } catch (JSONException e) {

                    }
                    if(products.isEmpty())
                    {
                        textviewthongbao.setVisibility(View.VISIBLE);
                    }else {
                        textviewthongbao.setVisibility(View.GONE);
                        adapter=new DonHangAdapter(products,getContext());
                        recyclerView.setAdapter(adapter);
                        adapter.setItemClickListener(new DonHangAdapter.ClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {

                            }
                        });
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> pa = new HashMap<>();
                pa.put("tk", String.valueOf(tk));
                pa.put("trangthai", String.valueOf(trangthai));
                return pa;
            }
        };
        requestQueue.add(request);
    }
}
