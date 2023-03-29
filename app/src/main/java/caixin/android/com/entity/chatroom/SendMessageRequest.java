package caixin.android.com.entity.chatroom;

import java.util.List;

public class SendMessageRequest {

    /**
     * act : message
     * data : [{"contents":"回家基本","touids":"31","uid":31}]
     */

    private String act;
    private List<DataBean> data;

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * contents : 回家基本
         * touids : 31
         * uid : 31
         */

        private String contents;
        private String touids;
        private int uid;

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
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
    }
}
