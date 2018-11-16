package com.zhiyun88.www.module_main.dotesting.mvp.presenter;

import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitTestBean;
import com.zhiyun88.www.module_main.dotesting.mvp.contranct.CommonTestContranct;
import com.zhiyun88.www.module_main.dotesting.mvp.model.CommonTestModel;


import java.util.List;

import io.reactivex.disposables.Disposable;

public class CommonTestPresenter extends CommonTestContranct.CommonTestPresenter {
    public CommonTestPresenter(CommonTestContranct.CommonTestView iView) {
        this.mView=iView;
        this.mModel=new CommonTestModel();
    }


    @Override
    public void getCommonTest(String id, String taskId,int testType) {
        HttpManager.newInstance().commonRequest(mModel.getCommonTest(id,taskId,testType), new BaseObserver<List<QuestionBankBean>>(AppUtils.getContext()) {
            @Override
            public void onSuccess(List<QuestionBankBean> questionBankBeans) {
                if (questionBankBeans == null || questionBankBeans.size() == 0) {
                }else {
                    mView.SuccessData(questionBankBeans);
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
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

    @Override
    public void submitTest(String report_id, String type, String answer_time, String answer_data) {
        HttpManager.newInstance().commonRequest(mModel.submitTest(report_id, type, answer_time, answer_data), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSuccess(Result result) {
                    mView.submitSuccess(result.getMsg());
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
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