package caixin.android.com.entity;

/**
 * Created by jogger on 2019/6/13
 * 描述：
 */
public class ArticleRequest {

    private String artid;
    private String typeid;
    private String page;
    private String type;
    private String limit;
    private String share_type;

    public String getArtid() {
        return artid;
    }

    public void setArtid(String artid) {
        this.artid = artid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getShare_type() {
        return share_type;
    }

    public void setShare_type(String share_type) {
        this.share_type = share_type;
    }

    @Override
    public String toString() {
        return "ArticleRequest{" +
                "artid='" + artid + '\'' +
                ", typeid='" + typeid + '\'' +
                ", page='" + page + '\'' +
                ", type='" + type + '\'' +
                ", limit='" + limit + '\'' +
                ", share_type='" + share_type + '\'' +
                '}';
    }
}
