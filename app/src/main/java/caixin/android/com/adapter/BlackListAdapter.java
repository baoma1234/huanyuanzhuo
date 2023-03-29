package caixin.android.com.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.utils.ImgLoader;

public class BlackListAdapter extends BaseQuickAdapter<BlackFriendEntity, BaseViewHolder> {

    public BlackListAdapter() {
        super(R.layout.item_black_friend);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BlackFriendEntity item) {
        ImageView imageView = helper.getView(R.id.avatar);
        ImgLoader.normalGlide(imageView, item.getImg(), R.mipmap.default_avatar);
        helper.setText(R.id.tv_name, item.getNikeName());
        helper.addOnClickListener(R.id.ll_delete);
    }
}
