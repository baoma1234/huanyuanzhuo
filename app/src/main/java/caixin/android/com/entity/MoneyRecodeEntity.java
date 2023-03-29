package caixin.android.com.entity;

public class MoneyRecodeEntity   {


    /**
     * id : 66
     * uid : 3
     * reason : 抢红包
     * money : +1.22
     * addtime : 2020-05-27 18:15:07
     * message : null
     */

    private int id;
    private int uid;
    private String reason;
    private String money;
    private String addtime;
    private Object message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
