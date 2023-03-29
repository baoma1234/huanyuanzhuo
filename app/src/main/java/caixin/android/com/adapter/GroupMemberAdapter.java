package caixin.android.com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import caixin.android.com.Application;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.utils.GlideCircleTransform;
import caixin.android.com.utils.ImgLoader;

import java.util.ArrayList;
import java.util.List;


public class GroupMemberAdapter extends BaseMultiItemQuickAdapter<MemberEntity, BaseViewHolder> implements Filterable {

    private Context context;
    private List<MemberEntity> originData;

    public GroupMemberAdapter(Context context) {
        super(null);
        addItemType(0, R.layout.item_group_member_horizontal);
        addItemType(1, R.layout.item_group_member_horizontal);
        addItemType(2, R.layout.item_group_member_horizontal);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MemberEntity item) {
        ImageView avatar = helper.getView(R.id.avatar);
        TextView nameTextView = helper.getView(R.id.tv_name);
        switch (helper.getItemViewType()) {
            case 0:
                setUserNick(item, nameTextView);
                setUserAvatar(item, avatar);
                TextView noteTextView = helper.getView(R.id.tv_note);
                if (item.getManager() == MemberEntity.MANAGER_BOSS) {
                    noteTextView.setText("主");
                    noteTextView.setBackground(context.getResources().getDrawable(R.drawable.bg_text_view_zhu_circle));
                    noteTextView.setVisibility(View.VISIBLE);
                } else if (item.getManager() == MemberEntity.MANAGER_MANAGER) {
                    noteTextView.setText("管");
                    noteTextView.setBackground(context.getResources().getDrawable(R.drawable.bg_text_view_guan_circle));
                    noteTextView.setVisibility(View.VISIBLE);
                } else {
                    noteTextView.setVisibility(View.GONE);
                }
                break;
            case 1:
                nameTextView.setVisibility(View.GONE);
                avatar.setImageResource(R.mipmap.add);
                break;
            case 2:
                nameTextView.setVisibility(View.GONE);
                avatar.setImageResource(R.mipmap.subtract);
                break;
        }


    }

    public void setUserNick(MemberEntity friendEntity, TextView textView) {
        if (textView != null) {
            if (friendEntity == null)
                return;
            textView.setText(friendEntity.getNikename());
            if (!TextUtils.isEmpty(friendEntity.getTag())) {
                textView.setText(friendEntity.getTag());
            }
        }
    }

    public void setUserAvatar(MemberEntity friendEntity, ImageView imageView) {
        if (friendEntity != null && friendEntity.getImg() != null) {
            try {
//                int avatarResId = Integer.parseInt(friendEntity.getImg());
                ImgLoader.GlideLoadRoundedCorners(friendEntity.getImg(), imageView, R.mipmap.default_avatar);
//                Glide.with(context).load(avatarResId).into(imageView);
            } catch (Exception e) {
                //use default avatar
              /*  Glide.with(context).load(friendEntity.getImg())
                        .apply(RequestOptions.placeholderOf(R.mipmap.default_avatar)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
*/

                RequestOptions requestOptions = new RequestOptions()
                        .fitCenter()
                        .transform(new GlideCircleTransform(Application.getInstance()))
                        .error(R.mipmap.default_avatar)
                        .skipMemoryCache(true)
                        .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                        .placeholder(R.mipmap.default_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                Glide.with(Application.getInstance())
                        .load(friendEntity.getImg())
                        .apply(requestOptions)
                        .into(imageView);
            }
        }
    }

    private Filter myFilter;
    private boolean isFilter = false;

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
                        filterResults.values = originData;
                        return filterResults;
                    } else {
                        List<MemberEntity> filteredList = new ArrayList<>();
                        for (MemberEntity item : originData) {
                            //这里根据需求，添加匹配规则
                            Log.e(TAG, "performFiltering: " + item.getNikename());
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

    @Override
    public void setNewData(@Nullable List<MemberEntity> data) {
        super.setNewData(data);
        if (!isFilter) {
            originData = data;
        }
    }
}
