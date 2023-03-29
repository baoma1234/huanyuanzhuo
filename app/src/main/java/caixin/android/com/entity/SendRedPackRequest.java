package caixin.android.com.entity;

import caixin.android.com.entity.base.BaseWebSocketItemRequest;

public class SendRedPackRequest extends BaseWebSocketItemRequest {
    int uid;    //发送者用户id 必填写
    int touids;    //发送给用户id
    int suid;    //发送给专属用户的id
    int togroups;    //发送给群组id
    String contents;    //红包内容必填写
    int size; //红包数量必填写
    double money;    //红包金额必填写
    String  pay_pwd;    //红包金额必填写

    public int getSuid() {
        return suid;
    }

    public void setSuid(int suid) {
        this.suid = suid;
    }

    public String getPay_pwd() {
        return pay_pwd;
    }

    public void setPay_pwd(String pay_pwd) {
        this.pay_pwd = pay_pwd;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTouids() {
        return touids;
    }

    public void setTouids(int touids) {
        this.touids = touids;
    }

    public int getTogroups() {
        return togroups;
    }

    public void setTogroups(int togroups) {
        this.togroups = togroups;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
