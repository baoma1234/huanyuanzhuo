package caixin.android.com.entity;

import java.util.List;

public class MediaInfo {

    /**
     * types : 1
     * year : 2019
     * type : 3
     * qscount : [{"id":3,"qs":"51期"}]
     * qs : 51期
     * message : {"author":"","time":"2019-06-06 00:13:35","num":2,"text_content":"","img_url":".ffk.gif","video_url":null}
     * toucount : 24
     * is_tou : 0
     * toudata : [{"id":1,"name":"狗","p_num":16}]
     * plnum : 0
     * pldata : {"hot":[{"id":2,"addtime":"2019-06-04 15:51:52","dianzan":15,"ishot":1,"contents":"出来吧","tximg":"","nikename":"大佬","tzimglist":["http://img.scss.com/img6.jpg"],"pl":[]}],"nhot":[{"id":1,"addtime":"2019-06-04 15:50:33","dianzan":0,"ishot":0,"contents":"我就看看行不行","tximg":"","nikename":"测试","tzimglist":[],"pl":[]}]}
     */
    private int id;
    private String types;
    private String year;
    private int type;
    private String qs;
    private ContentBean content;
    private int toucount;//当前文章的投票总数
    private int is_tou;//当前文章的是否可以投票 1可以 0不可以
    private int plnum;//当前文章的评论数
    private PlData pldata;
    private List<QscountBean> qscount;
    private List<ToudataBean> toudata;
    private String toudate;
    private int is_dz;
    private int is_gx;//是否更新 0没更新 1更新了
    private int is_sc;//是否收藏 0没收藏 1收藏了
    private List<ArticleDetailModel.AD> guanggao;
    private int typeid;//六合
    private String share_links;

    public String getShare_links() {
        return share_links;
    }

    public void setShare_links(String share_links) {
        this.share_links = share_links;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public List<ArticleDetailModel.AD> getGuanggao() {
        return guanggao;
    }

    public void setGuanggao(List<ArticleDetailModel.AD> guanggao) {
        this.guanggao = guanggao;
    }

    public String getToudate() {
        return toudate;
    }

    public void setToudate(String toudate) {
        this.toudate = toudate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_dz() {
        return is_dz;
    }

    public void setIs_dz(int is_dz) {
        this.is_dz = is_dz;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQs() {
        return qs;
    }

    public void setQs(String qs) {
        this.qs = qs;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public int getToucount() {
        return toucount;
    }

    public void setToucount(int toucount) {
        this.toucount = toucount;
    }

    public int getIs_tou() {
        return is_tou;
    }

    public void setIs_tou(int is_tou) {
        this.is_tou = is_tou;
    }

    public int getPlnum() {
        return plnum;
    }

    public void setPlnum(int plnum) {
        this.plnum = plnum;
    }

    public PlData getPldata() {
        return pldata;
    }

    public void setPldata(PlData pldata) {
        this.pldata = pldata;
    }

    public List<QscountBean> getQscount() {
        return qscount;
    }

    public void setQscount(List<QscountBean> qscount) {
        this.qscount = qscount;
    }

    public List<ToudataBean> getToudata() {
        return toudata;
    }

    public void setToudata(List<ToudataBean> toudata) {
        this.toudata = toudata;
    }

    public static class ContentBean {
        /**
         * author :
         * time : 2019-06-06 00:13:35
         * num : 2
         * text_content :
         * img_url : .ffk.gif
         * video_url : null
         */
        private int dianji;//六合
        private String author;
        private String time;
        private int num;
        private String text_content;
        private String img_url;
        private String video_url;
        private int dianzan;
        private String img;//六合用，无法吐槽

        public int getDianji() {
            return dianji;
        }

        public void setDianji(int dianji) {
            this.dianji = dianji;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getDianzan() {
            return dianzan;
        }

        public void setDianzan(int dianzan) {
            this.dianzan = dianzan;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getText_content() {
            return text_content;
        }

        public void setText_content(String text_content) {
            this.text_content = text_content;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }
    }

    public static class QscountBean {
        /**
         * id : 3
         * qs : 51期
         */

        private int id;
        private String qs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQs() {
            return qs;
        }

        public void setQs(String qs) {
            this.qs = qs;
        }
    }

    public int getIs_gx() {
        return is_gx;
    }

    public void setIs_gx(int is_gx) {
        this.is_gx = is_gx;
    }

    public int getIs_sc() {
        return is_sc;
    }

    public void setIs_sc(int is_sc) {
        this.is_sc = is_sc;
    }

}
