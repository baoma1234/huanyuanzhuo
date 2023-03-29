package caixin.android.com.entity;

public class GroupAdEntity {

    /**
     * group_id : 1
     * text : 北京赛车测试3
     * add_time : 1585741759
     * url : www.beijingsaiche.com
     * title : 北京赛车
     */

    private int group_id;
    private String text;
    private int add_time;
    private String url;
    private String title;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
