package caixin.android.com.entity;

import caixin.android.com.entity.base.BaseWebSocketItemRequest;

public class SendVideoRequest extends BaseWebSocketItemRequest {
    int uid;
    int touids;
    int togroups;
    String src;
    String thumb;
    int width;
    int height;
    int is_zl;
    int pid;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getIs_zl() {
        return is_zl;
    }

    public void setIs_zl(int is_zl) {
        this.is_zl = is_zl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTouids() {
        return touids;
    }

    public void setTouids(int touids) {
        this.touids = touids;
    }

    public int getTogroups() {
        return togroups;
    }

    public void setTogroups(int togroups) {
        this.togroups = togroups;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
