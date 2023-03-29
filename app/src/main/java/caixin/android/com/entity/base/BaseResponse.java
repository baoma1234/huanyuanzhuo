package caixin.android.com.entity.base;

/**
 * Created by goldze on 2017/5/10.
 * 该类仅供参考，实际业务返回的固定字段, 根据需求来定义，
 */
public class BaseResponse<T> {
    private int errorcode;
    private String msg;
    private int status;
    private int type;
    private T data;
    public static final int ERRORCODE_ERROR = 500;
    public static final int ERRORCODE_SUCCESS = 200;
    public static final int ERRORCODE_SYSTEM = 600;
    public static final int ERRORCODE_TOKEN_INVAILABLE = 700;

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

    public T getData() {
        return data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "errorcode=" + errorcode +
                ", msg='" + msg + '\'' +
                ", \ndata=" + data.toString() +
                '}';
    }
}
