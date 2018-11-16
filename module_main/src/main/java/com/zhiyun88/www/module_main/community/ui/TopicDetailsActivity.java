package com.zhiyun88.www.module_main.community.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.MyListView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentListBean;
import com.zhiyun88.www.module_main.community.bean.QuestionInfoBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDetailsContranct;
import com.zhiyun88.www.module_main.community.mvp.presenter.CommunityDetailsPresenter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TopicDetailsActivity extends MvpActivity<CommunityDetailsPresenter> implements CommunityDetailsContranct.CommunityDetailsView {

    private TopBarView topBarView;
    private ImageView like;
    private TextView commit,comment_count,text,content;
    private RelativeLayout details_rl;
    private String url;
    private String question_id;
    private MyListView details_list;
    private CommentAdapater mAdapter;
    private MultipleStatusView multiplestatusview;
    private SmartRefreshLayout smartRefreshLayout;
    private int page = 1;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        content.setText(charSequence);
                        content.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private List<DetailsCommentListBean> listBeans;

    @Override
    protected CommunityDetailsPresenter onCreatePresenter() {
        return new CommunityDetailsPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_mu_community_details);
        question_id = getIntent().getStringExtra("question_id");
        url = getIntent().getStringExtra("h5");
        multiplestatusview = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        topBarView = getViewById(R.id.topbarview);
        content = getViewById(R.id.details_textview);
        comment_count = getViewById(R.id.comment_count);
        details_list = getViewById(R.id.p_mlv);
        text = getViewById(R.id.details_text);
        like = getViewById(R.id.details_like);
        commit = getViewById(R.id.details_commit);
        details_rl = getViewById(R.id.details_rl);
        smartRefreshLayout.setEnableRefresh(false);
        listBeans = new ArrayList<>();
        mAdapter = new CommentAdapater(this, listBeans);
        details_list.setAdapter(mAdapter);
        multiplestatusview.showLoading();
        mPresenter.getCommunityDetails(question_id);
        initWebView();
    }

    private void initWebView() {

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
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setImageResource(R.drawable.details_like_org);
                like.setEnabled(false);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
            }
        });
        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                View view = LayoutInflater.from(TopicDetailsActivity.this).inflate(R.layout.main_custom_dialog_bottom, null);
                StyledDialog.buildCustomBottomSheet(view);

            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        QuestionInfoBean questionInfoBean = (QuestionInfoBean) o;
        setActivityContent(questionInfoBean.getContent());
        topBarView.getCenterTextView().setText(questionInfoBean.getTitle());
        comment_count.setText("全部评论 ("+0+")");
        multiplestatusview.showContent();
        mPresenter.getCommentList(question_id, "1", page);
    }

    private void setActivityContent(final String activityContent) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Html.ImageGetter imageGetter = new Html.ImageGetter() {

                    @Override
                    public Drawable getDrawable(String source) {
                        Drawable drawable;
                        drawable = getImageNetwork(source);
                        if (drawable != null) {
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        } else if (drawable == null) {
                            return null;
                        }
                        return drawable;
                    }
                };
                CharSequence charSequence = Html.fromHtml(activityContent.trim(), imageGetter, null);
                Message ms = Message.obtain();
                ms.what = 1;
                ms.obj = charSequence;
                mHandler.sendMessage(ms);
            }
        }).start();
    }

    public Drawable getImageNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            myFileUrl = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            // 在这一步最好先将图片进行压缩，避免消耗内存过多
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            drawable = new BitmapDrawable(bitmap);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void ShowLoadView() {
        multiplestatusview.showLoading();
    }

    @Override
    public void NoNetWork() {
        multiplestatusview.showNoNetwork();
    }

    @Override
    public void NoData() {
        multiplestatusview.showEmpty();
    }

    @Override
    public void ErrorData() {
        multiplestatusview.showError();
    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout, TopicDetailsActivity.this).isLoadData(isLoadMore);
    }

    @Override
    public void CommentListData(int total, List<DetailsCommentListBean> list) {
        if (page == 1) {
            listBeans.clear();
        }
        listBeans.addAll(list);
        mAdapter.notifyDataSetChanged();
        page++;
        comment_count.setText("全部评论 ("+total+")");

    }
}
