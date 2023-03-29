package caixin.android.com.entity;

import caixin.android.com.entity.base.BaseWebSocketItemRequest;

public class SendMessageRequest extends BaseWebSocketItemRequest {
    String touids;
    int togroups;
    String contents;
    int assignType;
    String assignTo;
    int reply;

    //0 不是@ 1 @单人多人 2 @所有人
    public static final int TYPE_ASSIGN_ALONE = 1;
    public static final int TYPE_ASSIGN_NONE = 0;
    public static final int TYPE_ASSIGN_ALL = 2;

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getAssignType() {
        return assignType;
    }

    public void setAssignType(int assignType) {
        this.assignType = assignType;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTos) {
        this.assignTo = assignTos;
    }

    public String getTouids() {
        return touids;
    }

    public void setTouids(String touids) {
        this.touids = touids;
    }

    public int getTogroups() {
        return togroups;
    }

    public void setTogroups(int togroups) {
        this.togroups = togroups;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
