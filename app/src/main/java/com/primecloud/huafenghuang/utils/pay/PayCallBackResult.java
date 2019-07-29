package com.primecloud.huafenghuang.utils.pay;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.primecloud.huafenghuang.utils.ToastUtils;
import com.primecloud.primecloudpaysdk.version2_0.DealCallBackResult;

import org.json.JSONObject;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class PayCallBackResult extends DealCallBackResult {


    public static final int PAY_SUCCESS = 1;
    public static final int PAY_ERROR = 2;

    private Action1<Integer> action1;
    private Context context;

    public PayCallBackResult(Action1<Integer> action1, Context context) {

        this.action1 = action1;
        this.context = context;
    }

    @Override
    public void onError(String s) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(context, "订单支付失败");
            }
        });

        returnPayResult(PAY_ERROR);
    }


    @Override
    public void onSuccess(JSONObject orderInfo) {
        returnPayResult(PAY_SUCCESS);
    }

    private String string = "";

    @Override
    public void aLiPayResult(Map<String, String> map) {
        String resultStatus = map.get("resultStatus");
        switch (resultStatus) {
            case "9000":
                string = "订单支付成功";
                returnPayResult(PAY_SUCCESS);
                break;
            case "8000":
                string = "订单正在处理，请查询订单列表";
                returnPayResult(PAY_SUCCESS);
                break;
            case "4000":
            case "5000":
            case "6001":
            case "6002":
            case "6004":
            default:
                string = "支付失败";
                returnPayResult(PAY_ERROR);
                break;

        }
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showToast(context, string + ":" + resultStatus);
            }
        });


    }

    private void returnPayResult(int pay_state) {
        Observable
                .just(pay_state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1);
    }
}
