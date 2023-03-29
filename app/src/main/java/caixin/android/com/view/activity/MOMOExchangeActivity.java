package caixin.android.com.view.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityMomoexchangeBinding;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.entity.MyPlatformEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.viewmodel.MomoExchangeViewModel;
import caixin.android.com.widget.paypassword.PayDialog;

public class MOMOExchangeActivity extends BaseActivity<ActivityMomoexchangeBinding, MomoExchangeViewModel> {

    private void updateExchangeEnable() {
        mBinding.btnExchange.setEnabled(!TextUtils.isEmpty(mBinding.etMoney.getText()) &&
                !TextUtils.isEmpty(mBinding.etAccount.getText()) &&
                !TextUtils.isEmpty(mBinding.tvChoosePlatform.getText()));
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_momoexchange;
    }

    @Override
    public MomoExchangeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MomoExchangeViewModel.class);
    }

    private void handleExchangeMOMO(Boolean o) {
        if (o) {
            showShortToast("已成功提交兑换申请");
            finish();
        }
    }

    private MyPlatformEntity myPlatformEntity;

    private void handlePlatforms(MyPlatformEntity myPlatformEntity) {
        mBinding.tvChoosePlatform.setText(myPlatformEntity.getPtname());
        mBinding.etMoney.setHint("兑换金额不少于" + myPlatformEntity.getMin() + "元");
        mBinding.tvDescribe.setText(Html.fromHtml(myPlatformEntity.getDescribe()));
        this.myPlatformEntity = myPlatformEntity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.setLeftLayoutClickListener(v -> finish());
        mBinding.titleBar.setTitle("兑换彩金").setTextColor(getResources().getColor(R.color.white));
        mBinding.etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.ibtnDelAccount.setVisibility(!TextUtils.isEmpty(mBinding.etAccount.getText()) ? View.VISIBLE : View.GONE);
                updateExchangeEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateExchangeEnable();
                mBinding.ibtnDelMoney.setVisibility(!TextUtils.isEmpty(mBinding.etMoney.getText()) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (TextUtils.isEmpty(MMKVUtil.getUserInfo().getPt_account())) {
            MessageDialog.show(MOMOExchangeActivity.this, null, "首次成功兑换彩金的用户，系统会自动为您绑定您的平台账号！",
                    getResources().getString(R.string.confirm), "");
        } else {
            mBinding.etAccount.setText(MMKVUtil.getUserInfo().getPt_account());
            mBinding.etAccount.setEnabled(false);
            mBinding.ibtnDelAccount.setVisibility(View.GONE);
        }
        mBinding.ibtnDelMoney.setOnClickListener(v -> mBinding.etMoney.setText(""));
        mBinding.ibtnDelAccount.setOnClickListener(v -> mBinding.etAccount.setText(""));
        mBinding.tvExchangeRecord.setOnClickListener(v -> startActivity(MOMOExchangeRecordActivity.class));
        mBinding.btnExchange.setOnClickListener(v -> {
            if (mBinding.etAccount.getText().length() < 4) {
                showShortToast("平台账号不能少于四位");
            } else {
                PayDialog payDialog = new PayDialog(MOMOExchangeActivity.this, new PayDialog.FinishedPass() {
                    @Override
                    public void finishedpass(String pass) {
                        mViewModel.exchangeMOMO(mBinding.etMoney.getText().toString(), myPlatformEntity.getId() + "", mBinding.etAccount.getText().toString(), pass);
                    }
                });
                payDialog.show();
            }
        });

        mBinding.tvRegist.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mBinding.tvRegist.getPaint().setAntiAlias(true);//抗锯齿
       /* mBinding.tvRegist.setOnClickListener(v -> {
            if (TextUtils.isEmpty(myPlatformEntity.getUrl()))
                return;
            if (TextUtils.isEmpty(MMKVUtil.getUserInfo().getPt_account())) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(myPlatformEntity.getUrl());
                intent.setData(content_url);
                startActivity(intent);
            } else {
                MessageDialog.build(this)
                        .setTitle("提示")
                        .setMessage("您已绑定平台账号，请务重复申请注册!")
                        .setStyle(DialogSettings.STYLE.STYLE_IOS)
                        .setOkButton("知道了")
                        .show();
            }
        });*/
        mViewModel.getMyMoney();
        mViewModel.getPlatforms();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.cashOut.observe(this, this::handleExchangeMOMO);
        mViewModel.uc.mPlatformsLiveData.observe(this, this::handlePlatforms);
        mViewModel.uc.getMoney.observe(this, this::handleGetMyMoney);
    }

    private void handleGetMyMoney(MyMoneyResponse myMoneyResponse) {
        mBinding.tvEnableMomo.setText(myMoneyResponse.getMoney());
    }
}