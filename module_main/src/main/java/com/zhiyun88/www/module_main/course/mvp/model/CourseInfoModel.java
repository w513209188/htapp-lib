package com.zhiyun88.www.module_main.course.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.course.api.CourseServiceApi;
import com.zhiyun88.www.module_main.course.bean.CourseInfoBean;
import com.zhiyun88.www.module_main.course.mvp.contranct.CourseInfoContranct;

import io.reactivex.Observable;

public class CourseInfoModel implements CourseInfoContranct.CourseInfoModel {
    @Override
    public Observable<Result<CourseInfoBean>> getCourseInfoData(String basis_id) {
        return HttpManager.newInstance().getService(CourseServiceApi.class).getCourseInfo(basis_id);
    }

    @Override
    public Observable<Result> buyCourse(String basis_id) {
        return HttpManager.newInstance().getService(CourseServiceApi.class).joinCourse(basis_id);
    }
}
