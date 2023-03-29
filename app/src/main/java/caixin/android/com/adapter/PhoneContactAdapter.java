package caixin.android.com.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.downtail.plus.extensions.FloaterExtension;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.MyContacts;

public class PhoneContactAdapter extends BaseQuickAdapter<MyContacts, BaseViewHolder> implements  Filterable {
    private List<MyContacts> originDatas;

    public PhoneContactAdapter(@Nullable List<MyContacts> data) {
        super(R.layout.item_phone_contact, data);
        originDatas = data;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MyContacts item) {
        FriendEntity friendEntity = FriendDaoManager.getInstance().searchByAccount(item.getMobile());
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_mobile, item.getMobile());
        helper.addOnClickListener(R.id.tv_add);
        TextView textView = helper.getView(R.id.tv_add);
        if (friendEntity != null) {
            textView.setText(mContext.getResources().getString(R.string.accept));
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.shape_tv_translate_stroke_bg));
            textView.setTextColor(mContext.getResources().getColor(R.color.gray));
            textView.setClickable(false);
            if (friendEntity.getIsBlack() == 1) {
                textView.setText(mContext.getResources().getString(R.string.already_friend));
            } else {
                textView.setText(mContext.getResources().getString(R.string.already_black_friend));
            }
        } else {
            textView.setText(mContext.getResources().getString(R.string.add_friend));
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_text));
            textView.setTextColor(mContext.getResources().getColor(R.color.white));
            textView.setClickable(true);
        }
    }


    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new Filter() {
                //执行过滤操作
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    FilterResults filterResults = new FilterResults();
                    Log.e(TAG, "performFiltering: " + charString);
                    if (TextUtils.isEmpty(charString)) {
                        //没有过滤的内容，则使用源数据
                        filterResults.values = originDatas;
                        return filterResults;
                    } else {
                        List<MyContacts> filteredList = new ArrayList<>();
                        for (MyContacts item : originDatas) {
                            //这里根据需求，添加匹配规则
                            if (item.getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(item);
                                continue;
                            }
                            if (item.getMobile().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(item);
                            }
                        }
                        filterResults.values = filteredList;
                    }
                    return filterResults;
                }

                //把过滤后的值返回出来
                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mFilterList = (ArrayList<String>) filterResults.values;
                    isFilter = true;
                    setNewData((List<MyContacts>) filterResults.values);
                    isFilter = false;
//                    notifyDataSetChanged();
                }
            };
        }
        return myFilter;
    }

    private Filter myFilter;
    private boolean isFilter = false;

    @Override
    public void setNewData(@Nullable List<MyContacts> data) {
        super.setNewData(data);
        if (!isFilter) {
            originDatas = data;
        }
    }
}
