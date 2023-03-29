package caixin.android.com.entity;

import caixin.android.com.entity.base.BaseWebSocketItemRequest;

public class ApplyGroupRequest extends BaseWebSocketItemRequest {
    int uid;
    int group_id;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}
