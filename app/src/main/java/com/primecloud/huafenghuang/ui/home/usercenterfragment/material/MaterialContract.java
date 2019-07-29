package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.MaterialBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ResourceTag;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import java.io.File;
import java.util.List;

import retrofit2.Response;
import rx.Observable;

public interface MaterialContract {
    interface View extends BaseView {

       void showPicData(List<MaterialBean.DataBean> picDocuments);

       void showVidoeData(List<MaterialBean.DataBean> videoDatas);

       void showLikeResult(boolean isLike, int position, LikeResultBean.DataBean dataBean);// true 点赞成功，  false 取消点赞成功

        void downLoadFileResult(List<File> files);

        void handleAmountResult(int operateType, int position);

        void showResourceTags(List<ResourceTag.DataBean> resourceTags);

    }
    interface Model extends BaseModel {

        /**
         * 获取视频资源接口
         * @param userId
         * @param pageNumber
         * @param pageSize
         * @return
         */
        Observable<Response<MaterialBean>> getResourceVideo(String userId, int pageNumber, int pageSize, int type);

        /**
         * 获取图文资源接口
         * @param userId
         * @param pageNumber
         * @param pageSize
         * @return
         */
        Observable<Response<MaterialBean>> getResourceImageDocument(String userId, int pageNumber, int pageSize, int type);

        /**
         * 资源点赞接口
         * @param userId
         * @param resoutcdid
         * @return
         */
        Observable<Response<LikeResultBean>> resourceLike(String userId, int resoutcdid);

        /**
         * 资源取消点赞接口
         * @param userId
         * @param resoutcdid
         * @return
         */
        Observable<Response<Object>> resourceDislike(String userId, int resoutcdid);

        /**
         *
         * @param userId
         * @param resourceId
         * @param flag 	1. 数量+1 2.数量-1
         * @param operateType 	1. 下载 2 复制 3.转发
         * @return
         */
        Observable<Response<Object>> handleAmount(String userId, String resourceId, int flag, int operateType);

        /**
         * 获取每日素材标分类列表
         * @param type 获取的资源类型，1=图文，2=视频
         * @return
         */
        Observable<Response<ResourceTag>> getResourceTags(int type);

    }
    abstract class  Presenter extends BasePresenter<View, Model> {

        abstract void getResourceVideo(String userId, int pageNumber, int pageSize, int type);

        abstract void getResourceImageDocument(String userId, int pageNumber, int pageSize, int type);

        abstract void resorceLike(String userId, int resoutcdid, int position);

        abstract void resourceDislike(String userId, int resoutcdid, int position);

        abstract void handleAmount(String userId, String resourceId, int flag, int operateType, int position);

        abstract void getResourceTags(int type);
    }
}
