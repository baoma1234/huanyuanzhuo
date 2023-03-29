package caixin.android.com.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class FriendEntity implements MultiItemEntity, Serializable {

    @Override
    public String toString() {
        return "FriendEntity{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", is_gl=" + is_gl +
                ", lastacttime='" + lastacttime + '\'' +
                ", nikeName='" + nikeName + '\'' +
                ", status=" + status +
                ", tzstatus=" + tzstatus +
                ", layout_type=" + layout_type +
                ", letter='" + letter + '\'' +
                ", tag='" + tag + '\'' +
                ", isSelected=" + isSelected +
                ", isJoined=" + isJoined +
                ", mobile='" + mobile + '\'' +
                ", session_id='" + session_id + '\'' +
                ", remind=" + remind +
                ", totype=" + totype +
                ", isBlack=" + isBlack +
                ", signature='" + signature + '\'' +
                '}';
    }

    /**
     * id : 34
     * img : /uploads/avatars/20191230/5d2c61e3b2c4b7917f444f83de2cf05f.jpg
     * is_gl : 0
     * lastacttime : 1566134512
     * nikename : 李三
     * status : 1
     * tzstatus : 1
     */
    private static final long serialVersionUID = 1L;
    @Id
    private long id;
    private String img;
    private int is_gl;
    private String lastacttime;
    private String nikeName;
    private int status;
    private int tzstatus;
    private int layout_type;
    private String letter;
    private String tag;
    private boolean isSelected;
    private boolean isJoined = false;
    private String mobile;
    /**
     * session_id : 7f0000010fa20000001e
     * remind : 1
     * totype : 1
     * isBlack : 1
     */

    private String session_id;
    private int remind;
    private int totype;
    private int isBlack; //2已拉黑 1未拉黑
    /**
     * id : 9
     * signature : 哈哈，哈哈.......
     只见一声长笑，从此不闻声迹！
     */

    private String signature;

    private int is_gf;

    @Generated(hash = 1401695081)
    public FriendEntity(long id, String img, int is_gl, String lastacttime,
            String nikeName, int status, int tzstatus, int layout_type,
            String letter, String tag, boolean isSelected, boolean isJoined,
            String mobile, String session_id, int remind, int totype, int isBlack,
            String signature, int is_gf) {
        this.id = id;
        this.img = img;
        this.is_gl = is_gl;
        this.lastacttime = lastacttime;
        this.nikeName = nikeName;
        this.status = status;
        this.tzstatus = tzstatus;
        this.layout_type = layout_type;
        this.letter = letter;
        this.tag = tag;
        this.isSelected = isSelected;
        this.isJoined = isJoined;
        this.mobile = mobile;
        this.session_id = session_id;
        this.remind = remind;
        this.totype = totype;
        this.isBlack = isBlack;
        this.signature = signature;
        this.is_gf = is_gf;
    }

    @Generated(hash = 834006476)
    public FriendEntity() {
    }

    public int getIs_gf() {
        return is_gf;
    }

    public void setIs_gf(int is_gf) {
        this.is_gf = is_gf;
    }

    public boolean isJoined() {
        return isJoined;
    }

    public void setJoined(boolean joined) {
        isJoined = joined;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getLastacttime() {
        return lastacttime;
    }

    public void setLastacttime(String lastacttime) {
        this.lastacttime = lastacttime;
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

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getLayout_type() {
        return layout_type;
    }

    public void setLayout_type(int layout_type) {
        this.layout_type = layout_type;
    }

    @Override
    public int getItemType() {
        return layout_type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsSeleted() {
        return this.isSelected;
    }

    public void setIsSeleted(boolean isSeleted) {
        this.isSelected = isSeleted;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getIsJoined() {
        return this.isJoined;
    }

    public void setIsJoined(boolean isJoined) {
        this.isJoined = isJoined;
    }

    public String getNikeName() {
        return this.nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getTotype() {
        return totype;
    }

    public void setTotype(int totype) {
        this.totype = totype;
    }

    public int getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(int isBlack) {
        this.isBlack = isBlack;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
