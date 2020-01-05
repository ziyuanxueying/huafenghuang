package com.primecloud.huafenghuang.ui.home.findfragment;


import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
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

    @Override
    public Observable<Response<CourseBean>> listByCateGoryModel(int pageNum, int categoryId) {
        return NetWorks.getInstance()
                .getApi()
                .listByCateGory(pageNum, categoryId)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}