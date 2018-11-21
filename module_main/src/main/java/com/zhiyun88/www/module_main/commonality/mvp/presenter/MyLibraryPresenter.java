package com.zhiyun88.www.module_main.commonality.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.MyLibraryBean;
import com.zhiyun88.www.module_main.commonality.mvp.contranct.MyLibraryContranct;
import com.zhiyun88.www.module_main.commonality.mvp.model.MyLibraryModel;
import com.zhiyun88.www.module_main.course.bean.CommentListBean;

import io.reactivex.disposables.Disposable;


public class MyLibraryPresenter extends MyLibraryContranct.MyLibraryPresenter {
    public MyLibraryPresenter(MyLibraryContranct.MyLibraryView iView) {
        this.mView = iView;
        this.mModel = new MyLibraryModel();
    }

    @Override
    public void getLibraryData(final int page) {
        HttpManager.newInstance().commonRequest(mModel.getLibraryData(page), new BaseObserver<Result<MyLibraryBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<MyLibraryBean> libraryBeanResult) {
                if (libraryBeanResult.getData() == null) {
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                } else {
                    if (libraryBeanResult.getData().getList() == null || libraryBeanResult.getData().getList().size() == 0) {
                        if (page == 1) {
                            mView.NoData();
                        } else {
                            mView.showErrorMsg("已经没有数据了！");
                            mView.isLoadMore(false);
                        }
                    } else {
                        if (libraryBeanResult.getData().getList().size() < AppConfigManager.newInstance().getAppConfig().getMaxPage()) {
                            //没有下一页了
                            mView.isLoadMore(false);
                        } else {
                            mView.isLoadMore(true);
                        }
                        mView.SuccessData(libraryBeanResult.getData().getList());
                    }

                }
            }
                @Override
                public void onFail (ApiException e){
                    if (page == 1) {
                        mView.ErrorData();
                    } else {
                        mView.showErrorMsg("服务器繁忙，请稍后尝试！");
                        mView.isLoadMore(true);
                    }
                }

                @Override
                public void onSubscribe (Disposable d){
                    addSubscribe(d);
                }

                @Override
                public void onComplete () {

                }
            },mView.binLifecycle());
        }
    }

