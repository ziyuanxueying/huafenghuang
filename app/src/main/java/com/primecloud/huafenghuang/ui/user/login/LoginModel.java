package com.primecloud.huafenghuang.ui.user.login;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.library.baselibrary.entity.BaseEntity;
import com.primecloud.library.baselibrary.rx.RxResultHelper;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

public class LoginModel implements LoginContact.Model {
    @Override
    public Observable<Response<LoginBean>> login(String username, String password) {
        return NetWorks.getInstance()
                .getMyApi()
                .toLogin(username, password)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
