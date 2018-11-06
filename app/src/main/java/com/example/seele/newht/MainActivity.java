package com.example.seele.newht;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.baijiahulian.livecore.context.LPConstants;
import com.wb.baselib.crash.CrashHandler;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.zhiyun88.www.module_main.call.LoginStatusCall;
import com.zhiyun88.www.module_main.hApp;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button toact1,toact2,toact3,toact4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        CrashHandler.getInstance().CheckAppCarchLog();
        PerMissionsManager.newInstance().getUserPerMissions(MainActivity.this, new PerMissionCall() {
            @Override
            public void userPerMissionStatus(boolean b) {

            }
        },new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE});
        toact1=this.findViewById(R.id.toact1);
        toact2=this.findViewById(R.id.toact2);
        toact3=this.findViewById(R.id.toact3);
        toact4=this.findViewById(R.id.toact4);
        toact1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hApp.newInstance().toSchemme("htnx://course/1",MainActivity.this);
            }
        });
        toact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hApp.newInstance().toSchemme("htnx://task/1",MainActivity.this);
            }
        });
        toact3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hApp.newInstance().toMainActivity(MainActivity.this, "1", "dfsfsfds", new LoginStatusCall() {
//                    @Override
//                    public void LoginError(String msg, int code) {
//                        Log.e("---->>",msg+code);
//                    }
//                });




            }
        });
        toact4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (Map.Entry<String, String> entry :  HttpConfig.newInstance().getmMapHeader().entrySet()) {
//                    Log.e("baotou",entry.getKey()+"---"+entry.getValue());
//                }
//
                hApp.newInstance().toMainActivity(MainActivity.this, "31192", "dfsfsfds", new LoginStatusCall() {
                    @Override
                    public void LoginError(String msg, int code) {
                        Log.e("---->>",msg+code);
                    }
                });
//                LiveSDKWithUI.LiveRoomUserModel liveRoomUserModel=new LiveSDKWithUI.LiveRoomUserModel("ffsfsf","http://www.baidu.com","88", LPConstants.LPUserType.Student);
//               LiveSDKWithUI.LiveRoomUserModel liveRoomUserModel=new LiveSDKWithUI.LiveRoomUserModel("dfsfsd","dfssfsdf","888",1);
//                LiveSDKWithUI.enterRoom(MainActivity.this, Long.parseLong("465456456456456"), "dsdaDAdADAdA", liveRoomUserModel, new LiveSDKWithUI.LiveSDKEnterRoomListener() {
//                    @Override
//                    public void onError(String s) {
//
//                    }
//                });
            }
        });
    }
}
