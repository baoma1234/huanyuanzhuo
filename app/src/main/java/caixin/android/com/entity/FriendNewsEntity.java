package caixin.android.com.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FriendNewsEntity {

    /**
     * id : 66
     * uid : 10
     * content : 门卫
     * is_open : 1
     * remind : 0
     * add_time : 2020-06-24 13:03:47
     * userInfo : {"nikename":"dahai","img":"http://35666t.com/img/caixin_android/mimage.jpg"}
     * imgs : [{"img":"https://files.jb51.net/skin/2018/images/jb51ewm.png"},{"img":"https://files.jb51.net/skin/2018/images/jb51ewm.png"},{"img":"https://files.jb51.net/skin/2018/images/jb51ewm.png"}]
     * comments : []
     * thumbs : []
     */

    private int id;
    private int uid;
    private String content;
    private String is_open;
    private String remind;
    private String add_time;
    private UserInfoBean userInfo;
    private List<String> imgs;
    private List<CommentsBean> comments;
    private List<UserInfoBean> praise;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<CommentsBean> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public List<UserInfoBean> getThumbs() {
        if (praise == null)
            praise = new ArrayList<>();
        return praise;
    }

    public void setThumbs(List<UserInfoBean> thumbs) {
        this.praise = thumbs;
    }

    public static class UserInfoBean {
        /**
         * nikename : dahai
         * img : http://35666t.com/img/caixin_android/mimage.jpg
         */

        private String nikename;
        private String img;
        private int id;

        public UserInfoBean(String nikename, String img, int id) {
            this.nikename = nikename;
            this.img = img;
            this.id = id;
        }

        public UserInfoBean() {
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
    }

    public static class CommentsBean {
        /**
         * id : 5
         * uid : 9
         * cfid : 77
         * reply : 0
         * content : 啊啊
         * add_time : 2020-06-26 13:49:54
         * nikename : Along
         * unikename :
         */

        @SerializedName("id")
        private int idX;
        @SerializedName("uid")
        private int uidX;
        private int cfid;
        private int reply;
        @SerializedName("content")
        private String contentX;
        @SerializedName("add_time")
        private String add_timeX;
        private String nikename;
        private String unikename;

        public int getIdX() {
            return idX;
        }

        public void setIdX(int idX) {
            this.idX = idX;
        }

        public int getUidX() {
            return uidX;
        }

        public void setUidX(int uidX) {
            this.uidX = uidX;
        }

        public int getCfid() {
            return cfid;
        }

        public void setCfid(int cfid) {
            this.cfid = cfid;
        }

        public int getReply() {
            return reply;
        }

        public void setReply(int reply) {
            this.reply = reply;
        }

        public String getContentX() {
            return contentX;
        }

        public void setContentX(String contentX) {
            this.contentX = contentX;
        }

        public String getAdd_timeX() {
            return add_timeX;
        }

        public void setAdd_timeX(String add_timeX) {
            this.add_timeX = add_timeX;
        }

        public String getNikename() {
            return nikename;
        }

        public void setNikename(String nikename) {
            this.nikename = nikename;
        }

        public String getUnikename() {
            return unikename;
        }

        public void setUnikename(String unikename) {
            this.unikename = unikename;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CommentsBean that = (CommentsBean) o;
            return idX == that.idX;
        }

        @Override
        public int hashCode() {
            return Objects.hash(idX, uidX, cfid, reply, contentX, add_timeX, nikename, unikename);
        }
    }
}
