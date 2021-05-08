package com.example.banhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.banhang.R;
import com.example.banhang.adapter.SanphamkhacAdapter;
import com.example.banhang.model.Sanpham;
import com.example.banhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_E extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private TextView textviewthongbao;
    private ArrayList<Sanpham> products;
    private SanphamkhacAdapter adapter;

    int Page;
    int idsanpham;
    String localServer;

    public Fragment_E(int idsanpham, String localServer) {
        this.idsanpham = idsanpham;
        this.localServer = localServer;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_fragment);
        textviewthongbao = view.findViewById(R.id.textviewthongbao);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        getData(idsanpham);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = new ArrayList<>();
    }

    private void getData(int idsanpham) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String duongdan = localServer;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tenlaptop = "";
                int Gialaptop = 0;
                String Hinhanhlaptop = "";
                String Motalaptop = "";
                int Idsplaptop = 0;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenlaptop = jsonObject.getString("tensp");
                            Gialaptop = jsonObject.getInt("giasp");
                            Hinhanhlaptop = jsonObject.getString("hinhanhsp");
                            Motalaptop = jsonObject.getString("motasp");
                            Idsplaptop = jsonObject.getInt("idsanpham");
                            products.add(new Sanpham(id, Tenlaptop, Gialaptop, Hinhanhlaptop, Motalaptop, Idsplaptop));
                        }
                        adapter = new SanphamkhacAdapter(getContext(), products);
                        recyclerView.setAdapter(adapter);recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                  
                } else {
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
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idsanpham));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }  @Override
    public void onPause() {
        super.onPause();
        products.clear();
    }
}
