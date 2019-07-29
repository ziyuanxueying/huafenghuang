package com.primecloud.huafenghuang.ui.search.bean;


import com.primecloud.huafenghuang.base.BaseSubscrible;

import retrofit2.Response;

public class SearchPresenter extends SearchContract.Presenter {



    @Override
    public void searchPresenter(String search, int pageNumber) {
        mRxManager.add(mModel.searchModel(search, pageNumber).subscribe(new BaseSubscrible<Response<AllDataBean>>() {
            @Override
            public void onSuccess(Response<AllDataBean> allDataBeanResponse) {

                mView.searchView(allDataBeanResponse.body());
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
