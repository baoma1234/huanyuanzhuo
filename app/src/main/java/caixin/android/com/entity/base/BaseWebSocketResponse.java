package caixin.android.com.entity.base;

import java.util.List;

public class BaseWebSocketResponse<T> {

    public static final int ERRORCODE_ERROR = 500;
    public static final int ERRORCODE_SUCCESS = 200;
    public static final int ERRORCODE_SYSTEM = 600;
    /**
     * act : register
     * data : []
     * errorcode : 200
     * msg : 注册成功
     */

    private String act;
    private int errorcode;
    private String msg;
    private List<T> data;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseWebSocketResponse{" +
                "act='" + act + '\'' +
                ", errorcode=" + errorcode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
