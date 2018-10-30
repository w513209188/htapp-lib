package com.zhiyun88.www.module_main.main.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baijiahulian.BJVideoPlayerSDK;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.base.activity.BaseActivity;
import com.wb.baselib.base.fragment.BaseFragment;
import com.wb.baselib.bean.Result;
import com.wb.baselib.bean.UserLoginBean;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.wb.baselib.jptabbar.OnTabSelectListener;
import com.wb.baselib.user.AppLoginUserInfoUtils;
import com.wb.baselib.utils.NotificationsUtils;
import com.wb.baselib.view.BottomBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.bean.BannerBean;
import com.zhiyun88.www.module_main.main.fragment.CourseFragment;
import com.zhiyun88.www.module_main.main.fragment.HomeFragment;
import com.zhiyun88.www.module_main.main.fragment.MineFragment;
import com.zhiyun88.www.module_main.main.fragment.TaskFragment;
import com.zhiyun88.www.module_main.main.fragment.TrainFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity {
    public static void startForResult(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
    private View fake_status_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        fake_status_bar=getViewById(R.id.fake_status_bar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fake_status_bar.setBackgroundResource(R.color.statusbar_color);
        }else {
            fake_status_bar.setBackgroundResource(R.color.statusbar_color_K);
        }
        UserLoginBean userLoginBean=new UserLoginBean();
        //配置下载用户身份
        userLoginBean.setUid(AppConfigManager.newInstance().getAppConfig().getFlgs());
        AppLoginUserInfoUtils.getInstance().saveLoginInfo(userLoginBean);
        initView(savedInstanceState);
        boolean isopen= NotificationsUtils.isNotificationEnabled(MainActivity.this);
        Log.e("这个是权限",isopen+"-----");
        if(!isopen){
            showMdDialog("权限提醒", "您当前手机没有允许通知提醒，请在通知管理中允许通知并在屏幕顶部显示，否则将无法正常收到推送消息", "不设置", "设置", new MyDialogListener() {
                @Override
                public void onFirst() {
                }

                @Override
                public void onSecond() {
                    getAppDetailSettingIntent(MainActivity.this);
                }
            });
        }
    }

    /**
     * 打开设置通知栏
     * @param context
     */
    private void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }
    @Override
    protected void initView(Bundle savedInstanceState) {
        List<BaseFragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new TaskFragment());
        list.add(new CourseFragment());
        list.add(new TrainFragment());
        list.add(new MineFragment());
        BottomBarView bottomBarView = getViewById(R.id.main_main_btv);
        bottomBarView.setBottomNoIcon(R.drawable.main_home_no, R.drawable.main_task_no, R.drawable.main_course_no, R.drawable.main_train_no, R.drawable.main_user_no)//未选择的图标 必传
                .setBottomSelectIcon(R.drawable.main_home_sel, R.drawable.main_task_sel, R.drawable.main_course_sel, R.drawable.main_train_sel, R.drawable.main_user_sel)//选择的图标 必传
                .setBottomTitles(getString(R.string.main_home), getString(R.string.main_task), getString(R.string.main_course), getString(R.string.main_train), getString(R.string.main_mine)) //显示文字 必传
                .setFragments(list) //显示的gragment 必传
                .setBottomTextNoColor(getResources().getColor(R.color.main_text_grey_7c))//未选中的字体颜色 必传
                .bindFrament(getSupportFragmentManager());
        bottomBarView.setBottomTextSelectColor(getResources().getColor(R.color.main_text_blue_458));//选中的字体颜色 必传
        bottomBarView.setBottomLister(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int i) {
                if(i==4){
                    fake_status_bar.setVisibility(View.GONE);
                }else {
                    fake_status_bar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public boolean onInterruptSelect(int i) {
                return false;
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public boolean isAllImage() {
        return true;
    }
}
