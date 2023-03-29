package caixin.android.com.entity;

/**
 * Created by jogger on 2019/7/6
 * 描述：
 */
public class AppVersion {

    /**
     * version : 1.1.2
     * contents : 更新APP
     * date : 2019-07-06 19:24:58
     * weblocal : http://www.baidu.com
     */

    private String version;
    private String contents;
    private String date;
    private String weblocal;
    private int is_update;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeblocal() {
        return weblocal;
    }

    public void setWeblocal(String weblocal) {
        this.weblocal = weblocal;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }
}
