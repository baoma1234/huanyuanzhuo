package caixin.android.com.adapter;


import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import caixin.android.com.entity.MOMOExchangeRecord;

/**
 * Created by jogger on 2019/7/15
 * 描述：
 */
public class MOMOExchangeRecordAdapter extends BaseQuickAdapter<MOMOExchangeRecord.RecordsBean, BaseViewHolder> {
    public MOMOExchangeRecordAdapter(@Nullable List<MOMOExchangeRecord.RecordsBean> data) {
        super(R.layout.rv_momo_exchange_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MOMOExchangeRecord.RecordsBean item) {
        helper.setText(R.id.tv_name, "兑换彩金");
        helper.setText(R.id.tv_date, item.getAddtime());
        helper.setText(R.id.tv_msg, "-" + item.getAmount() + "元");
        helper.setText(R.id.tv_reasons, item.getReasons());
        if (item.getStatus() == 0) {
            helper.setImageResource(R.id.iv_status, R.mipmap.exchange_doing);
        } else if (item.getStatus() == 1) {
            helper.setImageResource(R.id.iv_status, R.mipmap.exchange_success);
        } else {
            helper.setImageResource(R.id.iv_status, R.mipmap.exchange_fail);
        }
    }
}
