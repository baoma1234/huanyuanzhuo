package caixin.android.com.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import caixin.android.com.entity.LiuHeIndexItem;

public class LiuHeGalleryAdapter extends BaseQuickAdapter<LiuHeIndexItem, BaseViewHolder> {
    public LiuHeGalleryAdapter(@Nullable List<LiuHeIndexItem> data) {
        super(R.layout.rv_liuhe_gallery_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiuHeIndexItem item) {
        helper.setText(R.id.tv_name, item.getTypename());
        RequestOptions option = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.img_user_head).placeholder(R.mipmap.web_default);
        Glide.with(mContext)
                .load(item.getImg())
                .apply(option)
                .into((ImageView) helper.getView(R.id.iv_icon));
        helper.setVisible(R.id.tv_status, item.getIs_update() == 1);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        try {
            for (int i = 0; i < getItemCount(); i++) {
                String sortStr = mData.get(i).getKey();
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
