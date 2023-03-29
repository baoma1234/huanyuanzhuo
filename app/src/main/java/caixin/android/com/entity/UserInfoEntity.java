package caixin.android.com.entity;

public class UserInfoEntity {

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "id=" + id +
                ", nikeName='" + nikeName + '\'' +
                ", password='" + password + '\'' +
                ", incode='" + incode + '\'' +
                ", money='" + money + '\'' +
                ", img='" + img + '\'' +
                ", is_gl=" + is_gl +
                ", status=" + status +
                ", tzstatus=" + tzstatus +
                ", lastacttime=" + lastacttime +
                ", session_id='" + session_id + '\'' +
                ", remind=" + remind +
                ", login_ip='" + login_ip + '\'' +
                ", token='" + token + '\'' +
                ", spay=" + spay +
                ", pt_account='" + pt_account + '\'' +
                ", pt_name='" + pt_name + '\'' +
                '}';
    }

    /**
     * id : 30
     * nikeName : 阿信
     * password : e10adc3949ba59abbe56e057f20f883e
     * incode : 100030
     * money : 45.44
     * img : http://128.14.144.72/images/my/icon_avatar.png
     * is_gl : 0
     * status : 1
     * tzstatus : 1
     * lastacttime : 1590674887
     * session_id : 7f0000010b5600000002
     * remind : 1
     * login_ip : 110.54.157.219
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiZW5jaS5jYWl4aW4uY29tIiwiYXVkIjoiYmVuY2kiLCJpYXQiOjE1OTA2NzQ4ODcsImV4cCI6MTU5MTI3OTY4NywidWlkIjozMH0.KClJP9Oau83MPy2kOXNcBdVAGDlB4tjz6poKT3dDzn4
     * spay : 0
     * pt_account :
     * pt_name :
     */

    private int id;
    private String nikeName;
    private String password;
    private String incode;
    private String money;
    private String img;
    private int is_gl;
    private int status;
    private int tzstatus;
    private int lastacttime;
    private String session_id;
    private int remind;
    private String login_ip;
    private String token;
    private int spay;
    private String pt_account;
    private String pt_name;
    private String mobile;
    private String signature;
    private int isNew;

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIncode() {
        return incode;
    }

    public void setIncode(String incode) {
        this.incode = incode;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIs_gl() {
        return is_gl;
    }

    public void setIs_gl(int is_gl) {
        this.is_gl = is_gl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTzstatus() {
        return tzstatus;
    }

    public void setTzstatus(int tzstatus) {
        this.tzstatus = tzstatus;
    }

    public int getLastacttime() {
        return lastacttime;
    }

    public void setLastacttime(int lastacttime) {
        this.lastacttime = lastacttime;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public String getLogin_ip() {
        return login_ip;
    }

    public void setLogin_ip(String login_ip) {
        this.login_ip = login_ip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSpay() {
        return spay;
    }

    public void setSpay(int spay) {
        this.spay = spay;
    }

    public String getPt_account() {
        return pt_account;
    }

    public void setPt_account(String pt_account) {
        this.pt_account = pt_account;
    }

    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }
}
