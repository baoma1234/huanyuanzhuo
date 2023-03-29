package caixin.android.com.view.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityEditSignatureBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.UserInfoViewModel;

public class EditSignatureActivity extends BaseActivity<ActivityEditSignatureBinding, UserInfoViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_edit_signature;
    }

    @Override
    public UserInfoViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(UserInfoViewModel.class);
    }

    private String signature;

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.etSignature.setText(MMKVUtil.getUserInfo().getSignature());
        mBinding.tvComplete.setOnClickListener(v -> {
            if (mBinding.etSignature.getText().toString().equals(MMKVUtil.getUserInfo().getSignature())) {
                showShortToast(getResources().getString(R.string.input_new_signature));
                return;
            }
            signature = mBinding.etSignature.getText().toString();
            mViewModel.modifySignature(signature);
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.editSignature.observe(this, this::handlerEditSignature);
    }

    private void handlerEditSignature(Object o) {
        if (!TextUtils.isEmpty(signature)) {
            UserInfoEntity userResponse = MMKVUtil.getUserInfo();
            userResponse.setSignature(signature);
            MMKVUtil.setUserInfo(userResponse);
            showShortToast(getResources().getString(R.string.edit_signature_success));
            finish();
        }
    }
}
