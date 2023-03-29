package caixin.android.com.entity;

import java.util.List;

public class KJResultModel {
    /**
     * id : 109000041
     * nextid : 109000042
     * nextdate : 1588077000
     * new_data : [{"num":"39","sx":"狗","bs":"green"},{"num":"07","sx":"马","bs":"red"},{"num":"14","sx":"猪","bs":"blue"},{"num":"17","sx":"猴","bs":"green"},{"num":"15","sx":"狗","bs":"blue"},{"num":"10","sx":"兔","bs":"blue"},{"num":"45","sx":"龙","bs":"red"}]
     */
    private int id;
    private int nextid;
    private List<NewDataBean> new_data;
    private long nextdate;

    public long getNextdate() {
        return nextdate;
    }

    public void setNextdate(long nextdate) {
        this.nextdate = nextdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNextid() {
        return nextid;
    }

    public void setNextid(int nextid) {
        this.nextid = nextid;
    }

    public List<NewDataBean> getNew_data() {
        return new_data;
    }

    public void setNew_data(List<NewDataBean> new_data) {
        this.new_data = new_data;
    }

    public void setZhibo(long zhibo) {
        zhibo = zhibo;
    }

    public static class NewDataBean {
        /**
         * num : 29
         * sx : 羊
         * bs : red
         */

        private int num;
        private String sx;
        private String bs;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getSx() {
            return sx;
        }

        public void setSx(String sx) {
            this.sx = sx;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }
    }
}
