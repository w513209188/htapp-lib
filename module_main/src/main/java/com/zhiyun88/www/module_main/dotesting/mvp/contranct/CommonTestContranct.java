package com.zhiyun88.www.module_main.dotesting.mvp.contranct;

import com.jungan.www.common_dotest.bean.QuestionBankBean;
import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;

import java.util.List;

import io.reactivex.Observable;

public interface CommonTestContranct {
    interface CommonTestView extends BaseView {
        void submitSuccess(String msg);
    }

    interface CommonTestModel extends BaseModel {
        Observable<List<QuestionBankBean>> getCommonTest(String id, String taskId, int testType);
        Observable<Result> submitTest(String report_id, String type, String answer_time, String answer_data);
    }

    abstract class CommonTestPresenter extends BasePreaenter<CommonTestView, CommonTestModel> {
        public abstract void getCommonTest(String id, String taskId,int testType);
        public abstract void submitTest(String report_id, String type,String answer_time,String answer_data);
    }
}