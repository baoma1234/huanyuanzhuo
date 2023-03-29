package caixin.android.com.http.basic.exception;

import caixin.android.com.http.basic.config.HttpCode;
import caixin.android.com.http.basic.exception.base.BaseException;

/**
 * 作者：leavesC
 * 时间：2018/10/27 8:11
 * 描述：
 */
public class AccountInvalidException extends BaseException {

    public AccountInvalidException() {
        super(HttpCode.CODE_ACCOUNT_INVALID, "账号或者密码错误");
    }

}
