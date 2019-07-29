package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.message;


import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/17.
 */

public class MessageModel implements MessageContract.Model{

    @Override
    public Observable<Response<MessageBean>> getMessageList(String userId, int pageNum) {
       return NetWorks.getInstance()
                .getMyApi()
                .getMessages(userId,pageNum)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
