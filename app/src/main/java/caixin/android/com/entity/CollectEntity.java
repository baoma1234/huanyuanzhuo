package caixin.android.com.entity;

import java.util.List;

public class CollectEntity {


    /**
     * id : 9
     * uid : 4
     * toid : 5
     * imgs : []
     * text : 1111111111111111111111
     * add_time : 2020-06-30 13:31:37
     * nikename : 天下客服AAAA
     */

    private int id;
    private int uid;
    private int toid;
    private String text;
    private String add_time;
    private String nikename;
    private List<String> imgs;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getToid() {
        return toid;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
}
