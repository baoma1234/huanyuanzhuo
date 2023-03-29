package caixin.android.com.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityLoginBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.constant.Extras;
import caixin.android.com.utils.CommonUtils;
import caixin.android.com.Application;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.ToastUtils;
import caixin.android.com.viewmodel.LoginViewModel;


public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    private static final String TAG = "LoginActivity";

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getBooleanExtra(Extras.INTENT_LOGIN_AGAIN, false)) {
                showShortToast(intent.getStringExtra(Extras.INTENT_LOGIN_MESSAGE));
            }
        }

        mBinding.cbPwdShow.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.editPwd.setInputType(!isChecked ?
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        mBinding.cbPwdShow.setChecked(true);

        mBinding.register.setOnClickListener(v -> {
            Log.e(TAG, "initData: ");
            startActivityForResult(RegisterActivity.class, null, 1000);
        });
        mBinding.tvForget.setOnClickListener(v -> startActivity(ForgetPasswordActivity.class));
        mBinding.btnLogin.setOnClickListener(v -> {
            login();
        });
        String loginPhone = MMKVUtil.getLoginPhone();
        mBinding.editPhone.setText(loginPhone);
        if (!TextUtils.isEmpty(MMKVUtil.getLoginPassword()) && !TextUtils.isEmpty(MMKVUtil.getLoginPhone())) {
            mBinding.editPwd.setText(MMKVUtil.getLoginPassword());
        }
    }

    private void login() {
        if (!CommonUtils.isNetWorkConnected(Application.getInstance())) {
            ToastUtils.show(getResources().getString(R.string.network_isnot_available));
            return;
        }
        String currentUsername = mBinding.editPhone.getText().toString();
        String currentPassword = mBinding.editPwd.getText().toString();
        if (TextUtils.isEmpty(currentUsername)) {
            ToastUtils.show("手机号不能为空");
            return;
        }
        if (currentUsername.length() != 11) {
            ToastUtils.show("请输入正确的11位手机号！");
        }
        if (TextUtils.isEmpty(currentPassword)) {
            ToastUtils.show(getResources().getString(R.string.Password_cannot_be_empty));
            return;
        }

        mViewModel.httpLogin(currentUsername, currentPassword);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.login.observe(this, this::handleLogin);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void handleLogin(Object o) {
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBinding.editPhone.setText(MMKVUtil.getLoginPhone());
    }
}
