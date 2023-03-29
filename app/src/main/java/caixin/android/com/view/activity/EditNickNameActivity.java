package caixin.android.com.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityEditNicknameBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.UserInfoViewModel;

public class EditNickNameActivity extends BaseActivity<ActivityEditNicknameBinding, UserInfoViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_edit_nickname;
    }

    @Override
    public UserInfoViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(UserInfoViewModel.class);
    }

    private String nickName;

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.etNickname.setText(MMKVUtil.getUserInfo().getNikeName());
        mBinding.tvComplete.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(mBinding.etNickname.getText())) {
                if (mBinding.etNickname.getText().length()<2){
                    showShortToast(getResources().getString(R.string.input_nick_name_more_than_two));
                }
                if (mBinding.etNickname.getText().toString().equals(MMKVUtil.getUserInfo().getNikeName())) {
                    showShortToast(getResources().getString(R.string.input_new_nick_name));
                    return;
                }
                nickName = mBinding.etNickname.getText().toString();
                mViewModel.modifyNickName(MMKVUtil.getUserInfo().getId(), nickName);
            } else {
                showShortToast(getResources().getString(R.string.no_input_nick_name));
            }
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.editNickName.observe(this, this::handlerEditNickName);
    }

    private void handlerEditNickName(Object o) {
        if (!TextUtils.isEmpty(nickName)) {
            UserInfoEntity userResponse = MMKVUtil.getUserInfo();
            userResponse.setNikeName(nickName);
            MMKVUtil.setUserInfo(userResponse);
            showShortToast(getResources().getString(R.string.edit_nick_name_success));
            finish();
        }
    }
}
