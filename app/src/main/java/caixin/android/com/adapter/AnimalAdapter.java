package caixin.android.com.adapter;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class AnimalAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public AnimalAdapter() {
        super(R.layout.item_animal);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        Glide.with(mContext).load(item).transition(DrawableTransitionOptions.withCrossFade()).into((ImageView) helper.getView(R.id.iv_animal));
    }
}
