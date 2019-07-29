package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.realnameauth;

import com.primecloud.huafenghuang.base.BaseSubscrible;

import retrofit2.Response;

public class RealNameAuthPresenter extends RealNameAuthContract.Presenter{
    @Override
    public void userBindingNameAndIDCard(String userId, String realName, String iDCard) {

        mRxManager.add(mModel.userBindingNameAndIDCard(userId, realName, iDCard).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> objectResponse) {

                mView.authSuccess();
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }
}
