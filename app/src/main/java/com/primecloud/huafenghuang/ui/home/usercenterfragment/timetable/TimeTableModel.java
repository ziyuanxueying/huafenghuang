package com.primecloud.huafenghuang.ui.home.usercenterfragment.timetable;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class TimeTableModel implements TimeTableContract.Model {
    //精品课程列表
    @Override
    public Observable<Response<TimeTableBean>> getTimeList(String userId, int pageNumber, int pageSize) {
        return NetWorks.getInstance()
                .getApi()
                .listTimeTable(userId, pageNumber, pageSize)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    //点赞
    @Override
    public Observable<Response<LikeResultBean>> courseLike(String userId, String courseId) {
        return NetWorks.getInstance()
                .getApi()
                .timeTableLike(userId,courseId)
                .compose(RxSchedulerHelper.applySchedulers());
    }
    //取消点赞
    @Override
    public Observable<Response<Object>> courseDisLike(String userId, String courseId) {
        return NetWorks.getInstance()
                .getApi()
                .timeTableDislike(userId, courseId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> handleAmountCourese(String userId, String courseId, int flag, int operateType) {
        return NetWorks.getInstance()
                .getApi()
                .handleAmountCourse(userId, courseId, flag, operateType)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
