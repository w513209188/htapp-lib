package com.zhiyun88.www.module_main.course.fragment;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wb.baselib.base.fragment.LazyFragment;
import com.wb.baselib.log.LogTools;
import com.zhiyun88.www.module_main.R;

public class WebViewFragment extends LazyFragment {
    private WebView course_wb;
    private String url;
    public static WebViewFragment newInstcace(String url){
        WebViewFragment courseWebViewFragment=new WebViewFragment();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        courseWebViewFragment.setArguments(bundle);
        return courseWebViewFragment;
    }
    @Override
    public boolean isLazyFragment() {
        return false;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_webview_fragment);
        url=getArguments().getString("url");
        course_wb=getViewById(R.id.course_wb);
//        course_wb.loadUrl(url);//加载url
//        LogTools.e("--->>"+url);
        Log.e("jia",url);
        //声明WebSettings子类
        WebSettings webSettings = course_wb.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        course_wb.loadUrl(url);
        course_wb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }
}
