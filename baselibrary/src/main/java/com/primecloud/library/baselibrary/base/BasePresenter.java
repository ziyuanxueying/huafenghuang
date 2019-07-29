package com.primecloud.library.baselibrary.base;


import com.primecloud.library.baselibrary.rx.RxManager;

/**
 * Created by Administrator on 2017/4/12.
 */

public abstract class BasePresenter<V, M> {
    public M mModel;
    public V mView;
    public RxManager mRxManager = new RxManager();

    public void attachVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }
    public void detachVM() {
        mView = null;
        mModel = null;
        mRxManager.clear();
    }
}