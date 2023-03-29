package caixin.android.com.entity;

import java.util.List;

/**
 * ===================================
 * Author: Eric
 * Date: 2021/1/19 18:01
 * Description:
 * ===================================
 */
public class PicChannel {

    /**
     * data : ["454155.top","454155.top","7166q.cc"]
     * explain : 默认线路是最优线路,如果您的图片不显示或者超时可以选择切换线路,有问题与管理员联系;
     */
    private String explain;
    private List<String> data;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
