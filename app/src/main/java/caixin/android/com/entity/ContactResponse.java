package caixin.android.com.entity;

import java.util.List;

public class ContactResponse {
    private List<FriendEntity> friends;
    private List<GroupEntity> groups;

    public List<FriendEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendEntity> friends) {
        this.friends = friends;
    }

    public List<GroupEntity> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupEntity> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "ContactResponse{" +
                "friends=" + friends +
                ", groups=" + groups +
                '}';
    }
}
