package com.zhiyun88.www.module_main.library.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.library.bean.LibraryDataBean;

import io.reactivex.Observable;

public interface LibraryFragmentContranct {
    interface LibraryFragmentView extends MvpView{
        void isLoadMore(boolean isLoadMore);
        void setCollectedSuccess();
    }
    interface LibraryFragmentModel extends BaseModel{
        Observable<Result<LibraryDataBean>> getLibraryData(String id, int page);
        Observable<Result> setLibraryCollection(String libraryId, String userId, String isClick);
    }
    abstract class LibraryFragmentPresenter extends BasePreaenter<LibraryFragmentView,LibraryFragmentModel>{
        public abstract void getLibraryData(String id, int page);
        public abstract void setLibraryCollection(String libraryId, String userId,String isClick);
    }
}
