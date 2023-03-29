package caixin.android.com.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivitySetPayPassBinding;
import com.kongzue.dialog.v3.MessageDialog;

import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.MoneyRecodeEntity;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.entity.MyPlatformEntity;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.ToastUtils;
import caixin.android.com.viewmodel.MomoExchangeViewModel;

public class SetPayPassActivity extends BaseActivity<ActivitySetPayPassBinding, MomoExchangeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_set_pay_pass;
    }

    @Override
    public MomoExchangeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MomoExchangeViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.setLeftLayoutClickListener(v -> finish());
        mBinding.titleBar.setTitle("设置支付密码").setTextColor(getResources().getColor(R.color.white));
        mBinding.etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.ibtnDeletePwd.setVisibility(!TextUtils.isEmpty(s) ? View.VISIBLE : View.GONE);
                updateConfirmButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.etPwdConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.ibtnDeletePwdConfirm.setVisibility(!TextUtils.isEmpty(s) ? View.VISIBLE : View.GONE);
                updateConfirmButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.ibtnDeletePwd.setOnClickListener(v -> mBinding.etPwd.setText(""));
        mBinding.ibtnDeletePwdConfirm.setOnClickListener(v -> mBinding.etPwdConfirm.setText(""));
        mBinding.cbPwdShow.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.etPwd.setInputType(!isChecked ?
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));

        mBinding.cbPwdShowConfirm.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.etPwdConfirm.setInputType(!isChecked ?
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
        mBinding.cbPwdShowConfirm.setChecked(true);
        mBinding.cbPwdShow.setChecked(true);
        mBinding.btnResetPassword.setOnClickListener(v -> {
                    if (!mBinding.etPwdConfirm.getText().toString().equals(mBinding.etPwd.getText().toString())) {
                        Toast.makeText(this, "密码和确认密码不一致！", Toast.LENGTH_SHORT).show();
                        showShortToast("密码和确认密码不一致！");
                        Log.e(TAG, "initData: 密码和确认密码不一致");
                        return;
                    }
                    mViewModel.setPayPass(mBinding.etPwd.getText().toString());
                }
        );
    }

    private static final String TAG = "SetPayPassActivity";

    private void updateConfirmButton() {
        mBinding.btnResetPassword.setEnabled(
                !TextUtils.isEmpty(mBinding.etPwd.getText()) &&
                        !TextUtils.isEmpty(mBinding.etPwdConfirm.getText()) &&
                        mBinding.etPwdConfirm.getText().length() == mBinding.etPwd.getText().length() &&
                        mBinding.etPwd.length() == 6
        );
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.setPayPass.observe(this, this::handleSetPass);
    }

    private void handleSetPass(Boolean b) {
        if (b) {
            UserInfoEntity userInfoEntity = MMKVUtil.getUserInfo();
            userInfoEntity.setSpay(1);
            MMKVUtil.setUserInfo(userInfoEntity);
            finish();
        }
    }
}