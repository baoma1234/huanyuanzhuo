package caixin.android.com.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.LikeEmojiEntity;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.view.activity.EditEmojiActivity;


public class EmojiEditAdapter extends BaseQuickAdapter<LikeEmojiEntity, BaseViewHolder> {

    private RecyclerView mMyRecyclerView;
    private EditEmojiActivity editEmojiActivity;
    private boolean isEditing = false;
    private List<LikeEmojiEntity> originData;

    public EmojiEditAdapter(EditEmojiActivity editEmojiActivity, RecyclerView recyclerView) {
        super(R.layout.item_like_emoji_vertical_layout);
        this.editEmojiActivity = editEmojiActivity;
        this.mMyRecyclerView = recyclerView;
    }

    public void setIsEditing(boolean isEditing, List<LikeEmojiEntity> data) {
        this.isEditing = isEditing;
        if (originData == null)
            originData = new ArrayList<>();
        originData.clear();
        originData.addAll(data);
        if (isEditing) {
            if (originData != null)
                originData.remove(0);
        }
        setNewData(originData);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LikeEmojiEntity item) {
        ImageView checkBox = helper.getView(R.id.checkbox);
        if (isEditing) {
            checkBox.setVisibility(View.VISIBLE);
            if (item.isSelected()) {
                checkBox.setImageResource(R.mipmap.selected);
            } else if (!item.isSelected()) {
                checkBox.setImageResource(R.mipmap.un_selected);
            }
            ImgLoader.ChatRoomRoundCorners(item.getSrc(), helper.getView(R.id.item_img), R.mipmap.web_default);
        } else {
            checkBox.setVisibility(View.GONE);
            if (helper.getAdapterPosition() == 0) {
                ImgLoader.ChatRoomRoundCorners(item.getLocalSrc(), helper.getView(R.id.item_img), R.mipmap.web_default);
            } else {
                ImgLoader.ChatRoomRoundCorners(item.getSrc(), helper.getView(R.id.item_img), R.mipmap.web_default);
            }
        }
    }

    public void setItemChecked(int position) {
        getData().get(position).setSelected(!getData().get(position).isSelected());
        notifyItemChanged(position);
    }

    //获得选中条目的结果
    public ArrayList<LikeEmojiEntity> getSelectedItems() {
        ArrayList<LikeEmojiEntity> selectList = new ArrayList<>();
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i).isSelected()) {
                selectList.add(getData().get(i));
            }
        }
        return selectList;
    }

    public void removeSelectedItem() {

    }
}
