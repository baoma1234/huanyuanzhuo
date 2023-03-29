package caixin.android.com.entity;

/**
 * Created by jogger on 2019/6/13
 * 描述：
 */
public class NoticePopRequest {
    private int type;
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}