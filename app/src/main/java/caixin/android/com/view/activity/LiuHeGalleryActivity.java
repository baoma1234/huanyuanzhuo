package caixin.android.com.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityEditNicknameBinding;
import com.caixin.huanyu.databinding.ActivityLiuheGalleryBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.view.fragment.LiuHeGalleryFragment;
import caixin.android.com.viewmodel.UserInfoViewModel;

public class LiuHeGalleryActivity extends BaseActivity<ActivityLiuheGalleryBinding, BaseViewModel> {

    public static final int AOMENLIUHE_GALLERY = 0;
    public static final int XIANGGANGLIUHE_GALLERY = 1;

    public static void navTo(Context context, int type) {
        Intent intent = new Intent(context, LiuHeGalleryActivity.class);
        intent.putExtra(Extras.TYPE, type);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_liuhe_gallery;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        
        mBinding.ivClose.setOnClickListener(v -> finish());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int type = getIntent().getIntExtra(Extras.TYPE, 0);
        if (type == AOMENLIUHE_GALLERY) {
            mBinding.tvTitle.setText(getResources().getString(R.string.aomen_gallery));
        } else if (type == XIANGGANGLIUHE_GALLERY) {
            mBinding.tvTitle.setText(getResources().getString(R.string.xianggang_gallery));
        }
        transaction.add(R.id.fl_container,
                LiuHeGalleryFragment.newInstance(type),
                LiuHeGalleryFragment.class.getSimpleName());
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void initViewObservable() {

    }
}
