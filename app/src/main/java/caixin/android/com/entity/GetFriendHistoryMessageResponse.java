package caixin.android.com.entity;

import java.util.List;

public class GetFriendHistoryMessageResponse {


    /**
     * act : getMessage
     * data : [{"addtime":"2020-03-16 17:25:55","contents":"0","groups":[],"id":54,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 17:25:55","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 17:26:54","contents":"","groups":[],"id":55,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 17:26:54","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 17:26:54","contents":"","groups":[],"id":56,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 17:26:54","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 17:26:56","contents":"","groups":[],"id":57,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 17:26:56","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 17:26:56","contents":"","groups":[],"id":58,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 17:26:56","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 18:16:37","contents":"1","groups":[],"id":59,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 18:16:37","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 18:16:37","contents":"1","groups":[],"id":60,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 18:16:37","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 18:16:43","contents":"0","groups":[],"id":61,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 18:16:43","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 18:16:43","contents":"0","groups":[],"id":62,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 18:16:43","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]},{"addtime":"2020-03-16 18:55:04","contents":"0","groups":[],"id":63,"img":[],"pbgid":0,"pid":1,"togroups":"","touids":"","uid":17,"updatetime":"2020-03-16 18:55:04","user":{"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1},"users":[]}]
     * errorcode : 200
     * msg :
     */

    private String act;
    private int errorcode;
    private String msg;
    private List<DataBean> data;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * addtime : 2020-03-16 17:25:55
         * contents : 0
         * groups : []
         * id : 54
         * img : []
         * pbgid : 0
         * pid : 1
         * togroups :
         * touids :
         * uid : 17
         * updatetime : 2020-03-16 17:25:55
         * user : {"id":17,"img":"http://35666j.com/uploads/mrtou.png","is_gl":1,"lastacttime":"1566909096","nikename":"平凡","status":1,"tzstatus":1}
         * users : []
         */

        private String addtime;
        private String contents;
        private int id;
        private int pbgid;
        private int pid;
        private String togroups;
        private String touids;
        private int uid;
        private String updatetime;
        private UserBean user;
        private List<?> groups;
        private List<?> img;
        private List<?> users;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getTogroups() {
            return togroups;
        }

        public void setTogroups(String togroups) {
            this.togroups = togroups;
        }

        public String getTouids() {
            return touids;
        }

        public void setTouids(String touids) {
            this.touids = touids;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
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

        public List<?> getGroups() {
            return groups;
        }

        public void setGroups(List<?> groups) {
            this.groups = groups;
        }

        public List<?> getImg() {
            return img;
        }

        public void setImg(List<?> img) {
            this.img = img;
        }

        public List<?> getUsers() {
            return users;
        }

        public void setUsers(List<?> users) {
            this.users = users;
        }

        public static class UserBean {
            /**
             * id : 17
             * img : http://35666j.com/uploads/mrtou.png
             * is_gl : 1
             * lastacttime : 1566909096
             * nikename : 平凡
             * status : 1
             * tzstatus : 1
             */

            private int id;
            private String img;
            private int is_gl;
            private String lastacttime;
            private String nikename;
            private int status;
            private int tzstatus;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getLastacttime() {
                return lastacttime;
            }

            public void setLastacttime(String lastacttime) {
                this.lastacttime = lastacttime;
            }

            public String getNikename() {
                return nikename;
            }

            public void setNikename(String nikename) {
                this.nikename = nikename;
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
        }
    }
}
