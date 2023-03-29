package caixin.android.com.entity;

import java.io.Serializable;
import java.util.List;

public class SSCModel implements Serializable {

    /**
     * type : bjpks
     * qishu : 735770
     * code : [{"number":"03","color":"#4d4d4d"},{"number":"09","color":"#760000"},{"number":"08","color":"#ff0000"},{"number":"01","color":"#ffff00"},{"number":"02","color":"#0089ff"},{"number":"06","color":"#5200ff"},{"number":"07","color":"#e3e3e3"},{"number":"10","color":"#28c200"},{"number":"05","color":"#81ffff"},{"number":"04","color":"#ff7300"}]
     * typename : 北京赛车PK拾
     * opentime : 2019-07-05 23:30:00
     */

    private String type;
    private int qishu;
    private String typename;
    private String opentime;
    private List<CodeBean> code;
    private String date;
    private String time;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQishu() {
        return qishu;
    }

    public void setQishu(int qishu) {
        this.qishu = qishu;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public List<CodeBean> getCode() {
        return code;
    }

    public void setCode(List<CodeBean> code) {
        this.code = code;
    }

    public static class CodeBean implements Serializable{
        /**
         * number : 03
         * color : #4d4d4d
         */

        private String number;
        private String color;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
