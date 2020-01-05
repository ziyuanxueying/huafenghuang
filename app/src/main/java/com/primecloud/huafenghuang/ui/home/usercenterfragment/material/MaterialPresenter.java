package com.primecloud.huafenghuang.ui.home.usercenterfragment.material;

import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.account.bean.AccountBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.LikeResultBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.MaterialBean;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.bean.ResourceTag;

import retrofit2.Response;

public class MaterialPresenter extends MaterialContract.Presenter {
    @Override
    public void getMyAccountFirstPage(String userId) {
        mRxManager.add(mModel.getMyAccountFirstPage(userId).subscribe(new BaseSubscrible<Response<AccountBean>>() {
            @Override
            public void onSuccess(Response<AccountBean> accountBeanResponse) {
                mView.showMyAccountData(accountBeanResponse.body().getData());
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
    void getResourceVideo(String userId, int pageNumber, int pageSize, int tagId) {
        mRxManager.add(mModel.getResourceVideo(userId, pageNumber, pageSize, tagId).subscribe(new BaseSubscrible<Response<MaterialBean>>() {
            @Override
            public void onSuccess(Response<MaterialBean> materialBeanResponse) {
                mView.showVidoeData(materialBeanResponse.body().getData());
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
    void getResourceImageDocument(String userId, int pageNumber, int pageSize, int tagId) {

        mRxManager.add(mModel.getResourceImageDocument(userId, pageNumber, pageSize, tagId).subscribe(new BaseSubscrible<Response<MaterialBean>>() {
            @Override
            public void onSuccess(Response<MaterialBean> materialBeanResponse) {
                mView.showPicData(materialBeanResponse.body().getData());
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
    void resorceLike(String userId, int resoutcdid, int position) {
        mRxManager.add(mModel.resourceLike(userId, resoutcdid).subscribe(new BaseSubscrible<Response<LikeResultBean>>() {
            @Override
            public void onSuccess(Response<LikeResultBean> stringResponse) {
                mView.showLikeResult(true, position, stringResponse.body().getData());
            }

            @Override
            public void onFail(String errorMsg) {

                mView.onRequestError("点赞失败");
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    void resourceDislike(String userId, int resoutcdid, int position) {

        mRxManager.add(mModel.resourceDislike(userId, resoutcdid).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> stringResponse) {
                mView.showLikeResult(false, position, null);
            }

            @Override
            public void onFail(String errorMsg) {

                mView.onRequestError("取消点赞失败");
            }

            @Override
            public void onCompleted() {

            }
        }));
    }

    @Override
    void handleAmount(String userId, String resourceId, int flag, int operateType, int position) {
        mRxManager.add(mModel.handleAmount(userId, resourceId, flag, operateType).subscribe(new BaseSubscrible<Response<Object>>() {
            @Override
            public void onSuccess(Response<Object> objectResponse) {
                mView.handleAmountResult(operateType, position);
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
    void getResourceTags(int type) {
        mRxManager.add(mModel.getResourceTags(type).subscribe(new BaseSubscrible<Response<ResourceTag>>() {
            @Override
            public void onSuccess(Response<ResourceTag> resourceTagResponse) {
                mView.showResourceTags(resourceTagResponse.body().getData());
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
