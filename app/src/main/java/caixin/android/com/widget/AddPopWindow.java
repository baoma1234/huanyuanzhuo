package caixin.android.com.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.caixin.huanyu.R;

import caixin.android.com.utils.DpUtil;
import caixin.android.com.view.activity.AddFriendActivity;
import caixin.android.com.view.activity.ScanActivity;

public class AddPopWindow extends PopupWindow implements PopupWindow.OnDismissListener {
    private View conentView;
    private Activity context;

    public AddPopWindow(final Activity context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popwindow_add, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        this.setContentView(conentView);
        this.setWidth(FrameLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        setOnDismissListener(this);
        this.setAnimationStyle(R.style.AnimationPreview);

        conentView.findViewById(R.id.ll_scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ScanActivity.class);
                context.startActivity(intent);
                dismiss();
            }
        });
        conentView.findViewById(R.id.ll_add_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, AddFriendActivity.class);
                context.startActivity(intent);
                dismiss();
            }
        });
    }

    private static final String TAG = "AddPopWindow";

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            Log.e(TAG, "showPopupWindow: " + parent.getLayoutParams().width);
            this.showAsDropDown(parent, -DpUtil.dp2px(105), 5, Gravity.RIGHT);
            WindowManager.LayoutParams params = context.getWindow().getAttributes();
            params.alpha = 0.7f;
            context.getWindow().setAttributes(params);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onDismiss() {
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = 1f;
        context.getWindow().setAttributes(params);
    }
}