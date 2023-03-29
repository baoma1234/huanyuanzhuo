package caixin.android.com.entity.chatroom;

import android.text.TextUtils;

import caixin.android.com.utils.MMKVUtil;

import java.io.Serializable;
import java.util.List;

public class MessageResponse  implements Serializable {

    public static final int TYPE_TEXT = 1;    //1 表示为普通消息 2 表示为红包
    public static final int TYPE_IMAGE = 3;
    public static final int TYPE_RED_PACK = 2;
    /**
     * uid : 31
     * img : {"imgurl":"http://156.232.95.18:80/img/lhzjchat/2020-01-02/20200102221439.png","thumb":"http://156.232.95.18:80/img/lhzjchat/2020-01-02/20200102221439.png"}
     * contents :
     * addtime : 2020-01-02 22:14:39
     * pbgid : 0
     * pid : 3
     * id : 1052
     * touids :
     * updatetime : 2020-01-02 22:14:39
     * user : {"img":"http://47.75.251.38/uploads/mrtou.png","lastacttime":"1578031971","tzstatus":1,"is_gl":0,"nikename":"711","session_id":"6","id":31,"status":1}
     * users : []
     */

    private int uid;
    private ImgBean img;
    private String contents;
    private String addtime;
    private int pbgid;
    private int pid;
    private int id;
    private String touids;
    private String updatetime;
    private UserBean user;
    private List<UserBean> users;


    public int getType() {
        return getPid();
    }

    public boolean isFromSelf() {
        if (TextUtils.isEmpty(MMKVUtil.getToken()))
            return false;
        if (MMKVUtil.getUserInfo().getId() == Integer.valueOf(getUid()))
            return true;
        return false;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public ImgBean getImg() {
        return img;
    }

    public void setImg(ImgBean img) {
        this.img = img;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getPbgid() {
        return pbgid;
    }

    public void setPbgid(int pbgid) {
        this.pbgid = pbgid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTouids() {
        return touids;
    }

    public void setTouids(String touids) {
        this.touids = touids;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<?> getUsers() {
        return users;
    }

    public void setUsers(List<UserBean> users) {
        this.users = users;
    }

    public static class ImgBean {
        /**
         * imgurl : http://156.232.95.18:80/img/lhzjchat/2020-01-02/20200102221439.png
         * thumb : http://156.232.95.18:80/img/lhzjchat/2020-01-02/20200102221439.png
         */

        private String imgurl;
        private String thumb;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public static class UserBean implements Serializable{
        /**
         * img : http://47.75.251.38/uploads/mrtou.png
         * lastacttime : 1578031971
         * tzstatus : 1
         * is_gl : 0
         * nikename : 711
         * session_id : 6
         * id : 31
         * status : 1
         */

        private String img;
        private String lastacttime;
        private int tzstatus;
        private int is_gl;
        private String nikename;
        private String session_id;
        private int id;
        private int status;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLastacttime() {
            return lastacttime;
        }

        public void setLastacttime(String lastacttime) {
            this.lastacttime = lastacttime;
        }

        public int getTzstatus() {
            return tzstatus;
        }

        public void setTzstatus(int tzstatus) {
            this.tzstatus = tzstatus;
        }

        public int getIs_gl() {
            return is_gl;
        }

        public void setIs_gl(int is_gl) {
            this.is_gl = is_gl;
        }

        public String getNikename() {
            return nikename;
        }

        public void setNikename(String nikename) {
            this.nikename = nikename;
        }

        public String getSession_id() {
            return session_id;
        }

        public void setSession_id(String session_id) {
            this.session_id = session_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
