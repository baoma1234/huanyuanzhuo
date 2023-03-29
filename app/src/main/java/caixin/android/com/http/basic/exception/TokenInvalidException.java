package caixin.android.com.http.basic.exception;


import caixin.android.com.http.basic.config.HttpCode;
import caixin.android.com.http.basic.exception.base.BaseException;

public class TokenInvalidException extends BaseException {

    public TokenInvalidException() {
        super(HttpCode.CODE_TOKEN_INVALID, "Token失效");
    }

    public TokenInvalidException(String msg) {
        super(HttpCode.CODE_TOKEN_INVALID, msg);
    }

}
