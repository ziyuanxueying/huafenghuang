package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.order;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/15.
 */

public class OrderModel implements OrderContract.Model {
    @Override
    public Observable<Response<OrderBean>> getOrderList(String userId, int pageNum) {
        return NetWorks.getInstance()
                .getMyApi()
                .getOrders(userId,pageNum)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
