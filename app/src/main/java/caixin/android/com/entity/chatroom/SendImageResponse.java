package caixin.android.com.entity.chatroom;

import java.util.List;

public class SendImageResponse {

    /**
     * uid : 30
     * contents :
     * addtime : 2020-01-04 14:31:27
     * updatetime : 2020-01-04 14:31:27
     * pbgid : 0
     * pid : 3
     * touids :
     * id : 1166
     * user : {"id":30,"nikename":"阿信","img":"http://35666j.com/uploads/avatars/20190716/8cad2675d066c4aadf686f2fb4b4d95a.jpg","is_gl":0,"status":1,"tzstatus":1,"lastacttime":"1578119036","session_id":"10"}
     * users : []
     * img : {"imgurl":"http:/dev.chat-app.com/uploads/2020-01-04/20200104143127.png","thumb":"http:/dev.chat-app.com/uploads/2020-01-04/20200104143127.png"}
     */

    private String uid;
    private String contents;
    private String addtime;
    private String updatetime;
    private int pbgid;
    private int pid;
    private String touids;
    private String id;
    private MessageResponse.UserBean user;
    private ImgBean img;
    private List<MessageResponse.UserBean> users;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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

    public String getTouids() {
        return touids;
    }

    public void setTouids(String touids) {
        this.touids = touids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MessageResponse.UserBean getUser() {
        return user;
    }

    public void setUser(MessageResponse.UserBean user) {
        this.user = user;
    }

    public ImgBean getImg() {
        return img;
    }

    public void setImg(ImgBean img) {
        this.img = img;
    }

    public List<?> getUsers() {
        return users;
    }

    public void setUsers(List<MessageResponse.UserBean> users) {
        this.users = users;
    }

    public static class ImgBean {
        /**
         * imgurl : http:/dev.chat-app.com/uploads/2020-01-04/20200104143127.png
         * thumb : http:/dev.chat-app.com/uploads/2020-01-04/20200104143127.png
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
}
