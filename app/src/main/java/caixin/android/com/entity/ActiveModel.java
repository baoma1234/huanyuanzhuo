package caixin.android.com.entity;

/**
 * Created by jogger on 2019/6/19
 * 描述：
 */
public class ActiveModel {

    /**
     * id : 1
     * name : 天天领红包
     * addtime : 2019-06-12 21:47:46
     * img : http://35666t.com/img/act/red.jpg
     * image : http://35666t.com/img/act/redinfo.jpg
     * action_url : /Activity/lingqu
     * width : 750
     * height : 385
     */

    private int id;
    private String name;
    private String addtime;
    private String img;
    private String image;
    private String action_url;
    private int width;
    private int height;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAction_url() {
        return action_url;
    }

    public void setAction_url(String action_url) {
        this.action_url = action_url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
