package com.primecloud.huafenghuang.ui.course;

import android.util.Log;

import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;

import retrofit2.Response;

public class CoursePresenter  extends CourseContract.Presenter{

    @Override
    public void listCourseByTypePresenter(int pageNum, int queryType) {
        mRxManager.add(mModel.listCourseByTypeModel(pageNum,queryType).subscribe(new BaseSubscrible<Response<CourseBean>>() {
            @Override
            public void onSuccess(Response<CourseBean> dataBeanResponse) {
                mView.listCourseByTypeView(dataBeanResponse.body());
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


    @Override
    public void courseDetailPresenter(int courseId, String pageNum, String pageType,int chapterId,String userId) {
        mRxManager.add(mModel.courseDetailModel(courseId,pageNum,pageType,chapterId,userId).subscribe(new BaseSubscrible<Response<CourseDetailBean>>() {
            @Override
            public void onSuccess(Response<CourseDetailBean> courseDetailBeanResponse) {
                mView.courseDetailView(courseDetailBeanResponse.body());
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

    @Override
    public void commentPresenter(String queryId, String pageNum, String queryType) {
        mRxManager.add(mModel.commentModel(queryId,pageNum,queryType).subscribe(new BaseSubscrible<Response<CommentBean>>() {
            @Override
            public void onSuccess(Response<CommentBean> commentBeanResponse) {
                mView.commentView(commentBeanResponse.body());
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
    public void offlineDetailsPresenter(String courseId,String userId) {
        mRxManager.add(mModel.offlineDetailsModel(courseId,userId).subscribe(new BaseSubscrible<Response<OfflineCourseDetailBean>>() {
            @Override
            public void onSuccess(Response<OfflineCourseDetailBean> offlineCourseDetailBeanResponse) {
                mView.offlineDetailsView(offlineCourseDetailBeanResponse.body());
            }

            @Override
            public void onFail(String errorMsg) {
            }

            @Override
            public void onCompleted() {

            }
        }));
    }
}
