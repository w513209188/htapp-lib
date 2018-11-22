package com.zhiyun88.www.module_main.community.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;


import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle2.LifecycleTransformer;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.MyListView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.adapter.CommentAdapater;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentListBean;
import com.zhiyun88.www.module_main.community.bean.QuestionInfoBean;
import com.zhiyun88.www.module_main.community.config.CommunityConfig;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDetailsContranct;
import com.zhiyun88.www.module_main.community.mvp.presenter.CommunityDetailsPresenter;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class TopicDetailsActivity extends MvpActivity<CommunityDetailsPresenter> implements CommunityDetailsContranct.CommunityDetailsView {

    private TopBarView topBarView;
    private ImageView like, headImage;
    private TextView comment_count, text, htmlTextView, details_name, details_time, details_browse;
    private String question_id;
    private MyListView details_list;
    private CommentAdapater mAdapter;
    private MultipleStatusView multiplestatusview;
    private SmartRefreshLayout smartRefreshLayout;
    private int page = 1;
    private int index;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    CharSequence charSequence = (CharSequence) msg.obj;
                    if (charSequence != null) {
                        htmlTextView.setText(charSequence);
                        multiplestatusview.showContent();
                        //  htmlTextView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private List<DetailsCommentListBean> listBeans;
    private Dialog dialog;
    private TextView commit_tv;
    private ImageView showName_tv;
    private EditText content_et;

    @Override
    protected CommunityDetailsPresenter onCreatePresenter() {
        return new CommunityDetailsPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_mu_community_details);
        try {
            question_id = getIntent().getStringExtra("question_id");
        }catch (NullPointerException e) {
            showShortToast("参数错误");
            return;
        }
        multiplestatusview = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        topBarView = getViewById(R.id.topbarview);
        htmlTextView = getViewById(R.id.details_textview);
        comment_count = getViewById(R.id.comment_count);
        details_list = getViewById(R.id.p_mlv);
        text = getViewById(R.id.details_text);
        like = getViewById(R.id.details_like);
        headImage = getViewById(R.id.details_head);
        details_name = getViewById(R.id.details_name);
        details_time = getViewById(R.id.details_time);
        details_browse = getViewById(R.id.details_browse);
        RefreshUtils.getInstance(smartRefreshLayout,this ).defaultRefreSh();
        smartRefreshLayout.setEnableRefresh(false);
        listBeans = new ArrayList<>();
        mAdapter = new CommentAdapater(this, listBeans);
        details_list.setAdapter(mAdapter);
        multiplestatusview.showLoading();
        mPresenter.getCommunityDetails(question_id);
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
                mPresenter.setLike(question_id);
                like.setEnabled(false);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReply=false;
                showDiaLog();
            }
        });
        mAdapter.setOnReplyListener(new CommunityConfig.OnReplyListener() {
            @Override
            public void setReplyClick(int position) {
                index = position;
                isReply=true;
                showDiaLog();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getCommentList(question_id, "1", page);
            }
        });
    }

    private boolean is_show = false;
    private boolean isReply = false;
    private void showDiaLog() {

        is_show = false;
        View view = LayoutInflater.from(TopicDetailsActivity.this).inflate(R.layout.main_custom_dialog_bottom, null);
        dialog = StyledDialog.buildCustomBottomSheet(view).show();
        dialog.setCanceledOnTouchOutside(true);
        content_et = view.findViewById(R.id.details_content);
        LinearLayout have_name_ll = view.findViewById(R.id.have_name_ll);
        showName_tv = view.findViewById(R.id.show_name);
        commit_tv = view.findViewById(R.id.details_commit);
        if (isReply) {
            content_et.setHint("回复: " + listBeans.get(index).getUser_name());
        }else {
            content_et.setHint("");
        }
        have_name_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_show = !is_show;
                showName_tv.setSelected(is_show);
            }
        });
        commit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = content_et.getText().toString().trim();
                if (isReply) {
                    mPresenter.sendComment(question_id, str, is_show ? "1" : "0", listBeans.get(index).getId());
                } else {
                    mPresenter.sendComment(question_id, str, is_show ? "1" : "0", "0");
                }
                commit_tv.setEnabled(false);
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
        if (questionInfoBean.getAvatar() == null || questionInfoBean.getAvatar().equals("")) {
            Picasso.with(TopicDetailsActivity.this).load("www").error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(headImage);
        }else {
            Picasso.with(TopicDetailsActivity.this).load(questionInfoBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(headImage);
        }
        details_name.setText(questionInfoBean.getUser_name());
        details_time.setText(questionInfoBean.getCreated_at());
        details_browse.setText(questionInfoBean.getRead_count() + "次浏览");

        comment_count.setText("全部评论 (" + 0 + ")");
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
        comment_count.setText("全部评论 (" + total + ")");
        page++;
    }

    @Override
    public void sendSuccess(String msg) {
        showShortToast(msg);
        dialog.dismiss();
        commit_tv.setEnabled(true);
        page = 1;
        mPresenter.getCommentList(question_id, "1", page);
    }

    @Override
    public void setLikeSuccess(String msg) {
        like.setImageResource(R.drawable.details_like_org);
    }
}
