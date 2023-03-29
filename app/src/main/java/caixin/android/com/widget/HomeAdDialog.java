package caixin.android.com.widget;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.caixin.huanyu.R;

import caixin.android.com.entity.HomeImageAdModel;
import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.MMKVUtil;

@SuppressLint("ValidFragment")
public class HomeAdDialog extends AbsDialogFragment {

    private HomeImageAdModel mHomeImageAdModel;

    public HomeAdDialog(HomeImageAdModel homeImageAdModel) {
        this.mHomeImageAdModel = homeImageAdModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_home_ad;
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
        params.width = DpUtil.dp2px(250);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView imageView = mRootView.findViewById(R.id.iv_ad);
        ImageView deleteImageView = mRootView.findViewById(R.id.iv_delete);
        CheckBox checkBox = mRootView.findViewById(R.id.cb_no_more_today);
        deleteImageView.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                MMKVUtil.setHomeImageAdTime(System.currentTimeMillis() + "");
            }
            dismiss();
        });
        ImgLoader.GlideLoad(imageView, mHomeImageAdModel.getTanch().get(position).getImg(), R.mipmap.web_default);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHomeImageAdModel.getTanch().get(position).getUrl() != null)
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mHomeImageAdModel.getTanch().get(position).getUrl())));
            }
        });
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        manager.beginTransaction().remove(this).commit();
        super.show(manager, tag);
    }

    private int position = 0;


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        position++;
        if (mHomeImageAdModel.getTanch().size() > position)
            show(getFragmentManager(), "chatIntroduce");
        else
            position = 0;
    }
}
