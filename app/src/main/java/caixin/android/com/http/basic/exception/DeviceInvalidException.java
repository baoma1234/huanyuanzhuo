package caixin.android.com.http.basic.exception;

import caixin.android.com.http.basic.config.HttpCode;
import caixin.android.com.http.basic.exception.base.BaseException;

public class DeviceInvalidException extends BaseException {

    public DeviceInvalidException() {
        super(HttpCode.CODE_DEVICE_INVALID, "新设备登录");
    }

    public DeviceInvalidException(String msg) {
        super(HttpCode.CODE_DEVICE_INVALID, msg);
    }

}
