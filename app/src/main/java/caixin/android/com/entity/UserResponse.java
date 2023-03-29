package caixin.android.com.entity;

public class UserResponse {
    /**
     * id : 31
     * nikename : 711
     * img : http://47.75.251.38/uploads/mrtou.png
     * is_gl : 0
     * status : 1
     * tzstatus : 1
     * lastacttime : 1577769413
     * session_id : 11
     */

    private int id;
    private String nikename;
    private String img;
    private int is_gl;
    private int status;
    private int tzstatus;
    private String lastacttime;
    private String session_id;
    private String token;

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", nikename='" + nikename + '\'' +
                ", img='" + img + '\'' +
                ", is_gl=" + is_gl +
                ", status=" + status +
                ", tzstatus=" + tzstatus +
                ", lastacttime='" + lastacttime + '\'' +
                ", session_id='" + session_id + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
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

    public String getLastacttime() {
        return lastacttime;
    }

    public void setLastacttime(String lastacttime) {
        this.lastacttime = lastacttime;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
