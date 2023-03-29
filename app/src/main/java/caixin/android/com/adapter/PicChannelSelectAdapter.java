package caixin.android.com.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by jogger on 2019/7/5
 * 描述：
 */
public class PicChannelSelectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public PicChannelSelectAdapter() {
        super(R.layout.rv_pic_select_item);
    }

    private int selectedPosition = 0;

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView textView = helper.getView(R.id.tv_channel);
        textView.setText(item);
        ImageView imageView = helper.getView(R.id.iv_selector);
        if (helper.getLayoutPosition() == selectedPosition) {
            imageView.setBackground(mContext.getResources().getDrawable(R.mipmap.selected));
        } else {
            imageView.setBackground(mContext.getResources().getDrawable(R.mipmap.un_selected));
        }
    }
}
