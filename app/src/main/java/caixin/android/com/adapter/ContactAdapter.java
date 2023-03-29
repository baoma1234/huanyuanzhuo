package caixin.android.com.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.downtail.plus.extensions.FloaterExtension;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.ViewSetUtil;
import caixin.android.com.widget.MyImageView;


public class ContactAdapter extends BaseMultiItemQuickAdapter<FriendEntity, BaseViewHolder> implements FloaterExtension, Filterable {

    private Context context;
    private List<FriendEntity> originDatas;

    public ContactAdapter(List<FriendEntity> data, Context context) {
        super(data);
        originDatas = data;
        addItemType(0, R.layout.item_letter);
        addItemType(1, R.layout.item_contact);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FriendEntity item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.setText(R.id.tv_letter, item.getLetter());
                break;
            case 1:
                MyImageView avatar = helper.getView(R.id.avatar);
                TextView nameTextView = helper.getView(R.id.name);
                ViewSetUtil.setUserNick(item, nameTextView);
                ViewSetUtil.setUserAvatar(item, avatar);
                if (item.getIs_gf() == 1) {
                    helper.setVisible(R.id.iv_guanfang, true);
                } else {
                    helper.setVisible(R.id.iv_guanfang, false);
                }
                break;
        }
    }

    public int getLetterPosition(String letter) {
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).getLetter().toUpperCase().equals(letter)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isFloaterView(int position) {
        return getItemViewType(position) == 0;
    }

    @Override
    public int getItemType(int position) {
        return 0;
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
                        List<FriendEntity> filteredList = new ArrayList<>();
                        for (FriendEntity item : originDatas) {
                            //这里根据需求，添加匹配规则
                            Log.e(TAG, "performFiltering: " + item.getNikeName());
                            if (TextUtils.isEmpty(item.getNikeName()))
                                continue;
                            if (item.getNikeName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(item);
                                continue;
                            } else {
                                if (TextUtils.isEmpty(item.getTag()))
                                    continue;
                                else if (item.getTag().toLowerCase().contains(charString.toLowerCase())) {
                                    filteredList.add(item);
                                    continue;
                                }
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
                    setNewData((List<FriendEntity>) filterResults.values);
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
    public void setNewData(@Nullable List<FriendEntity> data) {
        super.setNewData(data);
        if (!isFilter) {
            originDatas = data;
        }
    }
}
