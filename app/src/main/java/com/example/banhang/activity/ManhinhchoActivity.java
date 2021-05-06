package com.example.banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.banhang.R;
import com.example.banhang.ultil.CheckConnection;

public class ManhinhchoActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhcho);
        imageView = findViewById(R.id.imagecho);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            new Thread(new Runnable() {
                @Override
                public void run() {
                     try{
                         Thread.sleep(2000);
                         Intent intent = new Intent(getApplicationContext(),DangnhapActivity.class);
                         startActivity(intent);
                     }catch (Exception e){
                         e.printStackTrace();
                     }
                }
            }).start();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            //finish();
        }
    }
}