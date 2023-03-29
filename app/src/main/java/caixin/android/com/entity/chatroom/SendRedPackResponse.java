package caixin.android.com.entity.chatroom;

import java.io.Serializable;
import java.util.List;

public class SendRedPackResponse implements Serializable {


    /**
     * redbag : {"uid":"30","contents":"恭喜发财，大吉大利","addtime":"2020-01-03 20:19:26","pbgid":"183","pid":2,"touids":"","id":"1131","updatetime":"2020-01-03 20:19:26","status":1}
     * user : {"img":"http://35666j.com/uploads/avatars/20190716/8cad2675d066c4aadf686f2fb4b4d95a.jpg","lastacttime":"1578053939","tzstatus":1,"is_gl":0,"nikename":"阿信","session_id":"1","id":30,"status":1}
     * users : []
     */

    private MessageResponse redbag;
    private MessageResponse.UserBean user;
    private List<MessageResponse.UserBean> users;

    public MessageResponse getRedbag() {
        return redbag;
    }

    public void setRedbag(MessageResponse redbag) {
        this.redbag = redbag;
    }

    public MessageResponse.UserBean getUser() {
        return user;
    }

    public void setUser(MessageResponse.UserBean user) {
        this.user = user;
    }

    public List<?> getUsers() {
        return users;
    }

    public void setUsers(List<MessageResponse.UserBean> users) {
        this.users = users;
    }

    public static class RedbagBean {
        /**
         * uid : 30
         * contents : 恭喜发财，大吉大利
         * addtime : 2020-01-03 20:19:26
         * pbgid : 183
         * pid : 2
         * touids :
         * id : 1131
         * updatetime : 2020-01-03 20:19:26
         * status : 1
         */

        private String uid;
        private String contents;
        private String addtime;
        private String pbgid;
        private int pid;
        private String touids;
        private String id;
        private String updatetime;
        private int status;

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

        public String getPbgid() {
            return pbgid;
        }

        public void setPbgid(String pbgid) {
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

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

}
