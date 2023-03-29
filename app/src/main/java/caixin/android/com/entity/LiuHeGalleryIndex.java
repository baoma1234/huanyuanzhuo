package caixin.android.com.entity;

import java.util.List;

public class LiuHeGalleryIndex {

    private List<LiuHeIndexItem> hot;
    private List<LiuHeIndexItem> list;
    private LiuHeUrl other;

    public LiuHeUrl getOther() {
        return other;
    }

    public void setOther(LiuHeUrl other) {
        this.other = other;
    }

    public class LiuHeUrl{
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public List<LiuHeIndexItem> getHot() {
        return hot;
    }

    public void setHot(List<LiuHeIndexItem> hot) {
        this.hot = hot;
    }

    public List<LiuHeIndexItem> getList() {
        return list;
    }

    public void setList(List<LiuHeIndexItem> list) {
        this.list = list;
    }
}
