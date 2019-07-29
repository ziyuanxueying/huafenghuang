package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.group.member;

import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/14.
 */

public class MemberModel implements MemberContract.Model {

    @Override
    public Observable<Response<MemberBean>> getMemberList(String userId, int memberType, int pagNum) {
        return NetWorks.getInstance()
                .getMyApi()
                .getMembers(userId,memberType,pagNum)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
