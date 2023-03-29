package caixin.android.com.entity;

import java.io.Serializable;

public class ZhuanPanStatusEntity implements Serializable {

    private String status;  // status 1开 2关闭
    private String url;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ZhuanPanStatusEntity{" +
                "status='" + status + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
