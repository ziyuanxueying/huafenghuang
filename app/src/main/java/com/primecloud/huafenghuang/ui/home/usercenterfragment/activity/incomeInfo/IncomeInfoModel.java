package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class IncomeInfoModel implements IncomeInfoContract.Model {
    @Override
    public Observable<Response<IncomeInfoBean>> showIncomeInfo(String userId, int pageNumber, int pageSize) {
        return NetWorks.getInstance()
                .getApi()
                .listIncomeInfo(userId,pageNumber,pageSize)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<ExpenditureInfoBean>> showExpandInfo(String userId, int pageNumber, int pageSize) {
        return NetWorks.getInstance()
                .getApi()
                .listExpenditureInfo(userId, pageNumber, pageSize)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
