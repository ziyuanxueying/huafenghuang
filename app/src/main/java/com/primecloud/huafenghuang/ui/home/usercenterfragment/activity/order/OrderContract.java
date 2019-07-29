package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order;

import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/15.
 */

public interface OrderContract {

    interface View extends BaseView{
        void showOrderList(OrderBean orderBean);
        void stopLoad();
        void noDate();
    }

    interface Model extends BaseModel{
        Observable<Response<OrderBean>> getOrderList(String userId,int pageNum);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getOrderList(String userId,int pageNum);
    }

}
