package caixin.android.com.entity;

import java.util.List;

import caixin.android.com.entity.base.BaseWebSocketItemRequest;

public class MultipleSendRequest extends BaseWebSocketItemRequest {

    String content;
    String img;
    private List<Integer> friendIds;
    private List<Integer> groupIds;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Integer> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<Integer> friendIds) {
        this.friendIds = friendIds;
    }

    public List<Integer> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Integer> groupIds) {
        this.groupIds = groupIds;
    }
}
