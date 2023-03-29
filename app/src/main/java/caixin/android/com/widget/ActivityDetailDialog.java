package caixin.android.com.widget;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caixin.huanyu.R;

import caixin.android.com.entity.ActiveModel;
import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.ImgLoader;

@SuppressLint("ValidFragment")
public class ActivityDetailDialog extends AbsDialogFragment {

    private ActiveModel mActivityDetailResponse;

    public ActivityDetailDialog(ActiveModel homeImageAdModel) {
        this.mActivityDetailResponse = homeImageAdModel;
    }

    public void setActivityDetailResponse(ActiveModel mActivityDetailResponse) {
        this.mActivityDetailResponse = mActivityDetailResponse;
    }

    public ActiveModel getActivityDetailResponse() {
        return mActivityDetailResponse;
    }

    public void setmActivityDetailResponse(ActiveModel mActivityDetailResponse) {
        this.mActivityDetailResponse = mActivityDetailResponse;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_activity_detail;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.custom_dialog2;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = DpUtil.dp2px(330);
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView imageView = mRootView.findViewById(R.id.iv_detail);
        mRootView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView tv_submit = mRootView.findViewById(R.id.tv_submit);
        LinearLayout ll_submit = mRootView.findViewById(R.id.ll_submit);

        ImgLoader.GlideLoad(imageView, mActivityDetailResponse.getImage(), R.mipmap.web_default);
        dialog.setCanceledOnTouchOutside(true);// 设置点击屏幕其他地方Dialog是否消失

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myClick != null)
                    myClick.onClick(mActivityDetailResponse);
            }
        });
        if (TextUtils.isEmpty(mActivityDetailResponse.getAction_url())) {
            ll_submit.setVisibility(View.GONE);
        } else {
            ll_submit.setVisibility(View.VISIBLE);
        }
       /* if (mActivityDetailResponse.getStatus() == 1) {
            tv_submit.setText("已领取");
            tv_submit.setEnabled(false);
        } else {
            tv_submit.setText("立即领取");
            tv_submit.setEnabled(true);
        }*/
    }

    private MyClick myClick;

    public void setMyClick(MyClick myClick) {
        this.myClick = myClick;
    }

    public interface MyClick {
        void onClick(ActiveModel activityDetailResponse);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
