package caixin.android.com.entity;

import java.util.List;

/**
 * Created by jogger on 2019/6/27
 * 描述：
 */
public class HomeKJModel {

    /**
     * currnum : 070
     * open_date : 2019-06-22
     * next_open_date : 2019-06-25
     * zhibo : 1788
     * hao : [{"number":"29","color":"red","sx":"羊"},{"number":"33","color":"green","sx":"兔"},{"number":"13","color":"red","sx":"猪"},{"number":"30","color":"red","sx":"马"},{"number":"47","color":"blue","sx":"牛"},{"number":"43","color":"green","sx":"蛇"},{"number":"16","color":"green","sx":"猴"}]
     */

    private String currnum;
    private String open_date;
    private List<HaoBean> hao;
    private String number;
    private long nowtime;
    private long next_open_date;
    private DownLoadUrl url;

    public class DownLoadUrl {
        private String az;

        public String getAz() {
            return az;
        }

        public void setAz(String az) {
            this.az = az;
        }
    }

    public void setUrl(DownLoadUrl url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url.getAz();
    }

    public long getNowtime() {
        return nowtime;
    }

    public void setNowtime(long nowtime) {
        this.nowtime = nowtime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCurrnum() {
        return currnum;
    }

    public void setCurrnum(String currnum) {
        this.currnum = currnum;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public long getNext_open_date() {
        return next_open_date;
    }

    public void setNext_open_date(long next_open_date) {
        this.next_open_date = next_open_date;
    }

    public long getZhibo() {
        return next_open_date - nowtime;
    }

    public void setZhibo(long zhibo) {
        zhibo = zhibo;
    }

    public List<HaoBean> getHao() {
        return hao;
    }

    public void setHao(List<HaoBean> hao) {
        this.hao = hao;
    }

    public static class HaoBean {
        /**
         * number : 29
         * color : red
         * sx : 羊
         */

        private String number;
        private String color;
        private String sx;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSx() {
            return sx;
        }

        public void setSx(String sx) {
            this.sx = sx;
        }
    }
}
