package com.zhiyun88.www.module_main;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CustomDialog extends Dialog {

    private TextView dialog_title, dialog_content, dialog_yes, dialog_no,dialog_centre_btn;
    private Dialog dialog;
    private LinearLayout dialog_btn_ll;

    public CustomDialog(@NonNull Context context) {
        this(context,0);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

   /* public CustomDialog init() {
        View view = LayoutInflater.from(context).inflate(R.layout.main_custom_dialog, null);
        CustomDialog customDialog = new CustomDialog(context,R.style.Dialog);
        dialog = StyledDialog.buildCustom(view, Gravity.CENTER).show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog_title = view.findViewById(R.id.dialog_title);
        dialog_content = view.findViewById(R.id.dialog_content);
        dialog_yes = view.findViewById(R.id.dialog_yes);
        dialog_no = view.findViewById(R.id.dialog_no);
        dialog_btn_ll = view.findViewById(R.id.dialog_btn_ll);
        dialog_centre_btn = view.findViewById(R.id.dialog_centre_btn);

        dialog_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null)return;
                onClickListener.setYesClickListener();
                closeDiaLog();
            }
        });
        dialog_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null)return;
                onClickListener.setNoClickListener();
                closeDiaLog();
            }
        });
        dialog_centre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCentreClickListenter == null)return;
                onCentreClickListenter.setCentreClickListener();
                closeDiaLog();
            }
        });
        return dialog;
    }
*/
}
