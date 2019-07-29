package com.primecloud.huafenghuang.ui.home.usercenterfragment.activity.info;

import com.luck.picture.lib.entity.LocalMedia;
import com.primecloud.huafenghuang.api.NetWorks;
import com.primecloud.huafenghuang.ui.user.UserInfo;
import com.primecloud.huafenghuang.utils.Utils;
import com.primecloud.library.baselibrary.log.XLog;
import com.primecloud.library.baselibrary.rx.RxSchedulerHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;

/**
 * Created by ${qc} on 2019/5/17.
 */

public class InfoModel implements InfoContract.Model {

    @Override
    public Observable<Response<UserHead>> upHead(String uid, List<LocalMedia> imagePaths) {
        Map<String, RequestBody> datas = new HashMap<String, RequestBody>();
        datas.put("userId", Utils.getTextRequestBody(uid));
        int size = imagePaths.size();
        File file = new File(imagePaths.get(0).getCutPath());
        XLog.i("PATH:"+file.getAbsolutePath());
        datas.put("file\";filename=\"" + file.getName(), Utils.getFileRequestBody(file));
//        for(int i = 0; i<size; i++){
//            XLog.i("size:"+size);
//            File file = new File(imagePaths.get(i).getCutPath());
//            XLog.i("PATH:"+file.getAbsolutePath());
//            datas.put("file\";filename=\"" + file.getName(), Utils.getFileRequestBody(file));
//        }
        return NetWorks.getInstance()
                .getMyApi()
                .enrollInfo(datas)
                .compose(RxSchedulerHelper.applySchedulers());
    }
}
