package caixin.android.com.entity;

import java.util.List;

public class ArticleDetailModel {

    /**
     * title : 封为辅
     * contents : null
     * qishu : 061
     * addtime : 2019-06-04 20:36:43
     * dianzan : 12
     * dianji : 12
     * guanggao : ["http://pic26.nipic.com/20121218/6733828_144038516360_2.jpg","http://img3.imgtn.bdimg.com/it/u=962722419,4234919140&fm=26&gp=0.jpg","http://pic.58pic.com/58pic/13/74/94/21p58PICBDb_1024.jpg"]
     * plnum : 2
     * tzimglist : []
     * is_dz : 0
     * is_gztz : 0
     * is_gzlz : 0
     * nikename : 大佬
     * tximg :
     * pldata : [{"id":1,"addtime":"2019-06-04 15:50:33","dianzan":0,"ishot":0,"contents":"我就看看行不行","tximg":"","nikename":"测试","tzimglist":[],"pl":[{"nikename":"大佬","tximg":"","contents":"出来吧","addtime":"2019-06-04 15:51:52"}]}]
     */

    private String title;
    private String contents;
    private String qishu;
    private String taiqishu;
    private String addtime;
    private String updatetime;
    private int dianzan;
    private int dianji;
    private int plnum;
    private int is_dz;
    private int is_gztz;
    private int is_gzlz;
    private String nikename;
    private String tximg;
    private List<AD> guanggao;
    private List<String> tzimglist;
    private List<String> thumblist;
    private PlData pldata;
    private String share_links;
    private int uid;//发帖人id
    private int is_vip;
    private int jing;


    public int getJing() {
        return jing;
    }

    public void setJing(int jing) {
        this.jing = jing;
    }

    public String getTaiqishu() {
        return taiqishu;
    }

    public void setTaiqishu(String taiqishu) {
        this.taiqishu = taiqishu;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getShare_links() {
        return share_links;
    }

    public void setShare_links(String share_links) {
        this.share_links = share_links;
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

    public class AD {
        private String imgurl;
        private String url;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
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

    public int getDianji() {
        return dianji;
    }

    public void setDianji(int dianji) {
        this.dianji = dianji;
    }

    public int getPlnum() {
        return plnum;
    }

    public void setPlnum(int plnum) {
        this.plnum = plnum;
    }

    public int getIs_dz() {
        return is_dz;
    }

    public void setIs_dz(int is_dz) {
        this.is_dz = is_dz;
    }

    public int getIs_gztz() {
        return is_gztz;
    }

    public void setIs_gztz(int is_gztz) {
        this.is_gztz = is_gztz;
    }

    public int getIs_gzlz() {
        return is_gzlz;
    }

    public void setIs_gzlz(int is_gzlz) {
        this.is_gzlz = is_gzlz;
    }

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

    public List<AD> getGuanggao() {
        return guanggao;
    }

    public void setGuanggao(List<AD> guanggao) {
        this.guanggao = guanggao;
    }

    public List<String> getTzimglist() {
        return tzimglist;
    }

    public void setTzimglist(List<String> tzimglist) {
        this.tzimglist = tzimglist;
    }

    public PlData getPldata() {
        return pldata;
    }

    public void setPldata(PlData pldata) {
        this.pldata = pldata;
    }


}
