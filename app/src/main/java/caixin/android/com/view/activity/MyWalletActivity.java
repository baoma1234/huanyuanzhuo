package caixin.android.com.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityMyWalletBinding;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.MyWalletViewModel;

public class MyWalletActivity extends BaseActivity<ActivityMyWalletBinding, MyWalletViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_my_wallet;
    }

    @Override
    public MyWalletViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MyWalletViewModel.class);
    }

    private static final String TAG = "MyWalletActivity";

    @Override
    public void initData(Bundle savedInstanceState) {
        
        mBinding.titleBar.setLeftLayoutClickListener(v -> finish());
        mBinding.titleBar.setTitle("我的钱包").setTextColor(getResources().getColor(R.color.white));
        mBinding.tvExchange.setOnClickListener(v -> {
            UserInfoEntity userInfoEntity = MMKVUtil.getUserInfo();
            Log.e(TAG, "initData: " + userInfoEntity.toString());
            if (userInfoEntity.getSpay() == 0) {
                MessageDialog.build(this)
                        .setStyle(DialogSettings.STYLE.STYLE_IOS)
                        .setTitle("提示")
                        .setMessage("您还未设置过支付密码，暂不能进行余额兑换，是否现在去设置？")
                        .setOkButton("立即设置", new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                startActivity(SetPayPassActivity.class);
                                return false;
                            }
                        })
                        .setCancelButton("我再看看")
                        .show();
            } else {
                startActivity(MOMOExchangeActivity.class);
            }
        });
        mBinding.llMyMoneyRecode.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("money", "0.00");
            startActivity(MoneyRecodeActivity.class, bundle);
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getMoney.observe(this, this::handleGetMyMoney);
    }

    private void handleGetMyMoney(MyMoneyResponse o) {
        mBinding.tvMyMoney.setText("¥ " + o.getMoney());
        mBinding.llMyMoneyRecode.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("money", o.getMoney());
            startActivity(MoneyRecodeActivity.class, bundle);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getMyMoney();
    }
}
