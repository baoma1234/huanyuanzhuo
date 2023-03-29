package caixin.android.com.entity.chatroom;

import java.util.List;

public class UserRequest {
    /**
     * act : login
     * data : [{"uid":31,"nikename":"711","img":"http://47.75.251.38/uploads/mrtou.png"}]
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

        private String mobile;
        private String password;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
