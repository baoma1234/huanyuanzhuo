package caixin.android.com.entity;

import java.util.List;

public class HomeImageAdModel {

    private List<TanchBean> tanch;

    public List<TanchBean> getTanch() {
        return tanch;
    }

    public void setTanch(List<TanchBean> tanch) {
        this.tanch = tanch;
    }

    public static class TanchBean {
        /**
         * id : 5
         * img : https://35666t.com/img/lhzj_admin/2020-07-03/1593782264650lhzj_admin.png
         * url : https://2680017.com
         * addtime : 1593782247
         * width : 750.00
         * height : 1000.00
         */

        private int id;
        private String img;
        private String url;
        private int addtime;
        private String width;
        private String height;

        public int getId() {
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }
}
