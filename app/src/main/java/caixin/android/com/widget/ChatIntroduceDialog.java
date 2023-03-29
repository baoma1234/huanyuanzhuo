package caixin.android.com.widget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.caixin.huanyu.R;

import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.WebUtils;



@SuppressLint("ValidFragment")
public class ChatIntroduceDialog extends AbsDialogFragment {


    String ruleContent;

    public ChatIntroduceDialog(String contents) {
        this.ruleContent = contents;
    }

    private TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_chat_room_introduce;
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
        params.width = DpUtil.dp2px(300);
        params.height = DpUtil.dp2px(390);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = mRootView.findViewById(R.id.tv_know);
        textView.setOnClickListener(v -> dismiss());
        WebUtils webUtils = new WebUtils();
        webUtils.setWebView(getContext(), mRootView.findViewById(R.id.container), ruleContent);
    }
}
