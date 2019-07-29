package com.primecloud.library.baselibrary.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/13.
 */

/**
 * 不要打断链式结构使用使用 RxJava的 compose() 操作符
 *
 */
public class RxSchedulerHelper {

    public static <T> Observable.Transformer<T, T> io_main(){
        return new Observable.Transformer<T, T>(){
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        // 生产线程
                        .subscribeOn(Schedulers.io())
                        // 消费线程
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }




    static final Observable.Transformer schedulersTransformer = new  Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable)  observable).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };
    /**
     * 注意：上面的类型转换是不安全的，这就要求你的 Transformer 实现确实是无关类型的。
     * 比如这里的线程调度的两个函数，他们是不会修改 Observable 的类型的，所以可以这样使用。
     * @param <T>
     * @return
     */

   public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }





}
