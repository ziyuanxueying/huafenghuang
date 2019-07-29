package com.primecloud.huafenghuang.ui.home.findfragment;


import com.primecloud.huafenghuang.ui.home.findfragment.bean.FindItemBean;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;

public interface FindContract {
    interface View extends BaseView{
        public void showCourseData(FindItemBean findItemBean);
    }
    interface Model extends BaseModel{
        //发现-免费+热门课程
        Observable<Response<FindItemBean>> listIndexCourses();
    }
    abstract class  Presenter extends BasePresenter<View, Model>{
        //发现-免费+热门课程
        public abstract void listIndexCourses();
    }
}
