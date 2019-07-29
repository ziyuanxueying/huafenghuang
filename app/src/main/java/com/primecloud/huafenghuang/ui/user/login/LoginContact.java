package com.primecloud.huafenghuang.ui.user.login;

import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;


public interface LoginContact {

    interface Model extends BaseModel {
        Observable<Response<LoginBean>> login(String username, String password);
    }

    interface View extends BaseView {
        void loginResult(String msg);
        void loginToActivity(LoginBean loginBean);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        abstract void goLogin(String username, String password);
    }
}
