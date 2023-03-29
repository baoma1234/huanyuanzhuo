/*
package caixin.android.com.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityFriendInfoBinding;
import com.caixin.huanyu.databinding.ActivityMultipleSendBinding;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.daomanager.GroupDaoManager;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.GroupEntity;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.FriendInfoViewModel;
import caixin.android.com.viewmodel.HomeViewModel;
import caixin.android.com.widget.EditNoteDialog;

import static caixin.android.com.view.activity.ChatRoomActivity.TAG_FRIEND;

public class MultipleSendActivity extends BaseActivity<ActivityMultipleSendBinding, HomeViewModel> {

    public static final int TYPE_FRIEND = 0;
    public static final int TYPE_GROUP = TYPE_FRIEND + 1;
    public static final int TYPE_FRIEND_AND_GROUP = TYPE_GROUP + 1;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_multiple_send;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    private List<FriendEntity> friendEntities = new ArrayList<>();
    private List<GroupEntity> groupEntities = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {
        
        mBinding.ivClose.setOnClickListener(v -> finish());
        friendEntities = FriendDaoManager.getInstance().searchAll();
        mBinding.tvChooseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.cbGroup.setChecked(true);
                mBinding.cbFriend.setChecked(true);
            }
        });
        groupEntities = GroupDaoManager.getInstance().searchAll();
        if (!friendEntities.isEmpty()) {
            mBinding.rlFriend.setVisibility(View.VISIBLE);
            String friends = String.format(getString(R.string.my_friends_number), String.valueOf(friendEntities.size()));
            mBinding.tvFriend.setText(friends);
        } else {
            mBinding.rlFriend.setVisibility(View.GONE);
        }
        if (!groupEntities.isEmpty()) {
            mBinding.rlGroup.setVisibility(View.VISIBLE);
            String groups = String.format(getString(R.string.my_groups_number), String.valueOf(groupEntities.size()));
            mBinding.tvGroup.setText(groups);
        } else {
            mBinding.rlGroup.setVisibility(View.GONE);
        }

        mBinding.rlGroup.setOnClickListener(v -> mBinding.cbGroup.setChecked(!mBinding.cbGroup.isChecked()));

        mBinding.rlFriend.setOnClickListener(v -> mBinding.cbFriend.setChecked(!mBinding.cbFriend.isChecked()));

        mBinding.tvConfirm.setOnClickListener(v -> {
            int type;
            if (mBinding.cbFriend.isChecked() && mBinding.cbGroup.isChecked()) {
                type = TYPE_FRIEND_AND_GROUP;
            } else if (mBinding.cbFriend.isChecked()) {
                type = TYPE_FRIEND;
            } else if (mBinding.cbGroup.isChecked()) {
                type = TYPE_GROUP;
            } else {
                showShortToast(getResources().getString(R.string.at_least_choose_one));
                return;
            }
            Intent intent = new Intent(MultipleSendActivity.this, MultipleSendChatRoomActivity.class);
            intent.putExtra("type", type);
            startActivity(intent);
        });

        MessageDialog.build(this)
                .setStyle(DialogSettings.STYLE.STYLE_IOS)
                .setMessage(getResources().getString(R.string.multiple_send_need_money))
                .setOkButton(getResources().getString(R.string.confirm), new OnDialogButtonClickListener() {
                    @Override
                    public boolean onClick(BaseDialog baseDialog, View v) {
                        return false;
                    }
                }).show();
    }


    @Override
    public void initViewObservable() {
    }


}
*/
