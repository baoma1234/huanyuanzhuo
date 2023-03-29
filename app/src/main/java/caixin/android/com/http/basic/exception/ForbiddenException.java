package caixin.android.com.http.basic.exception;


import caixin.android.com.http.basic.config.HttpCode;
import caixin.android.com.http.basic.exception.base.BaseException;

public class ForbiddenException extends BaseException {

    public ForbiddenException() {
        super(HttpCode.CODE_PARAMETER_INVALID, "404错误");
    }

}