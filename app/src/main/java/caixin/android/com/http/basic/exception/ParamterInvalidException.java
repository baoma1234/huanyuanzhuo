package caixin.android.com.http.basic.exception;


import caixin.android.com.http.basic.config.HttpCode;
import caixin.android.com.http.basic.exception.base.BaseException;

/**
 * 作者：leavesC
 * 时间：2018/10/25 21:39
 * 描述：
 *
 *
 */
public class ParamterInvalidException extends BaseException {

    public ParamterInvalidException() {
        super(HttpCode.CODE_PARAMETER_INVALID, "参数有误");
    }

}
