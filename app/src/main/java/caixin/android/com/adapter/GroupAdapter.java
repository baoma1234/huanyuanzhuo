package caixin.android.com.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import caixin.android.com.entity.GroupEntity;
import caixin.android.com.utils.ImgLoader;


public class GroupAdapter extends BaseQuickAdapter<GroupEntity, BaseViewHolder> {

    private Context context;

    public GroupAdapter(Context context) {
        super(R.layout.item_group);
        this.context = context;
    }

    private static final String TAG = "GroupAdapter";

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GroupEntity item) {
        helper.setText(R.id.name, item.getName());
        helper.getView(R.id.tv_state_applying).setVisibility(View.GONE);
        helper.getView(R.id.btn_apply).setVisibility(View.GONE);
        ImgLoader.GlideLoadRoundedCorners(item.getGroupImage(), helper.getView(R.id.avatar), R.mipmap.default_avatar);
       /* switch (item.getStatus()) {
            case GroupEntity.STATE_ADDED:
                break;
            case GroupEntity.STATE_APPLYING:
                helper.getView(R.id.tv_state_applying).setVisibility(View.VISIBLE);
                break;
            case GroupEntity.STATE_NOT_ADDED:
                helper.getView(R.id.btn_apply).setVisibility(View.VISIBLE);
                helper.addOnClickListener(R.id.btn_apply);
                break;
        }*/
    }
}
