package caixin.android.com.entity;

import java.util.List;

/**
 * ===================================
 * Author: Eric
 * Date: 2020/10/30 18:16
 * Description:
 * ===================================
 */
public class EditEmojiRequest {

    private String token;
    private List<Integer> src;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Integer> getId() {
        return src;
    }

    public void setId(List<Integer> id) {
        this.src = id;
    }

    @Override
    public String toString() {
        return "EditEmojiRequest{" +
                "token='" + token + '\'' +
                ", id=" + src.toString() +
                '}';
    }
}
