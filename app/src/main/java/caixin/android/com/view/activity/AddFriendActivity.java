package caixin.android.com.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityAddFriendBinding;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.viewmodel.ContactViewModel;

public class AddFriendActivity extends BaseActivity<ActivityAddFriendBinding, ContactViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_friend;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.tvSearchComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.etSearch.getText().toString())) {
                    showShortToast("搜索不能为空");
                    return;
                }
                FriendEntity friendEntity = FriendDaoManager.getInstance().searchByAccount(mBinding.etSearch.getText().toString());
                if (friendEntity != null) {
                    if (friendEntity.getIsBlack() == 2) {
                        MessageDialog.build(AddFriendActivity.this)
                                .setStyle(DialogSettings.STYLE.STYLE_IOS)
                                .setTitle(getResources().getString(R.string.prompt))
                                .setOkButton(getResources().getString(R.string.confirm))
                                .setMessage(getResources().getString(R.string.he_is_in_your_black_list))
                                .show();
                    } else {
                        MessageDialog.build(AddFriendActivity.this)
                                .setStyle(DialogSettings.STYLE.STYLE_IOS)
                                .setTitle(getResources().getString(R.string.prompt))
                                .setOkButton(getResources().getString(R.string.confirm))
                                .setMessage(getResources().getString(R.string.already_exist_friend))
                                .show();
                    }
                } else {
                    mViewModel.searchFriend(mBinding.etSearch.getText().toString());
                }
            }
        });

        mBinding.rlQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MyQRCodeActivity.class);
            }
        });

        mBinding.rlScanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ScanActivity.class);
            }
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.searchFriend.observe(this, this::handleSearchFriend);
    }

    public static final String TAG_FRIEND = "friend";

    private void handleSearchFriend(FriendEntity friendEntity) {
        if (friendEntity == null) {
            showShortToast("搜错出错，请联系客服！");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG_FRIEND, friendEntity);
        startActivity(NewFriendActivity.class, bundle);
        finish();
    }
}
