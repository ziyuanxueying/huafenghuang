package com.primecloud.library.baselibrary.error;

/**
 * Created by zy on 2018/10/19.
 */

/**
 * 没有数据
 */
public class NoDataError extends Throwable {
    public NoDataError(String message){
        super(message);
    }
}
