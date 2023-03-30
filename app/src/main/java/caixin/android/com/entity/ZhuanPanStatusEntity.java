package caixin.android.com.entity;

import java.io.Serializable;

public class ZhuanPanStatusEntity implements Serializable {

    private String imgurl;  // status 1开 2关闭
    private String url;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
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
                "status='" + imgurl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
