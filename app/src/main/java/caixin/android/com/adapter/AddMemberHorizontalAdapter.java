package caixin.android.com.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.ImgLoader;


public class AddMemberHorizontalAdapter extends BaseQuickAdapter<FriendEntity, BaseViewHolder> {

    private RecyclerView mMyRecyclerView;

    public AddMemberHorizontalAdapter( RecyclerView recyclerView) {
        super(R.layout.item_add_member_horizontal);
        this.mMyRecyclerView = recyclerView;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FriendEntity item) {
        ImgLoader.GlideLoadRoundedCorners(item.getImg(), helper.getView(R.id.avatar), R.mipmap.img_user_head);
    }

    public void remove(FriendEntity friendEntity) {
        if (getData() != null && getData().size() > 0) {
            for (int i = 0; i < getData().size(); i++) {
                if (getData().get(i).getId() == friendEntity.getId()) {
                    if (i > 1) {
                        mMyRecyclerView.smoothScrollToPosition(i - 1);
                    }
                    remove(i);
                    return;
                }
            }
        }
    }
}
