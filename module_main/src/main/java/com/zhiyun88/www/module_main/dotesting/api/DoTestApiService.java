package com.zhiyun88.www.module_main.dotesting.api;

import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.dotesting.bean.PaperTestBean;
import com.zhiyun88.www.module_main.dotesting.bean.QestionTestBean;
import com.zhiyun88.www.module_main.dotesting.bean.SubmitTestBean;
import com.zhiyun88.www.module_main.dotesting.config.DoTestHttpUrlConfig;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DoTestApiService {

    @GET(DoTestHttpUrlConfig.QUESTIONNAIRE)
    Observable<Result<QestionTestBean>> getQuestionNaire(@Query("naire_id") String naireId, @Query("task_id") String taskId);

    @FormUrlEncoded
    @POST(DoTestHttpUrlConfig.PAPERQUESTION)
    Observable<Result<PaperTestBean>> getPaperQuestion(@Field("paper_id") String naireId, @Field("task_id") String taskId);

    @FormUrlEncoded
    @POST(DoTestHttpUrlConfig.SUBMIT)
    Observable<Result> submitTest(@FieldMap() Map<String, String> map);
}
