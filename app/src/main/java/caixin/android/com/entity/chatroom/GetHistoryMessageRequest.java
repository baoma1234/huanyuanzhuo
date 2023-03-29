package caixin.android.com.entity.chatroom;

import java.util.ArrayList;
import java.util.List;

public class GetHistoryMessageRequest {

    /**
     * act : getMessage
     * data : [{"size":10}]
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
         * size : 10
         */

        private int size;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
