package caixin.android.com.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class GroupEntity implements Serializable {

    /**
     * id : 2
     * name : 北京赛车房间
     * status : 1
     */
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;
    private String groupImage;

    //1表示已经加入 2表示已申请 3表示未加入
    public static final int STATE_APPLYING = 2;
    public static final int STATE_ADDED = 1;
    public static final int STATE_NOT_ADDED = 3;
    @Generated(hash = 375385947)
    public GroupEntity(Long id, String name, String groupImage) {
        this.id = id;
        this.name = name;
        this.groupImage = groupImage;
    }
    @Generated(hash = 954040478)
    public GroupEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGroupImage() {
        return this.groupImage;
    }
    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

 
}
