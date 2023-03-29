package caixin.android.com.entity;

import java.util.List;

public class PlData {
    private List<CommonModel> hot;
    private List<CommonModel> nhot;

    public List<CommonModel> getHot() {
        return hot;
    }

    public void setHot(List<CommonModel> hot) {
        this.hot = hot;
    }

    public List<CommonModel> getNhot() {
        return nhot;
    }

    public void setNhot(List<CommonModel> nhot) {
        this.nhot = nhot;
    }
}
