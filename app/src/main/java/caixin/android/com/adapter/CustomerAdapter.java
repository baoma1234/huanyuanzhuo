package caixin.android.com.adapter;


import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import caixin.android.com.entity.CustomerModel;

public class CustomerAdapter extends BaseQuickAdapter<CustomerModel, BaseViewHolder> {

    public CustomerAdapter(@Nullable List<CustomerModel> data, int type) {
        super(R.layout.rv_customer_item, data);
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomerModel item) {
        if (mType == 1) {
            helper.setText(R.id.tv_customer, item.getPtname());
        } else {
            helper.setText(R.id.tv_customer, item.getName());
        }
    }

    private int mType;

}