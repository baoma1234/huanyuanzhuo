package caixin.android.com.adapter;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import caixin.android.com.entity.LRHMModel;
import caixin.android.com.widget.NumberIcon;

public class CodeQueryAdapter extends BaseQuickAdapter<LRHMModel.ColdHotDataBean, BaseViewHolder> {

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CodeQueryAdapter(@Nullable List<LRHMModel.ColdHotDataBean> data) {
        super(R.layout.rv_code_query_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LRHMModel.ColdHotDataBean item) {
        helper.setText(R.id.tv_content,
                Html.fromHtml(String.format(mContext.getString(R.string.code_find_count_format), item.getCount())));
        if (type == 1) {
            (helper.getView(R.id.ni_code)).setVisibility(View.GONE);
            (helper.getView(R.id.tv_code)).setVisibility(View.VISIBLE);
            ((TextView) helper.getView(R.id.tv_code)).setText(item.getNum());
        } else {
            (helper.getView(R.id.ni_code)).setVisibility(View.VISIBLE);
            (helper.getView(R.id.tv_code)).setVisibility(View.GONE);
            ((NumberIcon) helper.getView(R.id.ni_code)).setNumber(Integer.valueOf(item.getNum()));
        }
    }
}