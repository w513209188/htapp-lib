package com.zhiyun88.www.module_main.sys;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.wb.baselib.base.fragment.LazyFragment;
import com.wb.baselib.log.LogTools;
import com.wb.baselib.utils.ToastUtils;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;
import com.xuexiang.xqrcode.ui.CaptureFragment;
import com.xuexiang.xqrcode.util.QRCodeAnalyzeUtils;
import com.zhiyun88.www.module_main.R;

import static android.app.Activity.RESULT_OK;

public class CustomCaptureFragment extends LazyFragment {

    @Override
    public boolean isLazyFragment() {
        return false;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_custom_capture);
        initViews();
    }
    /**
     * 初始化控件
     */
    protected void initViews() {
        // 为二维码扫描界面设置定制化界面
        CaptureFragment captureFragment = XQRCode.getCaptureFragment(R.layout.main_layout_custom_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        captureFragment.setCameraInitCallBack(new CaptureFragment.CameraInitCallBack() {
            @Override
            public void callBack(Exception e) {
                if (e != null) {
                    CaptureActivity.showNoPermissionTip(getActivity(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }
    /**
     * 二维码解析回调函数
     */
    QRCodeAnalyzeUtils.AnalyzeCallback analyzeCallback = new QRCodeAnalyzeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            LogTools.e("解析成功"+result);
        }
        @Override
        public void onAnalyzeFailed() {
            LogTools.e("解析失败");
        }
    };
}