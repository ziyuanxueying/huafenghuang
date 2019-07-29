package com.primecloud.library.baselibrary.error;

import com.primecloud.library.baselibrary.entity.BaseEntity;

import rx.functions.Func1;

public class ResponseFunc<T> implements Func1<BaseEntity<T>, T> {
    @Override
    public T call(BaseEntity<T> tBaseEntity) {
//        if (!httpResult.getCode().equals(RESP_OK)) {
//            // 在此处抛出异常，subscribe的onError方法中会收到异常。
//            // 抛出的异常不能是会使系统崩溃的检查异常，如OOM
//            throw new IllegalStateException(httpResult.getMsg());
//        }
//        return_back httpResult.getData();
        return tBaseEntity.getData();
    }
}
