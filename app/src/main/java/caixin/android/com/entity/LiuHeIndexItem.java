package caixin.android.com.entity;

public class LiuHeIndexItem {
    /**
     * id : 1
     * typename : 管家婆
     * ishot : 1
     * is_update : 1
     * key : G
     * img : ./img.jpg
     */
    private int id;
    private String typename;
    private int ishot;
    private int is_update;
    private String key;//排序参数 图库类型名称首字母的第一个拼音或者数字
    private String img;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getIshot() {
        return ishot;
    }

    public void setIshot(int ishot) {
        this.ishot = ishot;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "LiuHeIndexItem{" +
                "key='" + key + '\'' +
                '}';
    }
}
