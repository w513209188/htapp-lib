package com.zhiyun88.www.module_main.community.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhiyun88.www.module_main.R;

public class CustomDialog extends Dialog{
    private Context context;

    public CustomDialog(@NonNull Context context) {
        this(context,0);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = LayoutInflater.from(context).inflate(R.layout.main_custom_dialog_bottom, null);
        this.setContentView(layout);
    }

}
