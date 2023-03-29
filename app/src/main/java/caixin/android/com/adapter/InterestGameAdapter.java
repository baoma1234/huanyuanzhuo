package caixin.android.com.adapter;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import caixin.android.com.entity.InterestGameModel;

public class InterestGameAdapter extends BaseQuickAdapter<InterestGameModel, BaseViewHolder> {

    public InterestGameAdapter() {
        super(R.layout.item_interest_game);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, InterestGameModel item) {
        Glide.with(mContext).load(item.getLogo()).transition(DrawableTransitionOptions.withCrossFade()).into((ImageView) helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_tips, item.getTips());
    }
}
