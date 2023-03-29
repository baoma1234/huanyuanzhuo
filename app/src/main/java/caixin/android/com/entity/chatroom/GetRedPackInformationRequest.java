package caixin.android.com.entity.chatroom;

import java.util.List;

public class GetRedPackInformationRequest {

    /**
     * act : getRedbag
     * data : [{"rid":141}]
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
         * rid : 141
         */

        private int rid;

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }
    }
}
