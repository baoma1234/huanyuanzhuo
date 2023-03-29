package caixin.android.com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jogger on 2019/6/11
 * 描述：
 */
public class CommonModel implements Serializable {
    /**
     * id : 1
     * addtime : 2019-06-04 15:50:33
     * dianzan : 0
     * ishot : 0
     * contents : 我就看看行不行
     * tximg :
     * nikename : 测试
     * tzimglist : []
     * pl : [{"nikename":"大佬","tximg":"","contents":"出来吧","addtime":"2019-06-04 15:51:52"}]
     */

    private int id;
    private String addtime;
    private int dianzan;
    private int ishot;
    private String contents;
    private String tximg;
    private String nikename;
    private List<String> tzimglist;
    private List<String> thumblist;
    private List<PlBean> pl;
    private int uid;
    private int e_pl_num;
    private int is_vip;

    public int getE_pl_num() {
        return e_pl_num;
    }

    public void setE_pl_num(int e_pl_num) {
        this.e_pl_num = e_pl_num;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "CommonModel{" +
                "id=" + id +
                ", ishot=" + ishot +
                ", contents='" + contents + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getDianzan() {
        return dianzan;
    }

    public void setDianzan(int dianzan) {
        this.dianzan = dianzan;
    }

    public int getIshot() {
        return ishot;
    }

    public void setIshot(int ishot) {
        this.ishot = ishot;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTximg() {
        return tximg;
    }

    public void setTximg(String tximg) {
        this.tximg = tximg;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public List<String> getTzimglist() {
        return tzimglist;
    }

    public void setTzimglist(List<String> tzimglist) {
        this.tzimglist = tzimglist;
    }

    public List<PlBean> getPl() {
        return pl;
    }

    public void setPl(List<PlBean> pl) {
        this.pl = pl;
    }

    public List<String> getThumblist() {
        return thumblist;
    }

    public void setThumblist(List<String> thumblist) {
        this.thumblist = thumblist;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public static class PlBean implements Serializable {
        /**
         * nikename : 大佬
         * tximg :
         * contents : 出来吧
         * addtime : 2019-06-04 15:51:52
         */

        private String nikename;
        private String tximg;
        private String contents;
        private String addtime;

        public String getNikename() {
            return nikename;
        }

        public void setNikename(String nikename) {
            this.nikename = nikename;
        }

        public String getTximg() {
            return tximg;
        }

        public void setTximg(String tximg) {
            this.tximg = tximg;
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

        @Override
        public String toString() {
            return "PlBean{" +
                    "contents='" + contents + '\'' +
                    '}';
        }
    }
}
