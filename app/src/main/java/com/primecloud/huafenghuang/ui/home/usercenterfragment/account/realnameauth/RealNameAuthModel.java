package com.primecloud.huafenghuang.ui.home.usercenterfragment.account.realnameauth;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

public class RealNameAuthModel implements RealNameAuthContract.Model{

    @Override
    public Observable<Response<Object>> userBindingNameAndIDCard(String userId, String realName, String iDCard) {
        return NetWorks.getInstance()
                .getApi()
                .userBindingNameAndIDCard(userId, realName, iDCard)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
