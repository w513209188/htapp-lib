package com.zhiyun88.www.module_main;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

public class DialogUtils {

    private TextView dialog_title, dialog_content, dialog_yes, dialog_no,dialog_centre_btn;
    private Dialog dialog;
    private static DialogUtils dialogUtils;
    private LinearLayout dialog_btn_ll;
    private OnClickListener onClickListener;
    private OnCentreClickListenter onCentreClickListenter;
    private View view;

    private DialogUtils() {
    }
    public static DialogUtils newInstance() {
        if (dialogUtils == null) {
            dialogUtils = new DialogUtils();
        }
        return dialogUtils;
    }

    public DialogUtils initDialog(Context context) {
        return getDialog(context);
    }

    private DialogUtils getDialog(Context context) {
            view = LayoutInflater.from(context).inflate(R.layout.main_custom_dialog, null);
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
        return dialogUtils;
    }

    public DialogUtils setTitle(String title) {
        dialog_title.setText(title);
        return dialogUtils;
    }

    public DialogUtils setContent(String content) {
        dialog_content.setText(content);
        return dialogUtils;
    }

    public DialogUtils setBtn1Txt(String btn1Txt) {
        dialog_yes.setText(btn1Txt);
        return dialogUtils;
    }

    public DialogUtils setBtn2Txt(String btn2Txt) {
        dialog_no.setText(btn2Txt);
        return dialogUtils;
    }
    public DialogUtils setbtncentre(String btnCentreTxt) {
        dialog_centre_btn.setText(btnCentreTxt);
        return dialogUtils;
    }

    public DialogUtils setBtn2TxtColor(@ColorInt int btn2TxtColor) {
        dialog_no.setTextColor(btn2TxtColor);
        return dialogUtils;
    }

    public DialogUtils hitBtn(boolean isHit) {
        if (isHit) {
            dialog_btn_ll.setVisibility(View.GONE);
            dialog_centre_btn.setVisibility(View.VISIBLE);
        }else {
            dialog_btn_ll.setVisibility(View.VISIBLE);
            dialog_centre_btn.setVisibility(View.GONE);
        }
        return dialogUtils;
    }


    public void setOnClickListenter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnCentreClickListenter(OnCentreClickListenter onCentreClickListenter) {
        this.onCentreClickListenter = onCentreClickListenter;
    }

    private void closeDiaLog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public interface OnClickListener {
        void setYesClickListener();
        void setNoClickListener();
    }
    public interface OnCentreClickListenter {
        void setCentreClickListener();
    }
}
