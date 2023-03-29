package caixin.android.com.entity;

/**
 * Created by jogger on 2019/6/3
 * 描述：
 */
public class UserInfo {


    /**
     * userinfo : {"id":13,"img":"","truename":"稍等","mobile":"17665380811","money":"3.50"}
     * fensi : {"fs_num":0,"dt_num":0}
     */

    private UserinfoBean userinfo;
    private UserCommonModel fensi;
    private ActiveBean actnum;
    private ZhanBean zhan;
    private KfBean kf;
    private MygzBean mygz;

    public ActiveBean getActnum() {
        return actnum == null ? actnum = new ActiveBean() : actnum;
    }

    public void setActnum(ActiveBean actnum) {
        this.actnum = actnum;
    }

    public UserinfoBean getUserinfo() {
        return userinfo == null ? userinfo = new UserinfoBean() : userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public UserCommonModel getFensi() {
        return fensi == null ? fensi = new UserCommonModel() : fensi;
    }

    public void setFensi(UserCommonModel fensi) {
        this.fensi = fensi;
    }

    public ZhanBean getZhan() {
        return zhan == null ? zhan = new ZhanBean() : zhan;
    }

    public void setZhan(ZhanBean zhan) {
        this.zhan = zhan;
    }

    public KfBean getKf() {
        return kf;
    }

    public void setKf(KfBean kf) {
        this.kf = kf;
    }

    public MygzBean getMygz() {
        return mygz;
    }

    public void setMygz(MygzBean mygz) {
        this.mygz = mygz;
    }

    public static class ActiveBean {

        @Override
        public String toString() {
            return "ActiveBean{" +
                    "activity_num=" + activity_num +
                    ", gznum=" + gznum +
                    '}';
        }

        private int activity_num;
        private int gznum;

        public int getActivity_num() {
            return activity_num;
        }

        public void setActivity_num(int activity_num) {
            this.activity_num = activity_num;
        }

        public int getGznum() {
            return gznum;
        }

        public void setGznum(int gznum) {
            this.gznum = gznum;
        }
    }

    public static class UserinfoBean {
        @Override
        public String toString() {
            return "UserinfoBean{" +
                    "id=" + id +
                    ", img='" + img + '\'' +
                    ", truename='" + truename + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", money='" + money + '\'' +
                    ", level=" + level +
                    ", levelimg='" + levelimg + '\'' +
                    ", incode='" + incode + '\'' +
                    ", token='" + token + '\'' +
                    ", nikename='" + nikename + '\'' +
                    ", ptai='" + ptai + '\'' +
                    ", levelname='" + levelname + '\'' +
                    '}';
        }

        /**
         * id : 13
         * img :
         * truename : 稍等
         * mobile : 17665380811
         * money : 3.50
         */



        private int id = -1;
        private String img;
        private String truename;
        private String mobile;
        private String money;
        private int level;//用户等级
        private String levelimg;//等级图片
        private String incode = "102919";
        private String token;
        private String nikename;
        private String ptai;
        private String levelname;

        public String getNikename() {
            return nikename;
        }

        public void setNikename(String nikename) {
            this.nikename = nikename;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getLevelimg() {
            return levelimg;
        }

        public void setLevelimg(String levelimg) {
            this.levelimg = levelimg;
        }

        public String getIncode() {
            return incode;
        }

        public void setIncode(String incode) {
            this.incode = incode;
        }

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

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getPtai() {
            return ptai;
        }

        public void setPtai(String ptai) {
            this.ptai = ptai;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }
    }

    public static class ZhanBean {
        private int zhan_num;

        public int getZhan_num() {
            return zhan_num;
        }

        public void setZhan_num(int zhan_num) {
            this.zhan_num = zhan_num;
        }

        @Override
        public String toString() {
            return "ZhanBean{" +
                    "zhan_num=" + zhan_num +
                    '}';
        }
    }

    public static class KfBean {

        @Override
        public String toString() {
            return "KfBean{" +
                    "kf_num=" + kf_num +
                    '}';
        }

        private int kf_num;

        public int getKf_num() {
            return kf_num;
        }

        public void setKf_num(int kf_num) {
            this.kf_num = kf_num;
        }
    }

    public static class MygzBean {

        @Override
        public String toString() {
            return "MygzBean{" +
                    "is_new=" + is_new +
                    '}';
        }

        private int is_new;

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userinfo=" + userinfo.toString() +
                ", fensi=" + fensi.toString() +
                ", actnum=" + actnum.toString() +
                ", zhan=" + zhan.toString() +
                ", kf=" + kf.toString() +
                ", mygz=" + mygz.toString() +
                '}';
    }
}
