package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;


import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.MaterialBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ResourceTag;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

public class MaterialModel implements MaterialContract.Model {
    @Override
    public Observable<Response<AccountBean>> getMyAccountFirstPage(String userId) {
        return NetWorks.getInstance()
                .getApi()
                .getMyAccountFirstPage(userId)
                .compose(RxSchedulerHelper.applySchedulers());
    }


    @Override
    public Observable<Response<MaterialBean>> getResourceVideo(String userId, int pageNumber, int pageSize, int tagId) {
        return NetWorks.getInstance()
                .getApi()
                .getResourceVideo(userId, pageNumber, pageSize, tagId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<MaterialBean>> getResourceImageDocument(String userId, int pageNumber, int pageSize, int tagId) {
        return NetWorks.getInstance()
                .getApi()
                .getResourceImageDocument(userId, pageNumber, pageSize, tagId)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<LikeResultBean>> resourceLike(String userId, int resoutcdid) {
        return NetWorks.getInstance()
                .getApi()
                .resourceLike(userId, resoutcdid)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> resourceDislike(String userId, int resoutcdid) {
        return NetWorks.getInstance()
                .getApi()
                .resourceDislike(userId, resoutcdid)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<Object>> handleAmount(String userId, String resourceId, int flag, int operateType) {
        return NetWorks.getInstance()
                .getApi()
                .handleAmount(userId, resourceId, flag, operateType)
                .compose(RxSchedulerHelper.applySchedulers());
    }

    @Override
    public Observable<Response<ResourceTag>> getResourceTags(int type) {
        return NetWorks.getInstance()
                .getApi()
                .getResourceTags(type)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
