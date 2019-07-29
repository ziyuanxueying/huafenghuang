package com.primecloud.huafenghuang.ui.course;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

public class CourseModel implements CourseContract.Model {

    @Override
    public Observable<Response<CourseBean>> listCourseByTypeModel(int pageNum, int queryType) {
        return NetWorks.getInstance()
                .getApi()
                .listCourseByType(pageNum,queryType)
                .compose(RxSchedulerHelper.applySchedulers());
    }


    @Override
    public Observable<Response<CourseDetailBean>> courseDetailModel(int courseId, String pageNum, String pageType,int chapterId,String userId) {
        return NetWorks.getInstance()
                .getApi()
                .courseDetail(courseId,pageNum,pageType,chapterId,userId)
                .compose(RxSchedulerHelper.applySchedulers());
    }


    @Override
    public Observable<Response<CommentBean>> commentModel(String queryId, String pageNum, String queryType) {
        return NetWorks.getInstance()
                .getApi()
                .listCommentByQueryId(queryId,pageNum,queryType)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<OfflineCourseDetailBean>> offlineDetailsModel(String courseId,String userId) {
        return NetWorks.getInstance()
                .getApi()
                .listOfflineCourseDetail(courseId,userId)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
