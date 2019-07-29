package com.primecloud.library.baselibrary.error;

import android.util.Log;

import rx.Observer;


public abstract class BaseObserver<T> implements Observer<T> {
//    private Context context;
//    public BaseObserver(Context context) {
//        this.context = context;
//    }
    @Override
    public void onError(Throwable e) {
        Log.e("lvr", e.getMessage());
        // todo error somthing

        if(e instanceof ExceptionHandle.ResponeThrowable){
            onError((ExceptionHandle.ResponeThrowable)e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }






    public abstract void onError(ExceptionHandle.ResponeThrowable e);

}
