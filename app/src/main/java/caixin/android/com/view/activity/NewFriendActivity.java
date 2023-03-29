package caixin.android.com.view.activity;

import static caixin.android.com.view.activity.AddFriendActivity.TAG_FRIEND;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityNewFriendBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.viewmodel.ContactViewModel;


public class NewFriendActivity extends BaseActivity<ActivityNewFriendBinding, ContactViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_new_friend;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private FriendEntity friendEntity;

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        
        friendEntity = (FriendEntity) intent.getSerializableExtra(TAG_FRIEND);
        if (friendEntity == null) {
            finish();
        }
        ImgLoader.GlideLoadCircle(mBinding.ivAvatar, friendEntity.getImg(), R.mipmap.img_user_head);
        mBinding.userNickname.setText(friendEntity.getNikeName());
        mBinding.tvUseAccount.setText(friendEntity.getMobile());
        mBinding.tvAddAsFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.addFriend(friendEntity.getMobile());
            }
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.addFriend.observe(this, this::handleAddFriend);
    }

    private void handleAddFriend(Object o) {
        showShortToast("添加成功，请等待对方回应！");
        finish();
    }
}
