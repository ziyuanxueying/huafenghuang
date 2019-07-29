package com.primecloud.huafenghuang.ui.home.coursefragment;

import com.primecloud.huafenghuang.ui.home.coursefragment.bean.CourseBean;
import com.primecloud.huafenghuang.ui.home.coursefragment.bean.MainCourseBean;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;



import retrofit2.Response;
import rx.Observable;

public interface MainCourseContract {

    interface View extends BaseView
    {
        //发现—获取所有分类
        void mainCourseView(MainCourseBean mainCourseBean);
        //根据分类查询课程列表
        void listByCateGoryView(CourseBean courseBean);
    }

    interface Model extends BaseModel
    {
        //发现—获取所有分类
        Observable<Response<MainCourseBean>> mainCourseModel();
        //根据分类查询课程列表
        Observable<Response<CourseBean>> listByCateGoryModel(int pageNum,int categoryId);
    }


    public abstract class Presenter extends BasePresenter<View,Model>
    {
        //发现—获取所有分类
        public abstract void mainCoursePresenter();
        //根据分类查询课程列表
        public abstract  void listByCateGoryPresenter(int pageNum,int categoryId);
    }
}
