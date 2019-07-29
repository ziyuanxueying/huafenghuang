package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member;

import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/14.
 */

public interface MemberContract {

    interface View extends BaseView{
        void showMemberList(MemberBean memberBean);
        void stopLoad();
        void noDate();
    }

    interface Model extends BaseModel{
        Observable<Response<MemberBean>> getMemberList(String userId, int memberType, int pagNum);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void getMemberList(String userId,int memberType,int pagNum);
    }


}
