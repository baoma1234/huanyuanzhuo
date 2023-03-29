package caixin.android.com.entity;

public class MyContacts {

    private String name;
    private String note;
    private String mobile;

    @Override
    public String toString() {
        return "MyContacts{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }

    public MyContacts() {
    }

    public MyContacts(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
