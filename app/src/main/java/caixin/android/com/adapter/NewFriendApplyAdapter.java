package caixin.android.com.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import caixin.android.com.entity.NewFriendApplyEntity;
import caixin.android.com.utils.ImgLoader;

public class NewFriendApplyAdapter extends BaseQuickAdapter<NewFriendApplyEntity, BaseViewHolder> {

    public NewFriendApplyAdapter() {
        super(R.layout.item_new_friend_apply);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NewFriendApplyEntity item) {
        helper.addOnClickListener(R.id.state);
        ImageView avatarImage = helper.getView(R.id.avatar);
        ImgLoader.GlideLoad(avatarImage, item.getImg(), R.mipmap.default_avatar);
        helper.setText(R.id.name, item.getNikename());
        TextView stateTextView = helper.getView(R.id.state);
        switch (item.getStatus()) {
            case 0:
                stateTextView.setText(mContext.getResources().getString(R.string.accept));
                stateTextView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_text));
                stateTextView.setTextColor(mContext.getResources().getColor(R.color.white));
                stateTextView.setClickable(true);
                break;
            case 1:
                stateTextView.setText(mContext.getResources().getString(R.string.already_accept));
                stateTextView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_tv_translate_stroke_bg));
                stateTextView.setTextColor(mContext.getResources().getColor(R.color.gray));
                stateTextView.setClickable(false);
                break;
        }

    }
}
