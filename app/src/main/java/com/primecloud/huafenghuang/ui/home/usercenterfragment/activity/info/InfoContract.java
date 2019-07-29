package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info;

import com.luck.picture.lib.entity.LocalMedia;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.library.baselibrary.base.BaseModel;
import com.primecloud.library.baselibrary.base.BasePresenter;
import com.primecloud.library.baselibrary.base.BaseView;


import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/17.
 */

public interface InfoContract {

    interface View extends BaseView{
        void successHead(UserHead userInfo);
        void onFHead(String message);
    }

    interface Model extends BaseModel{
        Observable<Response<UserHead>> upHead(String uid,List<LocalMedia> imagePaths);
    }

    abstract class Presenter extends BasePresenter<View,Model>{
        public abstract void upLoadHead(String uid,List<LocalMedia> imagePaths);
    }
}
