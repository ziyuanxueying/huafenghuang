package com.primecloud.library.baselibrary.base;

import android.os.Bundle;
import android.widget.Toast;

import com.primecloud.library.baselibrary.rx.util.TUtils;


public abstract class BasePresenterFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment implements BaseView{
    public P mPresenter;

    public M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtils.getT(this, 0);
        mModel = TUtils.getT(this, 1);
        if (this instanceof BaseView && mPresenter != null) {
            mPresenter.attachVM(this, mModel);
        }
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachVM();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(String msg) {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onInternetError() {

    }

    @Override
    public void onRequestEnd() {

    }
}
