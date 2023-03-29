package caixin.android.com.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class MemberEntity implements MultiItemEntity, Serializable {

    public static final int MANAGER_BOSS = 1;
    public static final int MANAGER_MANAGER = 2;
    public static final int MANAGER_USER = 3;

    @Override
    public String toString() {
        return "MemberEntity{" +
                "id=" + id +
                ", nikename='" + nikeName + '\'' +
                ", img='" + img + '\'' +
                ", is_gl=" + is_gl +
                ", status=" + status +
                ", tzstatus=" + tzstatus +
                ", lastacttime='" + lastacttime + '\'' +
                ", session_id='" + session_id + '\'' +
                ", tag='" + tag + '\'' +
                ", manager=" + manager +
                '}';
    }

    /**
     * id : 38
     * nikename : 花花
     * img : http://35666j.com/uploads/avatars/20190807/c3692c443d9cb91a5815b05534a1aa35.jpg
     * is_gl : 0
     * status : 1
     * tzstatus : 1
     * lastacttime : 1585288114
     * session_id :
     * tag :
     * manager : 1
     */

    private int itemType;  //0普通  ，1添加，2减少
    private int id;
    private String nikeName;
    private String img;
    private int is_gl;
    private int status;
    private int tzstatus;
    private String lastacttime;
    private String session_id;
    private String tag;
    private int manager;  //群组管理员级别 1群主 2管理员 3普通用户 必返回
    private boolean isSelected;
    private String letter;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNikename() {
        return nikeName;
    }

    public void setNikename(String nikename) {
        this.nikeName = nikename;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getIs_gl() {
        return is_gl;
    }

    public void setIs_gl(int is_gl) {
        this.is_gl = is_gl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTzstatus() {
        return tzstatus;
    }

    public void setTzstatus(int tzstatus) {
        this.tzstatus = tzstatus;
    }

    public String getLastacttime() {
        return lastacttime;
    }

    public void setLastacttime(String lastacttime) {
        this.lastacttime = lastacttime;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
