package caixin.android.com.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import caixin.android.com.entity.ActiveModel;

public class DailyActivityAdapter extends BaseQuickAdapter<ActiveModel, BaseViewHolder> {

    public DailyActivityAdapter() {
        super(R.layout.rv_daily_activity_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActiveModel item) {
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.iv_activity));
        if (helper.getAdapterPosition() == getData().size() - 1) {
            helper.setVisible(R.id.line, false);
        } else {
            helper.setVisible(R.id.line, true);
        }
    }
}