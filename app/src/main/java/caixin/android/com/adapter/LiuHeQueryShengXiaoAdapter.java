package caixin.android.com.adapter;

import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import caixin.android.com.entity.LHCYModel;
import caixin.android.com.widget.CodeContainer;

public class LiuHeQueryShengXiaoAdapter extends BaseQuickAdapter<LHCYModel.ItemModel, BaseViewHolder> {
    public LiuHeQueryShengXiaoAdapter(@Nullable List<LHCYModel.ItemModel> data) {
        super(R.layout.rv_liuhe_query_shengxiao_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LHCYModel.ItemModel item) {
        helper.setText(R.id.tv_name, item.getName());
        ((CodeContainer) helper.getView(R.id.cc_container)).addCodeViews(item.getNumber());
    }
}
