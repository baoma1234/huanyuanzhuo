package caixin.android.com.entity;

import com.stx.xhb.xbanner.entity.BaseBannerInfo;
import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class ActivityEntity extends SimpleBannerInfo {


    /**
     * img : http://ksxlfcc.com//uploads/avatars/20200704/3f8b2dffaf68f50e0727eb75510b7da7.png
     * href : http://xy.com/admin/Home/index
     */

    private String img;
    private String href;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public Object getXBannerUrl() {
        return null;
    }

    @Override
    public String getXBannerTitle() {
        return null;
    }
}
