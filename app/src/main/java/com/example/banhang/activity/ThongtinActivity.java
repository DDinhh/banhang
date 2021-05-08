package com.example.banhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.banhang.R;
import com.example.banhang.activity.DangnhapActivity;
import com.example.banhang.activity.GiohangActivity;
import com.example.banhang.activity.MainActivity;
import com.example.banhang.activity.ManhinhchoActivity;
import com.example.banhang.adapter.TabAdapter;
import com.example.banhang.ultil.Server;
import com.google.android.material.tabs.TabLayout;

public class ThongtinActivity extends AppCompatActivity {
    TextView tv_ten,tv_dienthoai,tv_diachi;
    Toolbar toolbarlienhe;
    SharedPreferences sharedPreferences;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin);
        Anhxa();
      //  setData();
    }


    private void Anhxa() {
        toolbarlienhe = findViewById(R.id.toolbardangki);
        setSupportActionBar(toolbarlienhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlienhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
        viewPager = findViewById(R.id.view_group);
        tabLayout = findViewById(R.id.tabxxx);
        tv_ten=findViewById(R.id.tv1);
        tv_dienthoai=findViewById(R.id.tv2);
        tv_diachi=findViewById(R.id.tv3);
        sharedPreferences=getSharedPreferences(ManhinhchoActivity.SHARE_PREF,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        tv_ten.setText(sharedPreferences.getString(ManhinhchoActivity.NAME,""));
        tv_dienthoai.setText(sharedPreferences.getString(ManhinhchoActivity.ACCOUNT,""));
        tv_diachi.setText(sharedPreferences.getString(ManhinhchoActivity.ADDRESS,""));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menudangxuat:
                AlertDialog.Builder builder = new AlertDialog.Builder(ThongtinActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Xác nhận đăng xuất tài khoản");
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.remove(ManhinhchoActivity.ACCOUNT);
                        editor.remove(ManhinhchoActivity.ADDRESS);
                        editor.remove(ManhinhchoActivity.DATE);
                        editor.remove(ManhinhchoActivity.ID);
                        editor.remove(ManhinhchoActivity.PASSWORD);
                        editor.remove(ManhinhchoActivity.NAME);
                        editor.apply();
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), DangnhapActivity.class));
                        finish();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setData()
    {

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(),0,1,2,sharedPreferences.getString(ManhinhchoActivity.ACCOUNT,""),
                new String[]{"Đang giao","Đã nhận","Hủy"}, Server.Duongdandonhang));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
}