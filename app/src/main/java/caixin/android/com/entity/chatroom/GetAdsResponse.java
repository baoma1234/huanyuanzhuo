package caixin.android.com.entity.chatroom;

import java.util.List;

public class GetAdsResponse {
    /**
     * ads : {"content":"<p>弹窗广告<\/p>","url":"null"}
     * rule : {"content":"<p>欢迎回来！六合之家欢迎你！谢谢！<\/p>","url":""}
     * menu : [{"title":"菜单测试1","url":"https://35666app.com/"},{"title":"菜单测试2","url":"http://58.82.219.68:6868"},{"title":"菜单测试3","url":"https://cjtz.app/zmgw.app"}]
     */

    private AdsBean ads;
    private RuleBean rule;
    private List<MenuBean> menu;

    public AdsBean getAds() {
        return ads;
    }

    public void setAds(AdsBean ads) {
        this.ads = ads;
    }

    public RuleBean getRule() {
        return rule;
    }

    public void setRule(RuleBean rule) {
        this.rule = rule;
    }

    public List<MenuBean> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuBean> menu) {
        this.menu = menu;
    }

    public static class AdsBean {
        /**
         * content : <p>弹窗广告</p>
         * url : null
         */

        private String content;
        private String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class RuleBean {
        /**
         * content : <p>欢迎回来！六合之家欢迎你！谢谢！</p>
         * url :
         */

        private String content;
        private String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class MenuBean {
        /**
         * title : 菜单测试1
         * url : https://35666app.com/
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
