package caixin.android.com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jogger on 2019/6/19
 * 描述：
 */
public class PopModel implements Serializable {

    /**
     * errorcode : 200
     * msg :
     * data : {"tcggs":[{"img":"http://47.75.251.38/data/ac.png ","content":"香港彩 幸运注单双倍派送 What？幸运注单双倍派送？ 没错，香港彩就是这么任性 六合幸运注单，中奖金额双倍派送 天道无情，香港彩有情！ 赶快来投注，也许您就是下一个幸运儿！"},{"img":"http://47.75.251.38/data/ac.png ","content":"香港彩六合钜惠  六合彩，平特三连涨、特码49.5倍! 126期 平特2.15倍 127期 平特2.21倍 128期 平特2.30倍 下注六合彩、首选香港彩！"}]}
     */

    private List<TcggsBean> tcggs;

    public List<TcggsBean> getTcggs() {
        return tcggs;
    }

    public void setTcggs(List<TcggsBean> tcggs) {
        this.tcggs = tcggs;
    }

    public static class TcggsBean {
        /**
         * img : http://47.75.251.38/data/ac.png
         * content : 香港彩 幸运注单双倍派送 What？幸运注单双倍派送？ 没错，香港彩就是这么任性 六合幸运注单，中奖金额双倍派送 天道无情，香港彩有情！ 赶快来投注，也许您就是下一个幸运儿！
         */

        private String img;
        private String content;
        private String url;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

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
}
