package caixin.android.com.entity;

/**
 * ===================================
 * Author: Eric
 * Date: 2020/9/24 21:23
 * Description:
 * ===================================
 */
public class HomeMenuItemResponse {
    String title;
    String img;
    int id;
    int source;
    String url;

    public HomeMenuItemResponse() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HomeMenuItemResponse(String title, String img, int id) {
        this.title = title;
        this.img = img;
        this.id = id;
    }


    public HomeMenuItemResponse(String title, String img, int id, int source) {
        this.title = title;
        this.img = img;
        this.id = id;
        this.source = source;
    }

    public HomeMenuItemResponse(String title, String img, int id, int source, String url) {
        this.title = title;
        this.img = img;
        this.id = id;
        this.source = source;
        this.url = url;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
