package com.primecloud.huafenghuang.ui.home.coursefragment;

import android.util.Log;

import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.home.coursefragment.MainCourseContract;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;

import retrofit2.Response;

public class MainCoursePresenter extends MainCourseContract.Presenter {

    @Override
    public void mainCoursePresenter() {
        mRxManager.add(mModel.mainCourseModel().subscribe(new BaseSubscrible<Response<MainCourseBean>>() {
            @Override
            public void onSuccess(Response<MainCourseBean> mainCourseBeanResponse) {
               mView.mainCourseView(mainCourseBeanResponse.body());
            }

            @Override
            public void onFail(String errorMsg) {

            }

            @Override
            public void onCompleted() {

            }
        }));
    }


    @Override
    public void listByCateGoryPresenter(int pageNum, int categoryId) {
        mRxManager.add(mModel.listByCateGoryModel(pageNum,categoryId).subscribe(new BaseSubscrible<Response<CourseBean>>() {
            @Override
            public void onSuccess(Response<CourseBean> courseBeanResponse) {
                mView.listByCateGoryView(courseBeanResponse.body());
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onRequestError(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }
}
