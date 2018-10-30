package com.zhiyun88.www.module_main.main.mvp.presenter;


import com.wb.baselib.app.AppUtils;
import com.wb.baselib.appconfig.AppConfigManager;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.bean.MyCourseBean;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyCourseContranct;
import com.zhiyun88.www.module_main.main.mvp.model.MyCourseModel;

import io.reactivex.disposables.Disposable;


public class MyCoursePresenter extends MyCourseContranct.MyCoursePresenter {
    public MyCoursePresenter(MyCourseContranct.MyCourseView iView) {
        this.mView = iView;
        this.mModel = new MyCourseModel();
    }

    @Override
    public void getMyCourseData(int type, final int page) {
        HttpManager.newInstance().commonRequest(mModel.getMyCourseData(type,page), new BaseObserver<Result<MyCourseBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result<MyCourseBean> myCourseBeanResult) {
                if (myCourseBeanResult.getData() == null || myCourseBeanResult.getData().getList().size() == 0) {
                    if (page == 1) {
                        mView.NoData();
                    }else {
                        mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                        mView.loadMore(false);
                    }
                }else {
                    int maxPage = AppConfigManager.newInstance().getAppConfig().getMaxPage();
                    if (myCourseBeanResult.getData().getTotal() - page* maxPage  <= 0) {
                        //已经没有下一页了
                        mView.loadMore(false);
                    } else {
                        //还有下一页
                        mView.loadMore(true);
                    }
                    mView.SuccessData(myCourseBeanResult.getData().getList());
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.ErrorData();
                if (page == 1) {
                    mView.ErrorData();
                } else {
                    mView.showErrorMsg(AppUtils.getString(R.string.network_error));
                }
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        }, mView.binLifecycle());
    }
}

