package caixin.android.com.view.activity;

import static caixin.android.com.view.activity.AddFriendActivity.TAG_FRIEND;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityEditNicknameBinding;
import com.caixin.huanyu.databinding.ActivitySearchBinding;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.viewmodel.SearchUserViewModel;
import caixin.android.com.viewmodel.UserInfoViewModel;

public class SearchUserActivity extends BaseActivity<ActivitySearchBinding, SearchUserViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public SearchUserViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SearchUserViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
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
                        MessageDialog.build(SearchUserActivity.this)
                                .setStyle(DialogSettings.STYLE.STYLE_IOS)
                                .setTitle(getResources().getString(R.string.prompt))
                                .setOkButton(getResources().getString(R.string.confirm))
                                .setMessage(getResources().getString(R.string.he_is_in_your_black_list))
                                .show();
                    } else {
                        MessageDialog.build(SearchUserActivity.this)
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
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.searchFriend.observe(this, this::handleSearchFriend);
    }

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
