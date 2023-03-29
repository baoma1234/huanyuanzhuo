package caixin.android.com.utils;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caixin.huanyu.R;

import caixin.android.com.Application;
import caixin.android.com.entity.FriendEntity;

public class ViewSetUtil {

    public static void setUserNick(FriendEntity friendEntity, TextView textView) {
        if (textView != null) {
            if (friendEntity == null)
                return;
            textView.setText(friendEntity.getNikeName());
            if (!android.text.TextUtils.isEmpty(friendEntity.getNikeName())) {
                textView.setText(friendEntity.getNikeName());
            }
            if (!TextUtils.isEmpty(friendEntity.getTag())) {
                textView.setText(friendEntity.getTag());
            }
        }
    }

    public static void setUserAvatar(FriendEntity friendEntity, ImageView imageView) {
        if (friendEntity != null && friendEntity.getImg() != null) {
            try {
                ImgLoader.GlideLoadRoundedCorners(friendEntity.getImg(), imageView, R.mipmap.default_avatar);
            } catch (Exception e) {
                Glide.with(Application.getInstance()).load(friendEntity.getImg())
                        .apply(RequestOptions.placeholderOf(R.mipmap.default_avatar)
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(imageView);
            }
        }
    }
}
