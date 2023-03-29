package caixin.android.com.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivitySecuritySettingsBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.UserInfoViewModel;

public class SecuritySettingsActivity extends BaseActivity<ActivitySecuritySettingsBinding, UserInfoViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        
        return R.layout.activity_security_settings;
    }

    @Override
    public UserInfoViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(UserInfoViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.title.setText(getResources().getString(R.string.security_setting));
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.rlChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ChangePassActivity.class);
            }
        });

        if (MMKVUtil.getUserInfo().getSpay() == 0) {
            mBinding.tvChangePayPassword.setText(getResources().getString(R.string.set_pay_password));
            mBinding.rlChangePayPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(SetPayPassActivity.class);
                }
            });
        } else {
            mBinding.tvChangePayPassword.setText(getResources().getString(R.string.change_pay_password));
            mBinding.rlChangePayPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(ChangePayPassActivity.class);
                }
            });
        }
    }

    @Override
    public void initViewObservable() {

    }
}
