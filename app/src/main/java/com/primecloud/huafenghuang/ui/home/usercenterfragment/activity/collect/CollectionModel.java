package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.collect;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order.OrderContract;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/15.
 */

public class CollectionModel implements CollectionContract.Model {
    @Override
    public Observable<Response<CollectionBean>> getCollectionList(String userId, int pageNum) {
        return NetWorks.getInstance()
                .getMyApi()
                .getCollections(userId,pageNum)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
