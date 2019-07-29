package com.primecloud.library.baselibrary.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.primecloud.library.baselibrary.rx.util.TUtils;


/**
 * Created by Administrator on 2016/12/28.
 * MVP 的  BaseActivity
 */

public abstract class BasePresenterActivity<P extends BasePresenter, M extends BaseModel> extends CommonBaseActivity implements BaseView{
    public P mPresenter;
    public M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtils.getT(this, 0);
        mModel = TUtils.getT(this, 1);
        if (this instanceof BaseView && mPresenter != null) {
            mPresenter.attachVM(this, mModel);
        }
        initData();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachVM();
        super.onDestroy();
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestError(String msg) {
//        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, msg,  Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onInternetError() {

    }
    @Override
    public void finish() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.finish();
    }
    /**
     * 跳转到其他 Activity
     * @param cls  目标Activity的Class
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }
}
