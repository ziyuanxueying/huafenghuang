package com.primecloud.huafenghuang.ui.home.findfragment;


import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.FindItemBean;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

public class FindModel implements FindContract.Model {
    @Override
    public Observable<Response<FindItemBean>> listIndexCourses() {
       return NetWorks.getInstance()
               .getApi()
               .listIndexCourses()
               .compose(RxSchedulerHelper.applySchedulers());

    }
}
