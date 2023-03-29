package caixin.android.com.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.downtail.plus.extensions.FloaterExtension;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.widget.MyImageView;


public class AddManagerVerticalAdapter extends BaseMultiItemQuickAdapter<MemberEntity, BaseViewHolder> implements FloaterExtension, Filterable {

    Context mContext;
    private List<MemberEntity> originData;
    private RecyclerView mMyRecyclerView;

    public AddManagerVerticalAdapter(Context context, RecyclerView recyclerView) {
        super(null);
        addItemType(0, R.layout.item_letter);
        addItemType(1, R.layout.item_add_member_vertical);
        this.mContext = context;
        this.mMyRecyclerView = recyclerView;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MemberEntity item) {
        switch (helper.getItemViewType()) {
            case 0:
                helper.setText(R.id.tv_letter, item.getLetter());
                break;
            case 1:
                MyImageView avatar = helper.getView(R.id.avatar);
                TextView nameTextView = helper.getView(R.id.name);
                setUserNick(item, nameTextView);
                setUserAvatar(item, avatar);
                ImageView checkBox = helper.getView(R.id.checkbox);
              if (item.isSelected()) {
                    checkBox.setImageResource(R.mipmap.selected);
                } else if (!item.isSelected()) {
                    checkBox.setImageResource(R.mipmap.un_selected);
                }
                break;
        }
    }

    private void setUserNick(MemberEntity friendEntity, TextView textView) {
        if (textView != null) {
            if (friendEntity == null)
                return;
            if (!TextUtils.isEmpty(friendEntity.getNikename())) {
                textView.setText(friendEntity.getNikename());
            }
            if (!TextUtils.isEmpty(friendEntity.getTag())) {
                textView.setText(friendEntity.getTag());
            }
        }
    }

    private void setUserAvatar(MemberEntity friendEntity, ImageView imageView) {
        if (friendEntity != null && friendEntity.getImg() != null) {
            try {
                ImgLoader.GlideLoadRoundedCorners(friendEntity.getImg(), imageView, R.mipmap.default_avatar);
            } catch (Exception e) {
                Glide.with(mContext).load(friendEntity.getImg())
                        .apply(RequestOptions.placeholderOf(R.mipmap.default_avatar)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            }
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
                    Log.e(TAG, "CharSequence: " + charString);
                    if (TextUtils.isEmpty(charString)) {
                        //没有过滤的内容，则使用源数据
                        filterResults.values = originData;
                        return filterResults;
                    } else {
                        List<MemberEntity> filteredList = new ArrayList<>();
                        for (MemberEntity item : originData) {
                            //这里根据需求，添加匹配规则
                            Log.e(TAG, "item.getNikename()= " + item.getNikename());
                            if (TextUtils.isEmpty(item.getNikename()))
                                continue;
                            if (item.getNikename().toLowerCase().contains(charString.toLowerCase())) {
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
                    setNewData((List<MemberEntity>) filterResults.values);
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
    public void setNewData(@Nullable List<MemberEntity> data) {
        super.setNewData(data);
        if (!isFilter) {
            originData = data;
        }
    }

    //获得选中条目的结果
    public ArrayList<MemberEntity> getSelectedItem() {
        ArrayList<MemberEntity> selectList = new ArrayList<>();
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).isSelected()) {
                selectList.add(getData().get(i));
            }
        }
        return selectList;
    }

    public void setItemChecked(MemberEntity friendEntity, boolean isChecked) {
        if (getData() != null && getData().size() > 0) {
            for (int i = 0; i < getData().size(); i++) {
                if (getData().get(i).getId() == friendEntity.getId()) {
                    getData().get(i).setSelected(isChecked);
                    notifyItemChanged(i);
                    return;
                }
            }
        }
    }
}
