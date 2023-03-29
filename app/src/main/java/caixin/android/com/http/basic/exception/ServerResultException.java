package caixin.android.com.http.basic.exception;


import caixin.android.com.http.basic.exception.base.BaseException;

public class ServerResultException extends BaseException {

    public ServerResultException(int code, String message) {
        super(code, message);
    }

}
