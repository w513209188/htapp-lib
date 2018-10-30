package com.example.seele.newht;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zhiyun88.www.module_main.call.LoginStatusCall;
import com.zhiyun88.www.module_main.hApp;

public class MainActivity extends AppCompatActivity {
    private Button toact1,toact2,toact3,toact4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toact1=this.findViewById(R.id.toact1);
        toact2=this.findViewById(R.id.toact2);
        toact3=this.findViewById(R.id.toact3);
        toact4=this.findViewById(R.id.toact4);
        toact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hApp.newInstance().toSchemme("htnx://course/1",MainActivity.this);
            }
        });
        toact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hApp.newInstance().toSchemme("htnx://task/1",MainActivity.this);
            }
        });
        toact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hApp.newInstance().toSchemme("htnx://investigation/1",MainActivity.this);
            }
        });
        toact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hApp.newInstance().toMainActivity(MainActivity.this, "1", "dfsfsfds", new LoginStatusCall() {
                    @Override
                    public void LoginError(String msg, int code) {
                        Log.e("---->>",msg+code);
                    }
                });
            }
        });
    }
}
