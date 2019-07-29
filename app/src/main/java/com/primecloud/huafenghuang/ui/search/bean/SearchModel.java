package com.primecloud.huafenghuang.ui.search.bean;


import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

public class SearchModel implements SearchContract.Model {

    @Override
    public Observable<Response<AllDataBean>> searchModel(String search, int pageNum) {
        return NetWorks.getInstance()
                .getApi()
                .searchCourses(search, pageNum)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
