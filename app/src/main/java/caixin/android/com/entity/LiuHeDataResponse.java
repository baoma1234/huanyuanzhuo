package caixin.android.com.entity;

import java.util.List;

/**
 * Created by jogger on 2019/6/26
 * 描述：
 */
public class LiuHeDataResponse {
    private String typename;//图库名称
    private List<LiuHeInfoIssue> qscount;

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public List<LiuHeInfoIssue> getQscount() {
        return qscount;
    }

    public void setQscount(List<LiuHeInfoIssue> qscount) {
        this.qscount = qscount;
    }
}
