package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info;

import com.luck.picture.lib.entity.LocalMedia;
import com.primecloud.huafenghuang.base.BaseSubscrible;
import com.primecloud.huafenghuang.ui.user.UserInfo;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * Created by ${qc} on 2019/5/17.
 */

public class InfoPresenter extends InfoContract.Presenter{

    @Override
    public void upLoadHead(String uid, List<LocalMedia> imagePaths) {
        mRxManager.add(mModel.upHead(uid,imagePaths).subscribe(new BaseSubscrible<Response<UserHead>>() {
            @Override
            public void onSuccess(Response<UserHead> userInfoResponse) {
                mView.successHead(userInfoResponse.body());
            }

            @Override
            public void onFail(String errorMsg) {
                mView.onFHead(errorMsg);
            }

            @Override
            public void onCompleted() {

            }
        }));
    }
}
