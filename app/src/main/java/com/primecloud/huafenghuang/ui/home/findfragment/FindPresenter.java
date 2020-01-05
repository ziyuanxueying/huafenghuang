package com.primecloud.huafenghuang.ui.home.findfragment;

import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.findfragment.bean.FindItemBean;

import retrofit2.Response;

public class FindPresenter extends FindContract.Presenter {
    @Override
    public void listIndexCourses() {
     mRxManager.add(mModel.listIndexCourses().subscribe(new BaseSubscrible<Response<FindItemBean>>() {

         @Override
         public void onCompleted() {

         }

         @Override
         public void onSuccess(Response<FindItemBean> findItemBeanResponse) {
             mView.showCourseData(findItemBeanResponse.body());
         }

         @Override
         public void onFail(String errorMsg) {
             mView.onRequestError(errorMsg);
         }
     }));
    }

    @Override
    public void listTypePresenter(int pageNum,int categoryId) {
        mRxManager.add(mModel.listByCateGoryModel(pageNum,categoryId).subscribe(new BaseSubscrible<Response<CourseBean>>() {
            @Override
            public void onSuccess(Response<CourseBean> courseBeanResponse) {
                mView.showListData(courseBeanResponse.body());
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
