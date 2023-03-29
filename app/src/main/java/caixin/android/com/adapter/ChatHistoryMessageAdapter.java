package caixin.android.com.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.utils.DateUtil;
import caixin.android.com.utils.DimensUtils;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.TextRender;
import caixin.android.com.widget.BubbleLayout;

public class ChatHistoryMessageAdapter extends BaseMultiItemQuickAdapter<SendMessageResponse, BaseViewHolder> {

    public ChatHistoryMessageAdapter() {
        super(null);
        addItemType(1, R.layout.item_chat_text_left);
        addItemType(2, R.layout.item_chat_image_left);
        addItemType(3, R.layout.item_chat_red_pack_left);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SendMessageResponse item) {
        ImageView mAvatar = helper.getView(R.id.avatar);
        ImgLoader.GlideLoad(mAvatar, item.getHeadImg(), R.mipmap.img_user_head);
        helper.setText(R.id.text_nickName, item.getNikeName());
        helper.setText(R.id.time, DateUtil.formatDate(item.getAddtime()));
        switch (helper.getItemViewType()) {
            case 1:
                String text = item.getContents();
                text = Html.fromHtml(text).toString();
                TextView textView = helper.getView(R.id.text);
                textView.setText(TextRender.renderChatMessage(text));
                break;
            case 2:
                ImageView mImg = helper.getView(R.id.img);
                if (item.getImg().getHeight() > 120 || item.getImg().getWidth() > 120) {
                    ViewGroup.LayoutParams imageLayoutParams = mImg.getLayoutParams();
                    int width = (DimensUtils.getScreenWidth() - DimensUtils.dp2Px(mContext, 10)) / 2;
                    double height;
                    if (item.getImg().getWidth() == 0 || item.getImg().getHeight() == 0) {
                        height = 500;
                    } else {
                        height = (double) width / (double) item.getImg().getWidth() * item.getImg().getHeight();
                    }
                    imageLayoutParams.width = width;
                    imageLayoutParams.height = (int) height;
                    mImg.setLayoutParams(imageLayoutParams);
                    helper.setVisible(R.id.iv_is_ziliao, item.getIs_zl() == 1);
                }
                ImgLoader.ChatRoomRoundCorners(item.getImg().getImgurl(), mImg, R.mipmap.web_default);
                break;
            case 3:
                BubbleLayout bubbleLayout = helper.getView(R.id.bl_red_pack);
                ImageView imageView = helper.getView(R.id.iv_red_pack);
                if (item.getRed_status()) {
                    bubbleLayout.setmBubbleBgColor(R.color.pk_red_chat);
                    imageView.setImageResource(R.mipmap.packet_item1);
                } else {
                    bubbleLayout.setmBubbleBgColor(R.color.pk_red_chat_gray);
                    imageView.setImageResource(R.mipmap.packet_item2);
                }
                break;
        }
    }
}
