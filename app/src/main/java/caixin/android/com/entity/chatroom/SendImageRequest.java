package caixin.android.com.entity.chatroom;

import java.util.List;

public class SendImageRequest {


    /**
     * act : image
     * data : [{"uid":31,"touids":"23,10","src":"data:image/png;base64,iVBORw0KGgoAAAA......"}]
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
         * uid : 31
         * touids : 23,10
         * src : data:image/png;base64,iVBORw0KGgoAAAA......
         */

        private int uid;
        private String touids;
        private String src;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getTouids() {
            return touids;
        }

        public void setTouids(String touids) {
            this.touids = touids;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
