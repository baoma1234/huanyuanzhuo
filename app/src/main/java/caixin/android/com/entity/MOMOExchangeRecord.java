package caixin.android.com.entity;

import java.util.List;

/**
 * Created by jogger on 2019/7/15
 * 描述：
 */
public class MOMOExchangeRecord {


    /**
     * money : 200
     * records : [{"id":3,"ptid":1,"amount":"100.00","addtime":"2019-06-18 22:02:27","status":0,"ptname":"永诚国际"},{"id":2,"ptid":1,"amount":"100.00","addtime":"2019-06-18 22:02:04","status":0,"ptname":"永诚国际"}]
     */

    private int money;
    private List<RecordsBean> records;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * id : 3
         * ptid : 1
         * amount : 100.00
         * addtime : 2019-06-18 22:02:27
         * status : 0 0审核中 1审核完成 2审核未通过
         * ptname : 永诚国际
         */

        private int id;
        private int ptid;
        private String amount;
        private String addtime;
        private int status;
        private String ptname;
        private String reasons;

        public String getReasons() {
            return reasons;
        }

        public void setReasons(String reasons) {
            this.reasons = reasons;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPtid() {
            return ptid;
        }

        public void setPtid(int ptid) {
            this.ptid = ptid;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPtname() {
            return ptname;
        }

        public void setPtname(String ptname) {
            this.ptname = ptname;
        }
    }
}
