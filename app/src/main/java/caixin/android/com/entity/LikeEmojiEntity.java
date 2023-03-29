package caixin.android.com.entity;

import java.io.Serializable;

public class LikeEmojiEntity implements Serializable {

    @Override
    public String toString() {
        return "LikeEmojiEntity{" +
                "id=" + id +
                ", src='" + src + '\'' +
                ", localSrc=" + localSrc +
                '}';
    }

    /**
     * id : 1
     * src : http://35666t.com/biaoqing/91bt.com.cn%20%20(1)(0001).gif
     */

    private int id;
    private String src;
    private int localSrc;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getLocalSrc() {
        return localSrc;
    }

    public void setLocalSrc(int localSrc) {
        this.localSrc = localSrc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
