package caixin.android.com.entity;

/**
 * Created by jogger on 2019/6/13
 * 描述：
 */
public class VerifyCodeRequest {
    private String username;
    private String type;
    private String newpass;
    private String repass;

    public String getMobile() {
        return username;
    }

    public void setMobile(String mobile) {
        this.username = mobile;
    }

    public String getCode() {
        return type;
    }

    public void setCode(String code) {
        this.type = code;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }
}
