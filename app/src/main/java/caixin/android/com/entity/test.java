package caixin.android.com.entity;

import java.util.List;

public class test {


    /**
     * img : []
     * pbgid : 0
     * groups : []
     * pid : 1
     * users : [{"img":"https://ksxlfcc.com/images/my/icon_avatar.png","lastacttime":"1584002677","tzstatus":1,"is_gl":0,"nikename":"阿龙","id":45,"status":1}]
     * uid : 44
     * contents : 哈哈
     * addtime : 2020-03-16 16:43:53
     * togroups :
     * touids : 45,
     * id : 106
     * updatetime : 2020-03-16 16:43:53
     * user : {"img":"https://ksxlfcc.com//uploads/2020-03-12/thumb_20200312191130.jpeg","lastacttime":"1584348224","tzstatus":1,"is_gl":0,"nikename":"哈哈","id":44,"status":1}
     */

    private int pbgid;
    private int pid;
    private String uid;
    private String contents;
    private String addtime;
    private String togroups;
    private String touids;
    private String id;
    private String updatetime;
    private UserBean user;
    private List<?> img;
    private List<?> groups;
    private List<UsersBean> users;

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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<?> getImg() {
        return img;
    }

    public void setImg(List<?> img) {
        this.img = img;
    }

    public List<?> getGroups() {
        return groups;
    }

    public void setGroups(List<?> groups) {
        this.groups = groups;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UserBean {
        /**
         * img : https://ksxlfcc.com//uploads/2020-03-12/thumb_20200312191130.jpeg
         * lastacttime : 1584348224
         * tzstatus : 1
         * is_gl : 0
         * nikename : 哈哈
         * id : 44
         * status : 1
         */

        private String img;
        private String lastacttime;
        private int tzstatus;
        private int is_gl;
        private String nikename;
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

    public static class UsersBean {
        /**
         * img : https://ksxlfcc.com/images/my/icon_avatar.png
         * lastacttime : 1584002677
         * tzstatus : 1
         * is_gl : 0
         * nikename : 阿龙
         * id : 45
         * status : 1
         */

        private String img;
        private String lastacttime;
        private int tzstatus;
        private int is_gl;
        private String nikename;
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
