package com.zhiyun88.www.module_main.main.mvp.model;

import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.main.api.MainServiceApi;
import com.zhiyun88.www.module_main.main.mvp.contranct.MyCourseContranct;

import io.reactivex.Observable;

public class MyCourseModel implements MyCourseContranct.MyCourseModel {

    @Override
    public Observable getMyCourseData(int type,int page) {
        return HttpManager.newInstance().getService(MainServiceApi.class).getMyCourseData(type,page);
    }
}
