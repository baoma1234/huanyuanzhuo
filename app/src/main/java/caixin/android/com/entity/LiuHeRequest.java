package caixin.android.com.entity;

public class LiuHeRequest {
    private int typeid;//图库类型ID
    private int id;//图库文章ID(如不存在，取最新图库文章)
    private int limit;
    private String year;

    public LiuHeRequest() {
    }

    public LiuHeRequest(int typeid) {
        this.typeid = typeid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
