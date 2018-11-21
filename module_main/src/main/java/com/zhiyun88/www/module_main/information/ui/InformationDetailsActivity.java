package com.zhiyun88.www.module_main.information.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;

public class InformationDetailsActivity extends BaseActivity {

    private TopBarView topBarView;
    private WebView webView;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_information_details);
        url = getIntent().getStringExtra("h5");
        if (url == null || url.equals("")) {
            return;
        }
        topBarView = getViewById(R.id.topbarview);
        webView = getViewById(R.id.information_wb);
        topBarView.getCenterTextView().setText("资讯详情");
        initView(savedInstanceState);
        setListener();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setJavaScriptEnabled(true);//支持javascript
        webSettings.setUseWideViewPort(true);//适配屏幕
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(false);//支持放大缩小
        webSettings.setDisplayZoomControls(false);//隐藏放大缩小的按钮
        webSettings.setDomStorageEnabled(true);//支持Html5标签
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        //设置不用系统浏览器打开,直接显示在当前Webview
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
