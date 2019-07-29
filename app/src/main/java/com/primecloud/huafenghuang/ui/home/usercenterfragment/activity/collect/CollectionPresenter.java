package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.collect;

import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderContract;

import retrofit2.Response;

/**
 * Created by ${qc} on 2019/5/15.
 */

public class CollectionPresenter extends CollectionContract.Presenter{

    @Override
    public void getCollectionList(String userId, int pageNum) {
        mRxManager.add(mModel.getCollectionList(userId,pageNum).subscribe(new BaseSubscrible<Response<CollectionBean>>() {
            @Override
            public void onSuccess(Response<CollectionBean> collectionBeanResponse) {
                mView.showCollectionList(collectionBeanResponse.body());
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
