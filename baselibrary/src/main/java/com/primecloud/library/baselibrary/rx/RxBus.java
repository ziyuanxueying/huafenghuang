package com.primecloud.library.baselibrary.rx;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by Administrator on 2017/4/12.
 */

public class RxBus {

    private static RxBus rxBus;
    /**
     * 过把整个Map分为N个Segment（类似HashTable），可以提供相同的线程安全，但是效率提升N倍，默认提升16倍。
     */
    private ConcurrentHashMap<Object, List<Subject>> concurrentHashMap = new ConcurrentHashMap<>();

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (null == rxBus) {
            synchronized (RxBus.class) {
                if (null == rxBus) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    /**
     * 订阅事件
     *
     * @param mObservable
     * @param mAction1
     * @return
     */
    public RxBus onEvent(Observable<?> mObservable, Action1<Object> mAction1) {

        mObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(mAction1, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
        return getInstance();
    }

    /**
     * RxBus的注册方法
     *
     * @param tag
     * @param <T>
     * @return
     */
    public <T> Observable<T> register(@NonNull Object tag) {
        // 可以为同一个tag 注册多个
        List<Subject> subjectList = concurrentHashMap.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            concurrentHashMap.put(tag, subjectList);
        }
        Subject<T, T> subject;
        subject = PublishSubject.create();
        subjectList.add(subject);
        return subject;
    }

    /**
     * 发布事件
     *
     * @param content
     */
    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    /**
     * 发布事件
     *
     * @param tag
     * @param content
     */
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjectList = concurrentHashMap.get(tag);
        if (!isEmpty(subjectList)) {
            // 投一个tag， 都发布事件
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    /**
     * 取消注册
     *
     * @param tag
     */
    @SuppressWarnings("rawtypes")
    public void unregister(@NonNull Object tag) {
        List<Subject> subjects = concurrentHashMap.get(tag);
        if (null != subjects) {
            concurrentHashMap.remove(tag);
        }
    }

    /**
     * 取消注册
     *
     * @param tag
     * @param observable
     */
    public RxBus unregister(@NonNull Object tag, @NonNull Observable<?> observable) {
        if (null == observable) {
            return getInstance();
        }
        List<Subject> subjectList = concurrentHashMap.get(tag);
        if (null != subjectList) {
            subjectList.remove((Subject<?, ?>) observable);
            if (isEmpty(subjectList)) {
                concurrentHashMap.remove(tag);
            }
        }
        return getInstance();
    }

    @SuppressWarnings("rawtypes")
    private static boolean isEmpty(Collection<Subject> collection) {
        return null == collection || collection.isEmpty();
    }

}
