package caixin.android.com.entity;

public class MyPlatformEntity {


    /**
     * id : 1
     * ptname : 奔驰棋牌
     * url : http://cx.com/admin/Home/index
     * status : 1
     */

    private int id;
    private String ptname;
    private String url;
    private int status;
    private int min;
    private String describe;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPtname() {
        return ptname;
    }

    public void setPtname(String ptname) {
        this.ptname = ptname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
