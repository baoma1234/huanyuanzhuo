package caixin.android.com.entity;

import java.io.Serializable;

/**
 * Created by jogger on 2019/6/19
 * 描述：
 */
public class LiuHeInfoIssue implements Serializable {

    /**
     * id : 1
     * year : 2019
     * qs : 060期
     * types : 1
     */

    private int id;
    private String year;
    private String qishu;
    private int types;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getQishu() {
        return qishu;
    }

    public void setQishu(String qishu) {
        this.qishu = qishu;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
    }
}
