package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityForgetPassBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.entity.CaptchaCheckIt;
import caixin.android.com.entity.CaptchaGetIt;
import caixin.android.com.entity.base.VerificationImgBaseResponse;
import caixin.android.com.utils.ToastUtils;
import caixin.android.com.viewmodel.ForgetPasswordViewModel;
import caixin.android.com.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import caixin.android.com.widget.verificationcode.BlockPuzzleDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetPasswordActivity extends BaseActivity<ActivityForgetPassBinding, ForgetPasswordViewModel> {

    private Disposable mRegisterDisposable;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_forget_pass;
    }

    @Override
    public ForgetPasswordViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ForgetPasswordViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        mBinding.btnGetCode.setOnClickListener(view -> onClick());
        mBinding.cbPwdShow.setOnCheckedChangeListener((buttonView, isChecked) ->
                mBinding.editPwd.setInputType(isChecked ?
                        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));

        mBinding.btnRegist.setOnClickListener(v -> {

            String currentUsername = mBinding.editPhone.getText().toString();
            String currentPassword = mBinding.editPwd.getText().toString();
            String currentCode = mBinding.editCode.getText().toString();

            if (TextUtils.isEmpty(currentUsername)) {
                caixin.android.com.utils.ToastUtils.show("手机号不能为空");
                return;
            }
            if (TextUtils.isEmpty(currentCode)) {
                caixin.android.com.utils.ToastUtils.show("验证码不能为空");
                return;
            }
            if (currentUsername.length() != 11) {
                caixin.android.com.utils.ToastUtils.show("请输入正确的11位手机号！");
            }
            if (TextUtils.isEmpty(currentPassword)) {
                caixin.android.com.utils.ToastUtils.show(getResources().getString(R.string.Password_cannot_be_empty));
                return;
            }

            mViewModel.resetPassword(currentUsername,
                    currentCode,
                    currentPassword);
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.resetPassword.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                new com.adorkable.iosdialog.AlertDialog(ForgetPasswordActivity.this)
                        .init()
                        .setTitle("重置成功")
                        .setMsg((String) o)
                        .setPositiveButton("确定", v1 -> {
                            ForgetPasswordActivity.this.finish();
                        }).show();
            }
        });

        mViewModel.uc.getVerifyCode.observe(this, o -> handleGetVerifyCode(o));
    }

    private void handleGetVerifyCode(Object o) {
        Observable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mRegisterDisposable = d;
                        mBinding.btnGetCode.setEnabled(false);
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(Long aLong) {
                        mBinding.btnGetCode.setText("已发送" + "(" + (60L - aLong) + ")");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mBinding.btnGetCode.setText("获取验证码");
                        mBinding.btnGetCode.setClickable(true);
                    }

                    @Override
                    public void onComplete() {
                        mBinding.btnGetCode.setText("获取验证码");
                        mBinding.btnGetCode.setEnabled(true);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRegisterDisposable != null && mRegisterDisposable.isDisposed()) {
            mRegisterDisposable.dispose();
        }
    }

    private void onClick() {
        new com.adorkable.iosdialog.AlertDialog(ForgetPasswordActivity.this)
                .init()
                .setTitle("确认电话号码")
                .setMsg("我们将发送验证码到这个号码 \n" + mBinding.editPhone.getText().toString())
                .setPositiveButton("好", v1 -> {
                    mViewModel.getVerifyCode(mBinding.editPhone.getText().toString(), "2");
                })
                .setNegativeButton("取消", v12 -> {
                }).show();
    }
}
