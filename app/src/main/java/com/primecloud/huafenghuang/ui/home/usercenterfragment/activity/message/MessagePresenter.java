package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message;


import com.primecloud.huafenghuang.base.BaseSubscrible;

import retrofit2.Response;

/**
 * Created by ${qc} on 2019/5/17.
 */

public class MessagePresenter extends MessageContract.Presenter {
    @Override
    void getMessage(String userId, int pageNum) {
        mRxManager.add(mModel.getMessageList(userId,pageNum).subscribe(new BaseSubscrible<Response<MessageBean>>() {
            @Override
            public void onSuccess(Response<MessageBean> BeanResponse) {
                mView.showMessageList(BeanResponse.body());
            }

            @Override
            public void onFail(String errorMsg) {
                mView.noDate();
            }

            @Override
            public void onCompleted() {
                mView.stopLoad();
            }
        }));
    }
}
