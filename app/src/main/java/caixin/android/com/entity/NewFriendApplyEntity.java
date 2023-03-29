package caixin.android.com.entity;

public class NewFriendApplyEntity {


    /**
     * nikename : xw
     * img : http://35666t.com/img/caixin_android/mimage.jpg
     * mobile : 13477777777
     * id : 4
     * status : 1
     */

    private String nikename;
    private String img;
    private String mobile;
    private int id;
    private int status; //0未处理，1已添加，2已拒绝

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
