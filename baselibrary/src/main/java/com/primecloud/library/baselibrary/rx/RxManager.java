package com.primecloud.library.baselibrary.rx;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/4/13.
 */

public class RxManager {
    public RxBus rxBus = RxBus.getInstance();
    public Map<String, Observable<?>> mObservables = new HashMap<>();//管理观察源
    //概括：线程安全、由所有订阅者组的组
    public CompositeSubscription compositeSubscription = new CompositeSubscription();// 管理订阅者

    /**
     * Rbus的订阅
     *
     * @param eventName
     * @param action1
     */

    public void on(String eventName, Action1<Object> action1) {
        Observable<?> mObservable = rxBus.register(eventName);
        mObservables.put(eventName, mObservable);
        compositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(action1, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        }));
    }

    /**
     * 单纯的Observables 和 Subscribers管理
     * @param s
     */
    public void add(Subscription s){
        compositeSubscription.add(s);
    }

    /**
     * 单个presenter生命周期结束，取消订阅和所有rxbus观察
     */
    public void clear(){
        compositeSubscription.unsubscribe();//// 取消订阅
        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet()){
            rxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
        }
        mObservables.clear();
        mObservables = null;
        rxBus = null;
        compositeSubscription = null;

    }

    /**
     * 发送rxbus
     * @param tag
     * @param content
     */
    public void post(Object tag, Object content) {
        rxBus.post(tag, content);
    }
}
