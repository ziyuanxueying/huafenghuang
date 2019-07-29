package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message;

import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;


import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/17.
 */

public interface MessageContract {

    interface View extends BaseView{
        void showMessageList(MessageBean messageBean);
        void stopLoad();
        void noDate();
    }

    interface Model extends BaseModel{
        Observable<Response<MessageBean>> getMessageList(String userId, int pageNum);
    }

    abstract class Presenter extends BasePresenter<View,Model> {
        abstract void getMessage(String userId, int pageNum);
    }

}
