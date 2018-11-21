package com.zhiyun88.www.module_main.community.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.BaseView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.community.bean.ImageBean;
import com.zhiyun88.www.module_main.community.bean.ImageListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public interface ReleaseTopicContranct {
    interface ReleaseTopicView extends BaseView{
        void commitSuccess(String msg);
    }
    interface ReleaseTopicModel extends BaseModel{
        Observable<Result> commitTopicData(String group_id, String title, String content, String is_anonymity, String path);
        Observable<Result<ImageListBean>> commitImage(Map<String, RequestBody> map);

    }
    abstract class ReleaseTopicPresenter extends BasePreaenter<ReleaseTopicView,ReleaseTopicModel>{
        public abstract void commitTopicData(String group_id,String title,String content,String is_anonymity,String path);
        public abstract void commitImage(Map<String, RequestBody> map);
    }
}
