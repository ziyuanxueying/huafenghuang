package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo;

import com.luck.picture.lib.entity.LocalMedia;
import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info.InfoContract;

import java.util.List;

import retrofit2.Response;

/**
 * Created by ${qc} on 2019/5/22.
 */

public class IncomeInfoPresenter extends IncomeInfoContract.Presenter {

    @Override
    void getIncomeIncoList(String userId, int pageNumber, int pageSize) {
        mRxManager.add(mModel.showIncomeInfo(userId, pageNumber, pageSize).subscribe(new BaseSubscrible<Response<IncomeInfoBean>>() {
            @Override
            public void onSuccess(Response<IncomeInfoBean> incomeInfoBeanResponse) {
                mView.showIncomeInfo(incomeInfoBeanResponse.body());
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
    void getExpandInfoList(String userId, int pageNumber, int pageSize) {
        mRxManager.add(mModel.showExpandInfo(userId, pageNumber, pageSize).subscribe(new BaseSubscrible<Response<ExpenditureInfoBean>>() {
            @Override
            public void onSuccess(Response<ExpenditureInfoBean> expenditureInfoBeanResponse) {
                mView.showExpandInfo(expenditureInfoBeanResponse.body());
            }

            @Override
            public void onFail(String errorMsg) {

            }

            @Override
            public void onCompleted() {

            }
        }));
    }
}
