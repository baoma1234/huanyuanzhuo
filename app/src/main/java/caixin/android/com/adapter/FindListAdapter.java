package caixin.android.com.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.utils.ImgLoader;

public class FindListAdapter extends BaseQuickAdapter<FindItemModel, BaseViewHolder> {

    public FindListAdapter() {
        super(R.layout.item_find);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FindItemModel item) {
        ImageView imageView = helper.getView(R.id.image);
        ImgLoader.normalGlide(imageView, item.getImg(), R.mipmap.default_avatar);
        helper.setText(R.id.tv_title, item.getTitle());
    }
}
