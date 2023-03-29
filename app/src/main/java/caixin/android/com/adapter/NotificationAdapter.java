package caixin.android.com.adapter;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import caixin.android.com.entity.NotificationEntity;


public class NotificationAdapter extends BaseQuickAdapter<NotificationEntity, BaseViewHolder> {

    public NotificationAdapter() {
        super(R.layout.item_invite_msg);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NotificationEntity item) {
        helper.setText(R.id.name, item.getInfo().getName());
        switch (item.getStatus()) {
            case 0:
                helper.setText(R.id.message, "审核中");
                break;
            case 1:
                helper.setText(R.id.message, "审核通过");
                break;
            case 2:
                helper.setText(R.id.message, "审核未通过");
                break;
        }
    }
}
