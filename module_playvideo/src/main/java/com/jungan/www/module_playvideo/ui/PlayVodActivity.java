package com.jungan.www.module_playvideo.ui;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baijiahulian.BJVideoPlayerSDK;
import com.baijiahulian.common.networkv2.BJNetCall;
import com.baijiahulian.common.networkv2.BJNetCallback;
import com.baijiahulian.common.networkv2.BJNetRequestManager;
import com.baijiahulian.common.networkv2.BJNetworkClient;
import com.baijiahulian.common.networkv2.BJRequestBody;
import com.baijiahulian.common.networkv2.BJResponse;
import com.baijiahulian.common.networkv2.HttpException;
import com.baijiahulian.player.BJPlayerView;
import com.baijiahulian.player.OnPlayerViewListener;
import com.baijiahulian.player.bean.SectionItem;
import com.baijiahulian.player.bean.VideoItem;
import com.baijiahulian.player.playerview.BJCenterViewPresenter;
import com.baijiahulian.player.playerview.IPlayerTopContact;
import com.baijiahulian.player.playerview.PlayerConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.jungan.www.module_playvideo.R;
import com.jungan.www.module_playvideo.view.BJBottomViewPresenterCopy;
import com.jungan.www.module_playvideo.view.BJTopViewPresenterCopy;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

@Route(path = "/playvideo/open")
public class PlayVodActivity extends AppCompatActivity {
    private BJPlayerView playerView;
    private long videoId;
    private String Token;
    private String isOnLine;
    private String BJYID;
    private String Filepath;
    private BJBottomViewPresenterCopy bottomViewPresenterCopy;
    private BJTopViewPresenterCopy bjTopViewPresenterCopy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_vod);

        Token=getIntent().getStringExtra("token");
        BJYID=getIntent().getStringExtra("bjyId");
//        chId=getIntent().getStringExtra("chId");
        try {
            isOnLine=getIntent().getStringExtra("isOnLine");
        }catch (Exception e){
            isOnLine="1";
        }
        if(isOnLine.equals("1")){
            videoId=getIntent().getLongExtra("videoId",0L);
        }else {
            Filepath=getIntent().getStringExtra("Filepath");
        }
        if(isOnLine==null||isOnLine.equals("")){
            isOnLine="1";
        }
        //设置点播下载的服务器环境，默认值正式环境
        playerView = (BJPlayerView) findViewById(R.id.videoView);
        bottomViewPresenterCopy = new BJBottomViewPresenterCopy(playerView.getBottomView());
        playerView.setBottomPresenter(bottomViewPresenterCopy);

        bjTopViewPresenterCopy=new BJTopViewPresenterCopy(playerView.getTopView(),PlayVodActivity.this);
        playerView.setTopPresenter(bjTopViewPresenterCopy);
        final BJCenterViewPresenter centerpresenter = new BJCenterViewPresenter(playerView.getCenterView());
        centerpresenter.setRightMenuHidden(false);
        playerView.setCenterPresenter(centerpresenter);
        playerView.initPartner(Long.parseLong(BJYID), BJPlayerView.PLAYER_DEPLOY_ONLINE, 1);
        playerView.setHeadTailPlayMethod(BJPlayerView.HEAD_TAIL_PLAY_NONE);
        playerView.setVideoEdgePaddingColor(Color.argb(255, 0, 0, 0));
        playerView.setOnPlayerViewListener(new OnPlayerViewListener() {
            @Override
            public void onVideoInfoInitialized(BJPlayerView playerView, HttpException exception) {
                //TODO: 视频信息初始化结束
                if (exception != null) {
                    // 视频信息初始化成功
                    VideoItem videoItem = playerView.getVideoItem();
                }
            }

            @Override
            public void onPause(BJPlayerView playerView) {
                //TODO: video暂停
            }

            @Override
            public void onPlay(BJPlayerView playerView) {
                //TODO: 开始播放
            }

            @Override
            public void onError(BJPlayerView playerView, int code) {
                //TODO: 播放出错
            }

            @Override
            public void onUpdatePosition(BJPlayerView playerView, int position) {
                //TODO: 播放过程中更新播放位置
            }

            @Override
            public void onSeekComplete(BJPlayerView playerView, int position) {
                //TODO: 拖动进度条
            }

            @Override
            public void onSpeedUp(BJPlayerView playerView, float speedUp) {
                //TODO: 设置倍速播放
            }

            @Override
            public void onVideoDefinition(BJPlayerView playerView, int definition) {
                //TODO: 设置清晰度完成
            }

            @Override
            public void onPlayCompleted(BJPlayerView playerView, VideoItem item, SectionItem nextSection) {
                //TODO: 当前视频播放完成 [nextSection已被废弃，请勿使用]

            }

            @Override
            public void onVideoPrepared(BJPlayerView playerView) {
                //TODO: 准备好了，马上要播放
                // 可以在这时获取视频时长
                playerView.getDuration();
            }

            @Override
            public void onCaton(BJPlayerView playerView) {
                //TODO 视频播放卡顿，卡住超过3秒。可以在此处提示正在缓冲数据
            }

            @Override
            public String getVideoTokenWhenInvalid() {
                //TODO 视频token出错，需要集成方重新获取并传入BJPlayerview。
                return "test12345678";
            }
        });
        if(isOnLine.equals("1")){
            OnLineVideo();
        }else {
            OffLineVideo();
        }

    }
    private void OnLineVideo(){
//        long vId = Long.valueOf(videoId);
//        playerView.setVideoId(vId, Token);
//        int pos = 0;
//        playerView.playVideo(pos);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        playerView.switchOrientation();
//        IPlayerTopContact.TopView topView=playerView.getTopViewPresenter();
//        topView.setOnBackClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        long vId=0;
        try {
            vId = Long.valueOf(videoId);
        }catch (Exception e){
            vId=0;
        }
        playerView.setVideoId(vId, Token);
        playerView.getTopViewPresenter().setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        int pos = 0;
        playerView.playVideo(pos);
        playerView.switchOrientation();
        IPlayerTopContact.TopView topView=playerView.getTopViewPresenter();
        topView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void OffLineVideo(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(Filepath);
            if (file.exists()) {
                //进度条进度归零
                bottomViewPresenterCopy.setCurrentPosition(0);
                playerView.setVideoPath(Filepath);
                playerView.getTopViewPresenter().setOnBackClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                getWindow().setFormat(PixelFormat.TRANSLUCENT);
                int pos = 0;
                playerView.playVideo(pos);
                playerView.switchOrientation();
                IPlayerTopContact.TopView topView=playerView.getTopViewPresenter();
                topView.setOnBackClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                topView.setTitle(Token);
            } else {
                Toast.makeText(PlayVodActivity.this, videoId + "不存在的", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PlayVodActivity.this, "找不到存储卡！", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (playerView != null) {
            playerView.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (!playerView.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playerView != null) {
            playerView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerView != null) {
            playerView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playerView != null) {
            playerView.onDestroy();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Log.e("用户点击了返回","------>>>");
            bjTopViewPresenterCopy.finsnA(PlayVodActivity.this);
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}