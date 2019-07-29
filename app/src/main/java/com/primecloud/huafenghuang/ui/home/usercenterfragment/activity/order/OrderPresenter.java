package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order;

import com.primecloud.huafenghuang.base.BaseSubscrible;

import retrofit2.Response;

/**
 * Created by ${qc} on 2019/5/15.
 */

public class OrderPresenter extends OrderContract.Presenter{
    @Override
    public void getOrderList(String userId, int pageNum) {
        mRxManager.add(mModel.getOrderList(userId,pageNum).subscribe(new BaseSubscrible<Response<OrderBean>>() {

            @Override
            public void onSuccess(Response<OrderBean> orderBeanResponse) {
                mView.showOrderList(orderBeanResponse.body());
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
