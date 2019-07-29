package com.primecloud.huafenghuang.ui.search.bean;

import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;


public interface SearchContract  {

    interface View extends BaseView
    {
        void  searchView(AllDataBean dataBean);
    }

    interface Model extends BaseModel
    {
        Observable<Response<AllDataBean>> searchModel(String search, int pageNum);
    }

    public abstract  class Presenter extends BasePresenter<View,Model>
    {
        public  abstract  void searchPresenter(String search, int pageNumber);
    }
}
