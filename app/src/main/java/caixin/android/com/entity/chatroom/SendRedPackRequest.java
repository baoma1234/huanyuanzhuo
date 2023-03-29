package caixin.android.com.entity.chatroom;

import java.util.List;

public class SendRedPackRequest {
    /**
     * act : redbag
     * data : [{"uid":31,"contents":"恭喜发财，大吉大利","size":10,"money":5}]
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
         * contents : 恭喜发财，大吉大利
         * size : 10
         * money : 5
         */

        private int uid;
        private String contents;
        private int size;
        private int money;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }
    }
}
