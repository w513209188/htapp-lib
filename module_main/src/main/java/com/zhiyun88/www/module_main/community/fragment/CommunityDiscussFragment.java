package com.zhiyun88.www.module_main.community.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.wangbo.smartrefresh.layout.SmartRefreshLayout;
import com.wangbo.smartrefresh.layout.api.RefreshLayout;
import com.wangbo.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wangbo.smartrefresh.layout.listener.OnRefreshListener;
import com.wb.baselib.base.fragment.MvpFragment;
import com.wb.baselib.utils.RefreshUtils;
import com.wb.baselib.view.MultipleStatusView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.adapter.CommunityDiscussAdapter;
import com.zhiyun88.www.module_main.community.bean.DiscussListBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDiscussContranct;
import com.zhiyun88.www.module_main.community.mvp.presenter.CommunityDiscussPresenter;
import com.zhiyun88.www.module_main.community.ui.TopicDetailsActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CommunityDiscussFragment extends MvpFragment<CommunityDiscussPresenter> implements CommunityDiscussContranct.CommunityDiscussView {

    private MultipleStatusView multipleStatusView;
    private SmartRefreshLayout smartRefreshLayout;
    private ListView listView;
    private CommunityDiscussAdapter discussAdapter;
    private String type;
    private String groupId;
    private int page = 1;
    private List<DiscussListBean> discussListBeans;

    public static Fragment newInstance(String type, String groupId) {
        CommunityDiscussFragment discussFragment = new CommunityDiscussFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("groupId", groupId);
        discussFragment.setArguments(bundle);
        return discussFragment;
    }


    @Override
    public boolean isLazyFragment() {
        return true;
    }


    @Override
    protected CommunityDiscussPresenter onCreatePresenter() {
        return new CommunityDiscussPresenter(this);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.main_fragment_information);
        type = getArguments().getString("type");
        groupId = getArguments().getString("groupId","");
        multipleStatusView = getViewById(R.id.multiplestatusview);
        smartRefreshLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.p_lv);
        discussListBeans = new ArrayList<>();
        discussAdapter = new CommunityDiscussAdapter(getActivity(),discussListBeans);
        listView.setAdapter(discussAdapter);
        RefreshUtils.getInstance(smartRefreshLayout,getActivity() ).defaultRefreSh();
        multipleStatusView.showLoading();
        if ("".equals(groupId)) {
            mPresenter.getDiscussData(type,page);
        }else {
            mPresenter.getGroupTypeData(type,groupId,page);
        }

        setListener();
    }

    @Override
    protected void setListener() {
        super.setListener();
        multipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleStatusView.showLoading();
                page = 1;
                if ("".equals(groupId)) {
                    mPresenter.getDiscussData(type,page);
                }else {
                    mPresenter.getGroupTypeData(type,groupId,page);
                }

            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                if ("".equals(groupId)) {
                    mPresenter.getDiscussData(type,page);
                }else {
                    mPresenter.getGroupTypeData(type,groupId,page);
                }
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if ("".equals(groupId)) {
                    mPresenter.getDiscussData(type,page);
                }else {
                    mPresenter.getGroupTypeData(type,groupId,page);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //社区详情
                Intent intent = new Intent(getActivity(), TopicDetailsActivity.class);
                intent.putExtra("question_id", discussListBeans.get(position).getId());
                intent.putExtra("h5", discussListBeans.get(position).getH5_detail());
                startActivity(intent);
            }
        });
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
        if (page == 1) {
            discussListBeans.clear();
        }
        discussListBeans.addAll((Collection<? extends DiscussListBean>) o);
        discussAdapter.notifyDataSetChanged();
        multipleStatusView.showContent();
        page++;
    }

    @Override
    public LifecycleTransformer binLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void isLoadMore(boolean b) {
        RefreshUtils.getInstance(smartRefreshLayout,getActivity() ).isLoadData(b);
    }
}
