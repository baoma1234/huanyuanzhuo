package caixin.android.com.view.activity;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityChangePassBinding;
import com.caixin.huanyu.databinding.ActivityChangePayPassBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.ChangePassViewModel;


public class ChangePayPassActivity extends BaseActivity<ActivityChangePayPassBinding, ChangePassViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_change_pay_pass;
    }

    @Override
    public ChangePassViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ChangePassViewModel.class);
    }

    private boolean isOldPasswordVisibility = false;
    private boolean isNewPasswordVisibility = false;
    private boolean isConfirmPasswordVisibility = false;

    @Override
    public void initData(Bundle savedInstanceState) {
        
        mBinding.etConfirmNewPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        mBinding.etConfirmNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mBinding.etNewPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        mBinding.etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mBinding.etOldPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        mBinding.etOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        mBinding.ivOldSwitchPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOldPasswordVisibility = !isOldPasswordVisibility;
                if (isOldPasswordVisibility) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    mBinding.ivOldSwitchPassword.setImageResource(R.mipmap.show_psw);
                    mBinding.etOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    mBinding.ivOldSwitchPassword.setImageResource(R.mipmap.show_psw_press);
                    mBinding.etOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mBinding.ivNewSwitchPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNewPasswordVisibility = !isNewPasswordVisibility;
                if (isNewPasswordVisibility) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    mBinding.ivNewSwitchPassword.setImageResource(R.mipmap.show_psw);
                    mBinding.etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    mBinding.ivNewSwitchPassword.setImageResource(R.mipmap.show_psw_press);
                    mBinding.etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        mBinding.ivConfirmNewSwitchPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConfirmPasswordVisibility = !isConfirmPasswordVisibility;
                if (isConfirmPasswordVisibility) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    mBinding.ivConfirmNewSwitchPassword.setImageResource(R.mipmap.show_psw);
                    mBinding.etConfirmNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    mBinding.ivConfirmNewSwitchPassword.setImageResource(R.mipmap.show_psw_press);
                    mBinding.etConfirmNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mBinding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPasswordString = mBinding.etOldPassword.getText().toString();
                String newPasswordString = mBinding.etNewPassword.getText().toString();
                String confirmNewPasswordString = mBinding.etConfirmNewPassword.getText().toString();
                if (TextUtils.isEmpty(oldPasswordString)) {
                    showShortToast(R.string.old_password_cannot_be_empty);
                    return;
                }
                if (TextUtils.isEmpty(newPasswordString)) {
                    showShortToast(R.string.new_password_cannot_be_empty);
                    return;
                }
                if (TextUtils.isEmpty(confirmNewPasswordString)) {
                    showShortToast(R.string.confirm_new_password_cannot_be_empty);
                    return;
                }
                if (!confirmNewPasswordString.equals(newPasswordString)) {
                    showShortToast(R.string.confirm_password_not_equal_old_password);
                    return;
                }
                mViewModel.changePayPassword(oldPasswordString, confirmNewPasswordString);
            }
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.changePassword.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                finish();
            }
        });
    }
}
