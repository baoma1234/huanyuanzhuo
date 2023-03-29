package caixin.android.com.entity;

import java.util.ArrayList;
import java.util.List;

public class CollectRequest {

    private String text;
    private List<String> imgs = new ArrayList<>();
    private String token;
    private int toid;

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

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
