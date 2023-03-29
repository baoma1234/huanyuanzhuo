package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityForgetPayPassBinding;

import java.util.concurrent.TimeUnit;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.CaptchaCheckIt;
import caixin.android.com.entity.CaptchaGetIt;
import caixin.android.com.entity.base.VerificationImgBaseResponse;
import caixin.android.com.viewmodel.ForgetPasswordViewModel;
import caixin.android.com.widget.verificationcode.BlockPuzzleDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgetPayPasswordActivity extends BaseActivity<ActivityForgetPayPassBinding, ForgetPasswordViewModel> {

    private Disposable mRegisterDisposable;
    private BlockPuzzleDialog blockPuzzleDialog;
    private CaptchaGetIt captchaGetIt;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_forget_pay_pass;
    }

    @Override
    public ForgetPasswordViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ForgetPasswordViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        blockPuzzleDialog = new BlockPuzzleDialog(this);
        blockPuzzleDialog.setOnResultsListener(new BlockPuzzleDialog.OnResultsListener() {
            @Override
            public void onResultsClick(String result) {

            }

            @Override
            public void onReInit() {
                mViewModel.getVerificationImg();
            }

            @Override
            public void checkCaptcha(double sliderXMoved) {
                mViewModel.checkCaptcha(sliderXMoved, captchaGetIt.getToken(), captchaGetIt.getSecretKey());
            }
        });
        mBinding.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.btnGetCode.setEnabled(s.length() >= 11);
                updateRegisterButton();
                mBinding.ibtnDeletePhone.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateRegisterButton();
                mBinding.ibtnDeleteCode.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.ibtnDeleteCode.setOnClickListener(v -> mBinding.etCode.setText(""));
        mBinding.ibtnDeletePhone.setOnClickListener(v -> mBinding.etPhone.setText(""));
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
                return;
            }
            mViewModel.resetPayPassword(mBinding.etPhone.getText().toString(), mBinding.etCode.getText().toString(), mBinding.etPwd.getText().toString());
        });
        mBinding.btnGetCode.setOnClickListener(v -> onClick());
    }

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
        mViewModel.uc.resetPayPassword.observe(this, this::handleResetPayPass);
        mViewModel.uc.getVerifyCode.observe(this, o -> handleGetVerifyCode(o));
        mViewModel.uc.getVerificationImg.observe(this, this::handleGetVerifyImg);
        mViewModel.uc.checkItMutableLiveData.observe(this, this::handleCheckVerify);
    }

    private void handleCheckVerify(VerificationImgBaseResponse<CaptchaCheckIt> captchaCheckItVerificationImgBaseResponse) {
        if (captchaCheckItVerificationImgBaseResponse.isSuccess()) {
            blockPuzzleDialog.OK();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    blockPuzzleDialog.dismiss();
                }
            },1000);
            mViewModel.getVerifyCode(mBinding.etPhone.getText().toString(), "3");
        } else {
            blockPuzzleDialog.FAILED();
            mViewModel.getVerificationImg();
        }
    }

    private void handleGetVerifyImg(CaptchaGetIt captchaGetIt) {
        this.captchaGetIt = captchaGetIt;
        blockPuzzleDialog.init(captchaGetIt);
        blockPuzzleDialog.show();
    }


    private void handleResetPayPass(Object o) {
        showShortToast("修改成功");
        finish();
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

    private void updateRegisterButton() {
        mBinding.btnResetPassword.setEnabled(mBinding.etPhone.getText().length() >= 11 &&
                !TextUtils.isEmpty(mBinding.etPwd.getText()) &&
                !TextUtils.isEmpty(mBinding.etPwdConfirm.getText()) &&
                mBinding.etPwdConfirm.getText().length() == mBinding.etPwd.getText().length() &&
                mBinding.etPwd.length() == 6 &&
                !TextUtils.isEmpty(mBinding.etCode.getText().toString()) && mBinding.etCode.getText().toString().length() >= 4
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRegisterDisposable != null && mRegisterDisposable.isDisposed()) {
            mRegisterDisposable.dispose();
        }
    }

    private void onClick() {
        new com.adorkable.iosdialog.AlertDialog(ForgetPayPasswordActivity.this)
                .init()
                .setTitle("确认电话号码")
                .setMsg("我们将发送验证码到这个号码 \n" + mBinding.etPhone.getText().toString())
                .setPositiveButton("好", v1 -> {
                    mViewModel.getVerifyCode(mBinding.etPhone.getText().toString(), "3");
                })
                .setNegativeButton("取消", v12 -> {
                }).show();
    }
}
