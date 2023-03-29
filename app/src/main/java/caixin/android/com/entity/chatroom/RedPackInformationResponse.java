package caixin.android.com.entity.chatroom;

import java.util.List;

public class RedPackInformationResponse {
    private RedbagBean redbag;
    private List<GrabBean> grab;



    public RedbagBean getRedbag() {
        return redbag;
    }

    public void setRedbag(RedbagBean redbag) {
        this.redbag = redbag;
    }

    public List<GrabBean> getGrab() {
        return grab;
    }

    public void setGrab(List<GrabBean> grab) {
        this.grab = grab;
    }

    public static class RedbagBean {
        /**
         * uid : 47
         * size : 2
         * money : 2
         * addtime : 2020-05-05 15:42:13
         * message : 用户47-发送2个红包，共计2元
         * id : 340
         * user : {"id":47,"nikeName":"私人账号_","sbid":"","img":"https://35666t.com//base64/caixin/2020-03-31/1585638106989caixin.png","is_gl":0,"status":1,"tzstatus":1,"lastacttime":"1588569918","session_id":"7f0000010b5700004363","remind":1}
         */

        private String uid;
        private String size;
        private String money;
        private String addtime;
        private String message;
        private String id;
        private UserBean user;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 47
             * nikeName : 私人账号_
             * sbid :
             * img : https://35666t.com//base64/caixin/2020-03-31/1585638106989caixin.png
             * is_gl : 0
             * status : 1
             * tzstatus : 1
             * lastacttime : 1588569918
             * session_id : 7f0000010b5700004363
             * remind : 1
             */

            private int id;
            private String nikeName;
            private String sbid;
            private String img;
            private int is_gl;
            private int status;
            private int tzstatus;
            private String lastacttime;
            private String session_id;
            private int remind;

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

            public String getSbid() {
                return sbid;
            }

            public void setSbid(String sbid) {
                this.sbid = sbid;
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

            public int getRemind() {
                return remind;
            }

            public void setRemind(int remind) {
                this.remind = remind;
            }
        }
    }

    public static class GrabBean {
        /**
         * id : 8755
         * rid : 340
         * uid : 103
         * money : 1.00
         * addtime : 2020-05-05 16:42:46
         * sort : 1
         * user : {"id":103,"nikeName":"定","sbid":"1507bfd3f75af740fd7","img":"http://128.14.144.72/images/my/icon_avatar.png","is_gl":0,"status":1,"tzstatus":1,"lastacttime":"1588667714","session_id":"7f00000108fe000007ef","remind":1}
         */

        private int id;
        private int rid;
        private int uid;
        private String money;
        private String addtime;
        private int sort;
        private UserBeanX user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public static class UserBeanX {
            /**
             * id : 103
             * nikeName : 定
             * sbid : 1507bfd3f75af740fd7
             * img : http://128.14.144.72/images/my/icon_avatar.png
             * is_gl : 0
             * status : 1
             * tzstatus : 1
             * lastacttime : 1588667714
             * session_id : 7f00000108fe000007ef
             * remind : 1
             */

            private int id;
            private String nikeName;
            private String sbid;
            private String img;
            private int is_gl;
            private int status;
            private int tzstatus;
            private String lastacttime;
            private String session_id;
            private int remind;

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

            public String getSbid() {
                return sbid;
            }

            public void setSbid(String sbid) {
                this.sbid = sbid;
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

            public int getRemind() {
                return remind;
            }

            public void setRemind(int remind) {
                this.remind = remind;
            }
        }
    }
}
