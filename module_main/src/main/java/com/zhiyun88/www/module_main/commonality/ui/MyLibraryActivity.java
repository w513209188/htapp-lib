package com.zhiyun88.www.module_main.commonality.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thefinestartist.finestwebview.FinestWebView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wb.baselib.base.activity.MvpActivity;
import com.wb.baselib.phone.PhoneUtils;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.adapter.MyLibraryAdapter;
import com.zhiyun88.www.module_main.commonality.bean.MyLibraryListBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.MyLibraryContranct;
import com.zhiyun88.www.module_main.commonality.mvp.presenter.MyLibraryPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyLibraryActivity extends MvpActivity<MyLibraryPresenter> implements MyLibraryContranct.MyLibraryView{
    private TopBarView topBarView;
    private SmartRefreshLayout smartRefreshLayout;
    private MultipleStatusView multipleStatusView;
    private int page = 1;
    private List<MyLibraryListBean> libraryListBeans;
    private MyLibraryAdapter myLibraryAdapter;
    private ListView listView;

    @Override
    protected MyLibraryPresenter onCreatePresenter() {
        return new MyLibraryPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity_credential);
        topBarView = getViewById(R.id.topbarview);
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout,this ).defaultRefreSh();
        listView = getViewById(R.id.p_lv);
        libraryListBeans = new ArrayList<>();
        myLibraryAdapter = new MyLibraryAdapter(this, libraryListBeans);
        listView.setAdapter(myLibraryAdapter);
        multipleStatusView.showLoading();
        topBarView.getCenterTextView().setText(R.string.main_my_library);
        mPresenter.getLibraryData(page);
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
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getLibraryData(page);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getLibraryData(page);
            }
        });
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page=1;
                mPresenter.getLibraryData(page);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (libraryListBeans == null)return;
                new FinestWebView.Builder(MyLibraryActivity.this)
                        .titleDefault("文库详情")
                        .updateTitleFromHtml(false)
                        .webViewUseWideViewPort(true)
                        .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                        .iconDefaultColorRes(R.color.main_live_3c)
                        .showIconMenu(false)
                        .titleSizeRes(R.dimen.title2)
                        .webViewJavaScriptEnabled(true)
                        .progressBarHeight(PhoneUtils.newInstance().dip2px(MyLibraryActivity.this, 3))
                        .progressBarColorRes(R.color.main_live_3c)
                        .titleColorRes(R.color.main_live_3c)
                        .toolbarColorRes(R.color.statusbar_color)
                        .statusBarColorRes(R.color.statusbar_color)
                        .backPressToClose(false)
                        .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                        .showUrl(false)
                        .show(libraryListBeans.get(position).getH5_detail());
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
        if (page == 1 ) {
            libraryListBeans.clear();
        }
        libraryListBeans.addAll((Collection<? extends MyLibraryListBean>) o);
        myLibraryAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void ShowLoadView() {
        multipleStatusView.showLoading();
    }

    @Override
    public void NoNetWork() {
        multipleStatusView.showNoNetwork();
    }

    @Override
    public void NoData() {
        multipleStatusView.showEmpty();
    }

    @Override
    public void ErrorData() {
        multipleStatusView.showError();
    }

    @Override
    public void isLoadMore(boolean isLoadMore) {
        RefreshUtils.getInstance(smartRefreshLayout,this ).isLoadData(isLoadMore);
    }
}