package com.primecloud.library.baselibrary.error;

/**
 * Created by zy on 2018/10/21.
 * token失效error
 */

public class TokenInvalidError extends Throwable {
    public TokenInvalidError(String message){
        super(message);
    }
}
