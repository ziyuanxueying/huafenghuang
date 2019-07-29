package com.primecloud.huafenghuang.ui.course;

import com.primecloud.huafenghuang.ui.course.bean.CommentBean;
import com.primecloud.huafenghuang.ui.course.bean.CourseDetailBean;
import com.primecloud.huafenghuang.ui.course.bean.OfflineCourseDetailBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;


import retrofit2.Response;
import rx.Observable;

public interface CourseContract {

    interface View extends BaseView
    {
        //免费/热门列表
        void listCourseByTypeView(CourseBean dataBean);
        //课程详情
        void courseDetailView(CourseDetailBean courseDetailBean);
        //评论/回复
        void commentView(CommentBean commentBean);
        //线下课程详情
        void offlineDetailsView(OfflineCourseDetailBean courseDetailBean);
    }

    interface Model extends BaseModel
    {
        //免费/热门列表
        Observable<Response<CourseBean>> listCourseByTypeModel(int pageNum, int queryType);
        //课程详情
        Observable<Response<CourseDetailBean>> courseDetailModel(int courseId,String pageNum,String pageType,int chapterId,String userId);
        //评论/回复
        Observable<Response<CommentBean>>  commentModel(String queryId,String pageNum,String queryType);
        //线下课程详情
        Observable<Response<OfflineCourseDetailBean>>  offlineDetailsModel(String courseId,String userId);
     }

    public abstract class Presenter extends BasePresenter<View,Model>
    {
        //免费/热门列表
        public abstract void listCourseByTypePresenter(int pageNum,int queryType);
        //课程详情
        public abstract void courseDetailPresenter(int courseId,String pageNum,String pageType,int chapterId,String userId);
        //评论/回复
        public abstract void commentPresenter(String queryId,String pageNum,String queryType);
        //线下课程详情
        public abstract  void offlineDetailsPresenter(String courseId,String userId);
    }
}
