package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.incomeInfo;

import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/22.
 */

public interface IncomeInfoContract {

    interface View extends BaseView{
        void showIncomeInfo(IncomeInfoBean infoBean);
        void showExpandInfo(ExpenditureInfoBean infoBean);
    }

    interface Model extends BaseModel{
        Observable<Response<IncomeInfoBean>> showIncomeInfo(String userId, int pageNumber, int pageSize);
        Observable<Response<ExpenditureInfoBean>> showExpandInfo(String userId, int pageNumber, int pageSize);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        abstract void getIncomeIncoList(String userId,int pageNumber,int pageSize);
        abstract void getExpandInfoList(String userId, int pageNumber, int pageSize);
    }

}
