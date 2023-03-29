package caixin.android.com.entity;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class YearAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int mPosition;

    public YearAdapter() {
        super(R.layout.rv_year_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_year, item);
        if (helper.getLayoutPosition() == mPosition) {
            helper.setBackgroundRes(R.id.tv_year, R.drawable.shape_tv_year_bg);
        } else {
            helper.setBackgroundRes(R.id.tv_year, R.drawable.shape_tv_year_gray_bg);
        }
    }

    //选择某一年
    public void selectPosition(int position) {
        mPosition = position;
    }
}