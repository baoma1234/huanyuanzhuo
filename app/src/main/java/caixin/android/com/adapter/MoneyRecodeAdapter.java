package caixin.android.com.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import caixin.android.com.entity.MoneyRecodeEntity;

public class MoneyRecodeAdapter extends BaseQuickAdapter<MoneyRecodeEntity, BaseViewHolder> {
    //	1未审核 2已审核 3审核失败 4已打款
    public MoneyRecodeAdapter() {
        super(R.layout.item_money_recode);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MoneyRecodeEntity item) {
        helper.setText(R.id.tv_add_time, item.getAddtime());
        helper.setText(R.id.tv_money, item.getMoney() + "元");
        helper.setText(R.id.tv_title, item.getReason());
    }
}
