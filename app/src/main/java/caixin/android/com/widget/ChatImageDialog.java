package caixin.android.com.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.caixin.huanyu.R;

import caixin.android.com.entity.chatroom.MessageResponse;
import caixin.android.com.utils.ScreenDimenUtil;


/**
 * Created by cxf on 2018/11/28.
 */

public class ChatImageDialog extends PopupWindow {

    private Context mContext;
    private View mParent;
    private View mBg;
    private ImageView mCover;
    private float mScale;
    private int mScreenWidth;
    private int mScreenHeight;
    private ValueAnimator mAnimator;
    private int mStartX;
    private int mStartY;
    private int mDistanceX;
    private int mDistanceY;
    private ActionListener mActionListener;

    public ChatImageDialog(Context context, View parent) {
        mContext = context;
        mParent = parent;
        ScreenDimenUtil util = ScreenDimenUtil.getInstance();
        mScreenWidth = util.getScreenWidth();
        mScreenHeight = util.getScreenHeight();
        setContentView(initView());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ScreenDimenUtil.getInstance().getScreenHeight());
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setClippingEnabled(false);
        setFocusable(true);
        setOnDismissListener(() -> {
            if (mAnimator != null) {
                mAnimator.cancel();
            }
            if (mActionListener != null) {
                mActionListener.onImageDialogDismiss();
            }
            mActionListener = null;
        });
    }

    private View initView() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.view_chat_image, null);
        mBg = v.findViewById(R.id.bg);
        mCover = v.findViewById(R.id.cover);
        mCover.setOnClickListener(v12 -> dismiss());
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(animation -> {
            float v1 = (float) animation.getAnimatedValue();
            mCover.setTranslationX(mStartX + mDistanceX * v1);
            mCover.setTranslationY(mStartY + mDistanceY * v1);
            mCover.setScaleX(1 + (mScale - 1) * v1);
            mCover.setScaleY(1 + (mScale - 1) * v1);
            mBg.setAlpha(v1);
        });
        return v;
    }

    public void show(MessageResponse bean, int x, int y, int imageWidth, int imageHeight, Drawable drawable) {
        if (mCover == null || bean == null || imageWidth <= 0 || imageHeight <= 0 || drawable == null) {
            return;
        }
        showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
        ViewGroup.LayoutParams params = mCover.getLayoutParams();
        params.width = imageWidth;
        params.height = imageHeight;
        mCover.requestLayout();
        mCover.setTranslationX(x);
        mCover.setTranslationY(y);
        mCover.setImageDrawable(drawable);
        mScale = mScreenWidth / ((float) imageWidth);
        mStartX = x;
        mStartY = y;
        int targetX = mScreenWidth / 2 - imageWidth / 2;
        int targetY = mScreenHeight / 2 - imageHeight / 2;
        mDistanceX = targetX - mStartX;
        mDistanceY = targetY - mStartY;
        mAnimator.start();
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public interface ActionListener {
        void onImageDialogDismiss();
    }
}
