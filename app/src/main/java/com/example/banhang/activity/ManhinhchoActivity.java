package com.example.banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.banhang.R;
import com.example.banhang.ultil.CheckConnection;

public class ManhinhchoActivity extends AppCompatActivity {
    public static final String SHARE_PREF="share";
    public static final String ID="id";
    public static final String ACCOUNT="account";
    public static final String PASSWORD="password";
    public static final String DATE="date";
    public static final String NAME="name";
    public static final String ADDRESS="email";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhcho);
        imageView = findViewById(R.id.imagecho);
        sharedPreferences=getSharedPreferences(SHARE_PREF,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString(ACCOUNT,"x");
        editor.apply();
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
}