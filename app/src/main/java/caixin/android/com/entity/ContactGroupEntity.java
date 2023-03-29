package caixin.android.com.entity;

public class ContactGroupEntity {

    private String groupName;
    private int num;

    public ContactGroupEntity() {
    }

    public ContactGroupEntity(String groupName, int num) {
        this.groupName = groupName;
        this.num = num;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
