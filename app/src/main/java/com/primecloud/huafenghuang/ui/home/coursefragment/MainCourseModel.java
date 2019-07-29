package com.primecloud.huafenghuang.ui.home.coursefragment;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseContract;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

public class MainCourseModel implements MainCourseContract.Model {

    @Override
    public Observable<Response<MainCourseBean>> mainCourseModel() {
        return NetWorks.getInstance()
                .getApi()
                .listAllCategory()
                .compose(RxSchedulerHelper.applySchedulers());
    }


    @Override
    public Observable<Response<CourseBean>> listByCateGoryModel(int pageNum, int categoryId) {
        return NetWorks.getInstance()
                .getApi()
                .listByCateGory(pageNum,categoryId)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
