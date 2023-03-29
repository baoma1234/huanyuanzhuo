package caixin.android.com.entity;

import java.util.List;

/**
 * ===================================
 * Author: Eric
 * Date: 2020/10/30 18:16
 * Description:
 * ===================================
 */
public class DeleteEmojiRequest {

    private String token;
    private List<Integer> id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }
}
