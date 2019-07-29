package com.primecloud.library.baselibrary.rx;

import com.primecloud.library.baselibrary.entity.BaseEntity;
import com.primecloud.library.baselibrary.error.TokenInvalidError;

import rx.Observable;
import rx.functions.Func1;

/**
 * 对返回的数据进行拦截判断
 */
public class RxResultHelper {

    public static <T> Observable.Transformer<BaseEntity<T>, T> handleResult() {
        return new Observable.Transformer<BaseEntity<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseEntity<T>> tObservable) {
                return tObservable.flatMap(
                        new Func1<BaseEntity<T>, Observable<T>>() {
                            @Override
                            public Observable<T> call(BaseEntity<T> tBaseEntity) {
                                if(tBaseEntity.getErrorCode() == BaseEntity.LOGIN_ERROR_CODE ||
                                        tBaseEntity.getErrorCode() == BaseEntity.MISS_BASEHEAD_CODE ||
                                        tBaseEntity.getErrorCode() == BaseEntity.MISS_LOGINHEAD_CODE ||
                                        tBaseEntity.getErrorCode() == BaseEntity.MISS_LOGININFO_CODE ||
                                        tBaseEntity.getErrorCode() == BaseEntity.NOT_LOGIN_PRESSION){
                                    return Observable.error(new Throwable(tBaseEntity.getMessage()));
                                }else if(tBaseEntity.getErrorCode() == BaseEntity.TOKEN_INVALID_CODE){// token失效
                                    return Observable.error(new TokenInvalidError("登陆失效，请重新登陆"));
                                } else if((tBaseEntity.getExcuCode() == BaseEntity.DATA_EXCU_CODE || tBaseEntity.getExcuCode() == BaseEntity.NO_DATA_EXCU_CODE)
                                        && tBaseEntity.getState() == 1){  // 请求成功
                                    return Observable.just(tBaseEntity.getData());
                                }
//                                else if((tBaseEntity.getExcuCode() == BaseEntity.NO_DATA_EXCU_CODE) && tBaseEntity.getState() == 1) {
//                                    return_back Observable.error(new NoDataError(tBaseEntity.getMessage()));
//                                }
                                return Observable.error(new Throwable(tBaseEntity.getMessage()));
                            }
                        }
                );
            }
        };
    }


//    public static <T> Observable.Transformer<Response<T>, T> handleResult() {
//        return new Observable.Transformer<Response<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<Response<T>> responseObservable) {
//                return responseObservable.flatMap(new Func1<Response<T>, Observable<T>>() {
//
//                    @Override
//                    public Observable<T> call(Response<T> tResponse) {
//                        int code = tResponse.code();
//                        return Observable.error(new Throwable(tResponse.getMessage()));
//                    }
//                });
//            }
//        };
//    }


}
