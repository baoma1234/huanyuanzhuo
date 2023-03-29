package caixin.android.com.entity;

import java.io.Serializable;

public class CustomerModel implements Serializable {
    private String name;
    private int id;
    private String ptname;
    private int status;//状态：0禁用 1正常
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPtname() {
        return ptname;
    }

    public void setPtname(String ptname) {
        this.ptname = ptname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomerModel(String name) {
        this.name = name;
    }
}
