package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member;

import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.library.baselibrary.log.XLog;

import retrofit2.Response;

/**
 * Created by ${qc} on 2019/5/14.
 */

public class MemberPresenter extends MemberContract.Presenter {

    @Override
    public void getMemberList(String userId, int memberType, int pagNum) {

        mRxManager.add(mModel.getMemberList(userId,memberType,pagNum).subscribe(new BaseSubscrible<Response<MemberBean>>() {
            @Override
            public void onCompleted() {
                mView.stopLoad();
            }

            @Override
            public void onSuccess(Response<MemberBean> dataBeanResponse) {
                mView.showMemberList(dataBeanResponse.body());
            }

            @Override
            public void onFail(String errorMsg) {
                mView.noDate();
            }
        }));
    }
}
