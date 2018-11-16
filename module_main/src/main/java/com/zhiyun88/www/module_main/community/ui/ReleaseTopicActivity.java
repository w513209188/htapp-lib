package com.zhiyun88.www.module_main.community.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.image.GlideManager;
import com.wb.baselib.view.TopBarView;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.common.ImageLoader;
import com.wngbo.www.common_postphoto.widget.DividerGridItemDecoration;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.adapter.ImageShowAdapter;
import com.zhiyun88.www.module_main.community.bean.ImageBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.ReleaseTopicContranct;
import com.zhiyun88.www.module_main.community.mvp.presenter.ReleaseTopicPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ReleaseTopicActivity extends MvpActivity<ReleaseTopicPresenter> implements ReleaseTopicContranct.ReleaseTopicView {

    private TopBarView topBarView;
    private EditText topic_content, topic_title;
    private List<String> result;
    String path = "";
    private String groupId;
    private String title;
    private String content;
    private RecyclerView select_image;
    private ImageShowAdapter imageShowAdapter;
    private LinearLayout have_name_ll;
    private ImageView show_name;
    private boolean is_show = false;

    @Override
    protected ReleaseTopicPresenter onCreatePresenter() {
        return new ReleaseTopicPresenter(this);
    }

    @Override
    protected void initView(Bundle savedIState) {
        setContentView(R.layout.main_activity_releasetopic);
        groupId = getIntent().getStringExtra("groupId");
        topBarView = getViewById(R.id.topbarview);
        topic_title = getViewById(R.id.topic_title);
        topic_content = getViewById(R.id.topic_content);
        select_image = getViewById(R.id.select_image);
        have_name_ll = getViewById(R.id.have_name_ll);
        show_name = getViewById(R.id.show_name);

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                GlideManager.getInstance().setCommonPhoto(imageView, R.drawable.course_image, ReleaseTopicActivity.this, path, false);
            }
        });

        select_image.setLayoutManager(new GridLayoutManager(select_image.getContext(), 3));
        select_image.addItemDecoration(new DividerGridItemDecoration(select_image.getContext()));
        result = new ArrayList<>();
        imageShowAdapter = new ImageShowAdapter(this, result);
        select_image.setAdapter(imageShowAdapter);
    }

    @Override
    protected void setListener() {
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_LEFT_BUTTON) {
                    finish();
                } else if (action == TopBarView.ACTION_RIGHT_TEXT) {
                    title = topic_title.getText().toString().trim();
                    content = topic_content.getText().toString().trim();
                    if (TextUtils.isEmpty(title)) {
                        showShortToast("标题不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(content)) {
                        showShortToast("内容不能为空");
                        return;
                    }
                    path = "";
                    if (result == null || result.size() == 0) {
                        showLoadDiaLog("发表中...");
                        String showName = is_show ? "1" : "0";
                        mPresenter.commitTopicData(groupId, title, content, showName, path);
                    } else {
                        Map<String, File> map = new HashMap<>();
                        for (int i = 0; i < result.size(); i++) {
                            File file = new File(result.get(i));
                            map.put("file" + i, file);
                        }
                        Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image/*"));
                        mPresenter.commitImage(bodyMap);
                        showLoadDiaLog("发表中...");
                    }
                }
            }
        });
        have_name_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_show = !is_show;
                show_name.setSelected(is_show);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 666) {
            if (data == null) return;
            result = data.getStringArrayListExtra("result");
            imageShowAdapter.setData(result);
        }
    }

    @Override
    public void showErrorMsg(String msg) {
        showShortToast(msg);
    }

    @Override
    public void showLoadV(String msg) {
        showLoadDiaLog(msg);
    }

    @Override
    public void closeLoadV() {
        hidLoadDiaLog();
    }

    @Override
    public void SuccessData(Object o) {
        List<ImageBean> imageBeans = new ArrayList<>();
        imageBeans.addAll((List<ImageBean>) o);
        for (int i = 0; i < imageBeans.size(); i++) {
            if (i == 0) {
                path = imageBeans.get(i).getPath();
            } else {
                path = path + "," + imageBeans.get(i).getPath();
            }
        }
        String showName = is_show ? "1" : "0";
        mPresenter.commitTopicData(groupId, title, content, showName, path);
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void commitSuccess(String msg) {
        hidLoadDiaLog();
        showShortToast(msg);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        result.clear();
    }
}