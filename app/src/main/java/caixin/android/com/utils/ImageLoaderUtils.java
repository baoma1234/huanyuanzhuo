package caixin.android.com.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caixin.huanyu.R;

/**
 * @author：luck
 * @date：2021/7/14 3:15 PM
 * @describe：ImageLoaderUtils
 */
public class ImageLoaderUtils {

    public static boolean assertValidRequest(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !isDestroy(activity);
        } else if (context instanceof ContextWrapper) {
            ContextWrapper contextWrapper = (ContextWrapper) context;
            if (contextWrapper.getBaseContext() instanceof Activity) {
                Activity activity = (Activity) contextWrapper.getBaseContext();
                return !isDestroy(activity);
            }
        }
        return true;
    }

    private static boolean isDestroy(Activity activity) {
        if (activity == null) {
            return true;
        }
        return activity.isFinishing() || activity.isDestroyed();
    }


    public static void loadCropImage(ImageView view, String url) {
        if (view != null) {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            Glide.with(view.getContext())
                    .load(url)
                    .apply(RequestOptions.placeholderOf(R.mipmap.default_avatar)
                            .diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    )
                    .into(view);
        }
    }

}
