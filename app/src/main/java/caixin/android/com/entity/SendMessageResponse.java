package caixin.android.com.entity;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import caixin.android.com.utils.MMKVUtil;

@Entity
public class SendMessageResponse implements MultiItemEntity {

    public static final int TYPE_TEXT = 1;    //1 表示为普通消息 2 表示为红包
    public static final int TYPE_IMAGE = 3;
    public static final int TYPE_RED_PACK = 2;
    public static final int TYPE_VIDEO = 4;
    public static final int TYPE_FILE = 5;

    public static final int PID_TYPE_MESSAGE = 1;
    public static final int PID_TYPE_REDPACK = 2;
    public static final int PID_TYPE_PICTURE = 3;
    public static final int TOTYPE_FRIEND = 1;
    public static final int TOTYPE_GROUP = 2;


    @Id(autoincrement = true)
    private Long id;
    @Convert(columnType = String.class, converter = ImageList_Converter.class)
    private ImgBean img;
    private int sendId;
    private int manager;
    private String headImg;
    private int getId;
    private int groupId;
    private String groupImage;
    private int pbgid;
    private int assignType;
    private int pid;
    private int uid;
    private int totype;
    private String groupName;
    private String contents;
    private String addtime;
    private String togroups;
    private int isread;
    private String nikeName;
    private String touids;
    private String updatetime;
    private String assignTo;
    private boolean red_status;
    private String tag;
    private int messageId;
    private int is_zl;//1表示true，0表示false
    private int unread;
    /**
     * sort : 0    0代表不置顶，否则代表置顶
     */
    private int sort;
    private int replay_pid;
    private int replystatus;
    private String reply;
    @Convert(columnType = String.class, converter = ReplayDataList_Converter.class)
    private ReplayDataBean reply_data;
    private int is_gl;
    private int is_gf;


    @Generated(hash = 1295295113)
    public SendMessageResponse(Long id, ImgBean img, int sendId, int manager, String headImg, int getId,
            int groupId, String groupImage, int pbgid, int assignType, int pid, int uid, int totype,
            String groupName, String contents, String addtime, String togroups, int isread,
            String nikeName, String touids, String updatetime, String assignTo, boolean red_status,
            String tag, int messageId, int is_zl, int unread, int sort, int replay_pid, int replystatus,
            String reply, ReplayDataBean reply_data, int is_gl, int is_gf) {
        this.id = id;
        this.img = img;
        this.sendId = sendId;
        this.manager = manager;
        this.headImg = headImg;
        this.getId = getId;
        this.groupId = groupId;
        this.groupImage = groupImage;
        this.pbgid = pbgid;
        this.assignType = assignType;
        this.pid = pid;
        this.uid = uid;
        this.totype = totype;
        this.groupName = groupName;
        this.contents = contents;
        this.addtime = addtime;
        this.togroups = togroups;
        this.isread = isread;
        this.nikeName = nikeName;
        this.touids = touids;
        this.updatetime = updatetime;
        this.assignTo = assignTo;
        this.red_status = red_status;
        this.tag = tag;
        this.messageId = messageId;
        this.is_zl = is_zl;
        this.unread = unread;
        this.sort = sort;
        this.replay_pid = replay_pid;
        this.replystatus = replystatus;
        this.reply = reply;
        this.reply_data = reply_data;
        this.is_gl = is_gl;
        this.is_gf = is_gf;
    }

    @Generated(hash = 1719041837)
    public SendMessageResponse() {
    }


    public int getIs_gf() {
        return is_gf;
    }

    public void setIs_gf(int is_gf) {
        this.is_gf = is_gf;
    }

    public int getIs_gl() {
        return is_gl;
    }

    public void setIs_gl(int is_gl) {
        this.is_gl = is_gl;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public int getMessageId() {
        if (messageId == 0)
            messageId = id.intValue();
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(getUpdatetime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public int getIs_zl() {
        return is_zl;
    }

    public void setIs_zl(int is_zl) {
        this.is_zl = is_zl;
    }

    public int getType() {
        return getPid();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean getRed_status() {
        return red_status;
    }

    public void setRed_status(boolean red_status) {
        this.red_status = red_status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ImgBean getImg() {
        if (img == null) {
            return new ImgBean();
        }
        return img;
    }

    public void setImg(ImgBean img) {
        this.img = img;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getGroupId() {
        return groupId;
    }

    public boolean isFromSelf() {
        if (TextUtils.isEmpty(MMKVUtil.getToken()))
            return false;
        if (MMKVUtil.getUserInfo().getId() == Integer.valueOf(getSendId()))
            return true;
        return false;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getPbgid() {
        return pbgid;
    }

    public void setPbgid(int pbgid) {
        this.pbgid = pbgid;
    }

    public int getAssignType() {
        return assignType;
    }

    public void setAssignType(int assignType) {
        this.assignType = assignType;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getTotype() {
        return totype;
    }

    public void setTotype(int totype) {
        this.totype = totype;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getTogroups() {
        return togroups;
    }

    public void setTogroups(String togroups) {
        this.togroups = togroups;
    }

    public int getIsread() {
        return isread;
    }

    public int getGetId() {
        return getId;
    }

    public void setGetId(int getId) {
        this.getId = getId;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getTouids() {
        return touids;
    }

    public void setTouids(String touids) {
        this.touids = touids;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }


    public String getGroupImage() {
        return this.groupImage;
    }


    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    @Override
    public int getItemType() {
        switch (getType()) {
            case SendMessageResponse.TYPE_TEXT:
                return 1;
            case SendMessageResponse.TYPE_IMAGE:
                return 2;
            case SendMessageResponse.TYPE_RED_PACK:
                return 3;
        }
        return 1;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getReplay_pid() {
        return replay_pid;
    }

    public void setReplay_pid(int replay_pid) {
        this.replay_pid = replay_pid;
    }

    public int getReplystatus() {
        return replystatus;
    }

    public void setReplystatus(int replystatus) {
        this.replystatus = replystatus;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public ReplayDataBean getReply_data() {
        return reply_data;
    }

    public void setReply_data(ReplayDataBean reply_data) {
        this.reply_data = reply_data;
    }

    public static class ImgBean {

        /**
         * imgurl : https://35666t.com//img/caixin_android/2020-04-02/158582184860caixin_android.png
         * width : null
         * height : null
         * thumb : null
         */

        private String imgurl;
        private int width;
        private int height;
        private String thumb;
        private String thumbImgUrl;

        public String getThumbImgUrl() {
            return thumbImgUrl;
        }

        public void setThumbImgUrl(String thumbImgUrl) {
            this.thumbImgUrl = thumbImgUrl;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
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
    }

    public static class ReplayDataBean {
        private int id;
        private String contents;
        private int pid;
        private String imgurl;
        private String thumbImgUrl;
        private int width;
        private int height;
        private String addtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getThumbImgUrl() {
            return thumbImgUrl;
        }

        public void setThumbImgUrl(String thumbImgUrl) {
            this.thumbImgUrl = thumbImgUrl;
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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }


    public static class ImageList_Converter implements PropertyConverter<ImgBean, String> {

        @Override
        public ImgBean convertToEntityProperty(String databaseValue) {
            return new Gson().fromJson(databaseValue, ImgBean.class);
        }

        @Override
        public String convertToDatabaseValue(ImgBean entityProperty) {
            return new Gson().toJson(entityProperty);
        }
    }


    public static class ReplayDataList_Converter implements PropertyConverter<ReplayDataBean, String> {

        @Override
        public ReplayDataBean convertToEntityProperty(String databaseValue) {
            return new Gson().fromJson(databaseValue, ReplayDataBean.class);
        }

        @Override
        public String convertToDatabaseValue(ReplayDataBean entityProperty) {
            return new Gson().toJson(entityProperty);
        }
    }

    @Override
    public String toString() {
        return "SendMessageResponse{" +
                "id=" + id +
                ", img=" + img +
                ", sendId=" + sendId +
                ", manager=" + manager +
                ", headImg='" + headImg + '\'' +
                ", getId=" + getId +
                ", groupId=" + groupId +
                ", groupImage='" + groupImage + '\'' +
                ", pbgid=" + pbgid +
                ", assignType=" + assignType +
                ", pid=" + pid +
                ", uid=" + uid +
                ", totype=" + totype +
                ", groupName='" + groupName + '\'' +
                ", contents='" + contents + '\'' +
                ", addtime='" + addtime + '\'' +
                ", togroups='" + togroups + '\'' +
                ", isread=" + isread +
                ", nikeName='" + nikeName + '\'' +
                ", touids='" + touids + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", assignTo='" + assignTo + '\'' +
                ", red_status=" + red_status +
                ", tag='" + tag + '\'' +
                ", messageId=" + messageId +
                ", is_zl=" + is_zl +
                ", unread=" + unread +
                ", sort=" + sort +
                ", replay_pid=" + replay_pid +
                ", replystatus=" + replystatus +
                ", reply='" + reply + '\'' +
                ", reply_data='" + reply_data + '\'' +
                '}';
    }
}
