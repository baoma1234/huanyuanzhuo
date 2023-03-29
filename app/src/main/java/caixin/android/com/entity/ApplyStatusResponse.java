package caixin.android.com.entity;

/**
 * ===================================
 * Author: Eric
 * Date: 2020/11/3 17:06
 * Description: 用来判断是否有新的好友请求
 * ===================================
 */
public class ApplyStatusResponse {

    /**
     * status : 1  如果为1就表示有，否则就表示没有
     */

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
