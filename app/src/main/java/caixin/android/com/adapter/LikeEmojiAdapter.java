package caixin.android.com.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import caixin.android.com.entity.LikeEmojiEntity;
import caixin.android.com.utils.ImgLoader;

public class LikeEmojiAdapter extends BaseQuickAdapter<LikeEmojiEntity, BaseViewHolder> {

    public LikeEmojiAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LikeEmojiEntity item) {
        if (helper.getAdapterPosition() == 0) {
            ImgLoader.ChatRoomRoundCorners(item.getLocalSrc(), helper.getView(R.id.item_img), R.mipmap.web_default);
        } else {
            ImgLoader.ChatRoomRoundCorners(item.getSrc(), helper.getView(R.id.item_img), R.mipmap.web_default);
        }
    }
}
