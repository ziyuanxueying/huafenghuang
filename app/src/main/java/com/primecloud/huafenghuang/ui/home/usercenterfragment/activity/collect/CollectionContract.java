package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.collect;

import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderBean;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/15.
 */

public interface CollectionContract {

    interface View extends BaseView{
        void showCollectionList(CollectionBean orderBean);
        void stopLoad();
        void noDate();
    }

    interface Model extends BaseModel{
        Observable<Response<CollectionBean>> getCollectionList(String userId, int pageNum);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getCollectionList(String userId,int pageNum);
    }

}
