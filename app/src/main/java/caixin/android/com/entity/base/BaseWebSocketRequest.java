package caixin.android.com.entity.base;

import java.util.List;

public class BaseWebSocketRequest<T> {
    private String act;
    private List<T> data;
//    private String token;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

/*    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }*/

    @Override
    public String toString() {
        return "BaseWebSocketRequest{" +
                "act='" + act + '\'' +
                ", data=" + data +
                '}';
    }
}
