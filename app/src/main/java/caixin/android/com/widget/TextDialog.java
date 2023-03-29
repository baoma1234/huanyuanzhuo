package caixin.android.com.widget;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.caixin.huanyu.R;

@SuppressLint("ValidFragment")
public class TextDialog extends AbsDialogFragment {

    private String content;

    public TextDialog(String content) {
        this.content = content;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_text;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.custom_dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = mRootView.findViewById(R.id.tv_content);
        textView.setText(content);

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        manager.beginTransaction().remove(this).commit();
        super.show(manager, tag);
    }
}
