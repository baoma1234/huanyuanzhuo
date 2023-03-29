package caixin.android.com.entity;

import caixin.android.com.entity.base.BaseWebSocketItemRequest;

public class MultipleSendResponse extends BaseWebSocketItemRequest {

    String content;
    String add_time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    /**
     * img : {"imgurl":"","width":"","height":""}
     */



    private SendMessageResponse.ImgBean img;

    public SendMessageResponse.ImgBean getImg() {
        return img;
    }

    public void setImg(SendMessageResponse.ImgBean img) {
        this.img = img;
    }


}
