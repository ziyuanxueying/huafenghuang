package com.primecloud.huafenghuang.ui.home.usercenterfragment.timetable;

import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/22.
 * 精品课程
 */

public interface TimeTableContract {

    interface View extends BaseView{
        void showTimeTableList(TimeTableBean timeTableBean);
        void tableLike(boolean isLike,LikeResultBean.DataBean like, int position);
        void showHandleAmoutResult(int operateType, int position);
    }
    interface Model extends BaseModel{
        Observable<Response<TimeTableBean>> getTimeList(String userId,int pageNumber,int pageSize);
        Observable<Response<LikeResultBean>> courseLike(String userId, String courseId);
        Observable<Response<Object>> courseDisLike(String userId,String courseId);

        Observable<Response<Object>> handleAmountCourese(String userId,String courseId, int flag, int operateType);

    }

    abstract class Presenter extends BasePresenter<View,Model>{
        abstract void showTimeTable(String userId,int pageNumber,int pageSize);
        abstract void courseLike(String userId,String courseId,int position);
        abstract void courseDisLike(String userId,String courseId,int position);
        abstract void handleAmountCourese(String userId,String courseId, int flag, int operateType, int position);
    }

}
