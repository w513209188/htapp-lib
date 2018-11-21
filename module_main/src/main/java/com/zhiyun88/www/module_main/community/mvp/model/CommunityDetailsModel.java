package com.zhiyun88.www.module_main.community.mvp.model;

import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpManager;
import com.zhiyun88.www.module_main.community.api.CommunityServiceApi;
import com.zhiyun88.www.module_main.community.bean.CommunityDetailsBean;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentBean;
import com.zhiyun88.www.module_main.community.mvp.contranct.CommunityDetailsContranct;

import io.reactivex.Observable;

public class CommunityDetailsModel implements CommunityDetailsContranct.CommunityDetailsModel {
    @Override
    public Observable<Result<CommunityDetailsBean>> getCommunityDetails(String question_id) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getCommunityDetails(question_id, "1");
    }

    @Override
    public Observable<Result<DetailsCommentBean>> getCommentList(String question_id, String st, int page) {
        return HttpManager.newInstance().getService(CommunityServiceApi.class).getCommentList(question_id, "1", page);
    }
}
