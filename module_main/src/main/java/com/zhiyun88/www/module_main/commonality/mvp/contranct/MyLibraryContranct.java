package com.zhiyun88.www.module_main.commonality.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.commonality.bean.MyLibraryBean;

import io.reactivex.Observable;

public interface MyLibraryContranct {
    interface MyLibraryView extends MvpView {
        void isLoadMore(boolean isLoadMore);
    }
    interface MyLibraryModel extends BaseModel {
        Observable<Result<MyLibraryBean>> getLibraryData(int page);
    }
    abstract class MyLibraryPresenter extends BasePreaenter<MyLibraryView,MyLibraryModel> {
        public abstract void getLibraryData(int page);
    }
}
