package caixin.android.com.entity;

public class GetHistoryMessageRequest {
    int uid;
    int touid;
    int togroup;
    int size;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTouid() {
        return touid;
    }

    public void setTouid(int touid) {
        this.touid = touid;
    }

    public int getTogroup() {
        return togroup;
    }

    public void setTogroup(int togroup) {
        this.togroup = togroup;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "GetHistoryMessageRequest{" +
                "uid=" + uid +
                ", touid=" + touid +
                ", togroup=" + togroup +
                ", size=" + size +
                '}';
    }
}
