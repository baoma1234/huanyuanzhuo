package caixin.android.com.entity.chatroom;

import java.util.ArrayList;
import java.util.List;

public class GetAdsRequest {

    /**
     * act : session
     * data : [{"id":3}]
     */

    private String act;
    private List<DataBean> data = new ArrayList<>();

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
         * id : 3
         */

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
