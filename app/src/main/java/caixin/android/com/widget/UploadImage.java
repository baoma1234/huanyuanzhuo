package caixin.android.com.widget;

import android.text.TextUtils;

/**
 * Created by jogger on 2019/6/28
 * 描述：
 */
public class UploadImage {
    private String localImg;
    private String serverImg;
    private int id;

    public boolean isServerImg() {
        return TextUtils.isEmpty(localImg);
    }

    public String getLocalImg() {
        return localImg;
    }

    public void setLocalImg(String localImg) {
        this.localImg = localImg;
    }

    public String getServerImg() {
        return serverImg;
    }

    public void setServerImg(String serverImg) {
        this.serverImg = serverImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
