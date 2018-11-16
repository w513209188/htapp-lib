package com.zhiyun88.www.module_main.community.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.community.bean.CommunityDetailsBean;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentBean;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentListBean;

import java.util.List;

import io.reactivex.Observable;


public interface CommunityDetailsContranct {
    interface CommunityDetailsView extends MvpView{
        void isLoadMore(boolean isLoadMore);

        void CommentListData(int total, List<DetailsCommentListBean> list);
        //   void setCollectSuccess();
    }
    interface CommunityDetailsModel extends BaseModel{
        Observable<Result<CommunityDetailsBean>> getCommunityDetails(String question_id);
        Observable<Result<DetailsCommentBean>> getCommentList(String question_id, String st, int page);

    }
    abstract class CommunityDetailsPresenter extends BasePreaenter<CommunityDetailsView,CommunityDetailsModel>{
        public abstract void getCommunityDetails(String question_id);
        public abstract void getCommentList(String question_id,String st,int page);
    }
}
