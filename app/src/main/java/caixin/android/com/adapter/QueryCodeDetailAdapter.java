package caixin.android.com.adapter;

import android.util.Log;

import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import caixin.android.com.entity.LRHMModel;

/**
 * Created by jogger on 2019/7/5
 * 描述：
 */
public class QueryCodeDetailAdapter extends BaseQuickAdapter<LRHMModel.HotdataBean, BaseViewHolder> {
    private LRHMModel.ColdHotDataBean mColdHotDataBean;

    public QueryCodeDetailAdapter(@Nullable List<LRHMModel.HotdataBean> data, LRHMModel.ColdHotDataBean coldHotDataBean) {
        super(R.layout.rv_query_code_detail_item, data);
        mColdHotDataBean = coldHotDataBean;
    }

    @Override
    protected void convert(BaseViewHolder helper, LRHMModel.HotdataBean item) {
        helper.setText(R.id.tv_year, item.getYear() + "-" + item.getNumber());
        helper.setText(R.id.tv_one, item.getNumbers().get(0));
        helper.setText(R.id.tv_two, item.getNumbers().get(1));
        helper.setText(R.id.tv_three, item.getNumbers().get(2));
        helper.setText(R.id.tv_four, item.getNumbers().get(3));
        helper.setText(R.id.tv_five, item.getNumbers().get(4));
        helper.setText(R.id.tv_six, item.getNumbers().get(5));
        helper.setText(R.id.tv_code, item.getNumbers().get(6));
        helper.getView(R.id.tv_one).setSelected(false);
        helper.getView(R.id.tv_two).setSelected(false);
        helper.getView(R.id.tv_three).setSelected(false);
        helper.getView(R.id.tv_four).setSelected(false);
        helper.getView(R.id.tv_five).setSelected(false);
        helper.getView(R.id.tv_six).setSelected(false);
        helper.getView(R.id.tv_code).setSelected(false);
        for (int i = 0; i < item.getNumbers().size(); i++) {
            String s = item.getNumbers().get(i);
            if (s.equals(mColdHotDataBean.getNum())) {
                switch (i) {
                    case 0:
                        helper.getView(R.id.tv_one).setSelected(true);
                        break;
                    case 1:
                        helper.getView(R.id.tv_two).setSelected(true);
                        break;
                    case 2:
                        helper.getView(R.id.tv_three).setSelected(true);
                        break;
                    case 3:
                        helper.getView(R.id.tv_four).setSelected(true);
                        break;
                    case 4:
                        helper.getView(R.id.tv_five).setSelected(true);
                        break;
                    case 5:
                        helper.getView(R.id.tv_six).setSelected(true);
                        break;
                    case 6:
                        helper.getView(R.id.tv_code).setSelected(true);
                        break;
                }
            }
        }
    }
}
