package com.zhiyun88.www.module_main.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;

import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.MultipleStatusView;
import com.wb.baselib.view.TopBarView;
import com.xuexiang.xqrcode.ui.CaptureActivity;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.adapter.HomeAdapter;
import com.zhiyun88.www.module_main.main.bean.BannerBean;
import com.zhiyun88.www.module_main.main.bean.HomeBean;
import com.zhiyun88.www.module_main.main.bean.HomeCourseBean;
import com.zhiyun88.www.module_main.main.bean.HomeTransformerBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.HomeFragmentContranct;
import com.zhiyun88.www.module_main.main.mvp.presenter.HomeFragmentPresenter;
import com.zhiyun88.www.module_main.main.ui.SearchActivity;
import com.zhiyun88.www.module_main.sys.CustomCaptureFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class HomeFragment extends MvpFragment<HomeFragmentPresenter> implements HomeFragmentContranct.HomeFragmentView {

    private TopBarView topBarView;
    private ListView listView;
    private MultipleStatusView multiplestatusview;
    private List<HomeBean> homeBeanList;
    private SmartRefreshLayout smartRefreshLayout;
    private HomeAdapter homeAdapter;

    @Override
    protected HomeFragmentPresenter onCreatePresenter() {
        return new HomeFragmentPresenter(this);
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_home);
        topBarView = getViewById(R.id.topbarview);
        multiplestatusview = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity()).defaultRefreSh();
        smartRefreshLayout.setEnableLoadMore(false);
        multiplestatusview.showLoading();
        listView = getViewById(R.id.p_lv);
        listView.setVerticalScrollBarEnabled(false);
        topBarView.getCenterTextView().setText(R.string.main_curriculum_table);
//        topBarView.getLeftImageButton().setVisibility(View.GONE);
        mPresenter.getHomeData();
        homeBeanList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), homeBeanList);
        listView.setAdapter(homeAdapter);
        setListener();
    }


    @Override
    protected void setListener() {
        super.setListener();
        topBarView.setListener(new TopBarView.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == TopBarView.ACTION_RIGHT_BUTTON) {
                    //打开搜索页
                    ToActivityUtil.newInsance().toNextActivity(getActivity(),SearchActivity.class );
                }else if(action==TopBarView.ACTION_LEFT_BUTTON){
//                    openPageForResult(CustomCaptureFragment.class, null, 111);
//                    PerMissionsManager.newInstance().getUserPerMissions(getActivity(), new PerMissionCall() {
//                        @Override
//                        public void userPerMissionStatus(boolean is) {
//                            if (is) {
//                                Intent intent = new Intent(getActivity(), CaptureActivity.class);
//                                startActivityForResult(intent, REQUEST_CODE);
//                            }
//                        }
//                    },new String[]{Manifest.permission.CAMERA});
                            getActivity().finish();
                }
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getHomeData();
            }
        });
        multiplestatusview.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiplestatusview.showLoading();
                mPresenter.getHomeData();
            }
        });
    }

    @Override
    public boolean isLazyFragment() {
        return true;
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
        smartRefreshLayout.finishRefresh();
        homeBeanList.clear();
        homeBeanList.add((HomeBean) o);
        multiplestatusview.showContent();
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }
}
