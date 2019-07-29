package com.primecloud.huafenghuang.ui.user.login;


import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.user.UserInfo;

import retrofit2.Response;


public class LoginPresenter extends LoginContact.Presenter {
    @Override
    public void goLogin(String username, String password) {

       mRxManager.add(mModel.login(username, password)
      .subscribe(new BaseSubscrible<Response<LoginBean>>() {
          @Override
          public void onSuccess(Response<LoginBean> userInfoResponse) {
              mView.loginToActivity(userInfoResponse.body());
          }

          @Override
          public void onFail(String errorMsg) {
              mView.loginResult(errorMsg);
              mView.onRequestError(errorMsg);
          }

          @Override
          public void onCompleted() {
          }
      }));
    }
}
