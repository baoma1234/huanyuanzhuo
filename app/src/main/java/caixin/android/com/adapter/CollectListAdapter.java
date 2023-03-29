package caixin.android.com.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.entity.CollectEntity;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.view.activity.PostImageDetailActivity;
import caixin.android.com.widget.MultiImageView;
import caixin.android.com.widget.PopupList;

public class CollectListAdapter extends BaseQuickAdapter<CollectEntity, BaseViewHolder> {
    List<String> popupMenuItemList = new ArrayList<>();
    public CollectListAdapter() {
        super(R.layout.item_collect);
        popupMenuItemList.clear();
        popupMenuItemList.add("复制");
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CollectEntity item) {
        if (item.getImgs() != null) {
            MultiImageView multiImageView = helper.getView(R.id.ll_img_container);
            multiImageView.setList(item.getImgs());
            multiImageView.setOnItemClickListener((view, position) -> PostImageDetailActivity.navTo(mContext, item.getImgs(), position));
        }
        helper.setText(R.id.tv_content, item.getText());
        helper.setGone(R.id.tv_content, !TextUtils.isEmpty(item.getText()));
        helper.setText(R.id.tv_name, item.getNikename());
        helper.setText(R.id.tv_time, item.getAdd_time());
        helper.addOnClickListener(R.id.tv_delete);
        TextView textView = helper.getView(R.id.tv_content);
        PopupList normalViewPopupList = new PopupList(mContext);
        normalViewPopupList.bind(textView, popupMenuItemList, new PopupList.PopupListListener() {
            @Override
            public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
                return true;
            }
            @Override
            public void onPopupListClick(View contextView, int contextPosition, int position) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", item.getText());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
            }
        });
    }
}
