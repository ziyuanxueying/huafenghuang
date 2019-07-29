package com.primecloud.huafenghuang.ui.home.usercenterfragment.timetable;

import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.library.baselibrary.log.XLog;

import retrofit2.Response;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class TimeTablePresenter extends TimeTableContract.Presenter {
    @Override
    public void showTimeTable(String userId, int pageNumber, int pageSize) {
        mRxManager.add(mModel.getTimeList(userId, pageNumber, pageSize).subscribe(new BaseSubscrible<Response<TimeTableBean>>() {
            @Override
            public void onSuccess(Response<TimeTableBean> timeTableBeanResponse) {
                mView.showTimeTableList(timeTableBeanResponse.body());
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
    public void courseLike(String userId, String courseId,int position) {
        mRxManager.add(mModel.courseLike(userId, courseId).subscribe(new BaseSubscrible<Response<LikeResultBean>>() {

            @Override
            public void onSuccess(Response<LikeResultBean> materialBeanResponse) {
                mView.tableLike(true,materialBeanResponse.body().getData(),position);
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
    public void courseDisLike(String userId, String courseId,int position) {
        mRxManager.add(mModel.courseDisLike(userId, courseId).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> stringResponse) {
                XLog.i("取消点赞"+stringResponse.body().toString());
                mView.tableLike(false,null,position);
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
    void handleAmountCourese(String userId, String courseId, int flag, int operateType, int position) {
        mRxManager.add(mModel.handleAmountCourese(userId, courseId, flag, operateType).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> objectResponse) {
                mView.showHandleAmoutResult(operateType, position);
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
