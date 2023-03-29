package caixin.android.com.entity;

import java.util.List;

/**
 * ===================================
 * Author: Eric
 * Date: 2020/11/10 21:14
 * Description:
 * ===================================
 */
public class EditMangerRequest {
    private String token;
    private int gid;
    private List<Integer> uids;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public List<Integer> getIds() {
        return uids;
    }

    public void setIds(List<Integer> ids) {
        this.uids = ids;
    }
}
