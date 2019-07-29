package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.realnameauth;

import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;

public interface RealNameAuthContract {
    interface View extends BaseView {
        void authSuccess();
    }
    interface Model extends BaseModel {

        /**
         * 用户实名认证
         * @param userId
         * @return
         */
        Observable<Response<Object>> userBindingNameAndIDCard(String userId, String realName, String iDCard);
    }
    abstract class  Presenter extends BasePresenter<View, Model>{

        public abstract void  userBindingNameAndIDCard(String userId, String realName, String iDCard);
    }
}
