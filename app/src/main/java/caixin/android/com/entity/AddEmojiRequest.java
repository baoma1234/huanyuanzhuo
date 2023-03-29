package caixin.android.com.entity;

import java.util.List;

/**
 * ===================================
 * Author: Eric
 * Date: 2020/10/29 17:50
 * Description:
 * ===================================
 */
public class AddEmojiRequest {

    private String token;
    private String src;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getImgs() {
        return src;
    }

    public void setImgs(String imgs) {
        this.src = imgs;
    }
}
