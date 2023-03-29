package caixin.android.com.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityChatSettingBinding;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.ConversationDaoManager;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.viewmodel.UserInfoViewModel;

public class ChatSettingActivity extends BaseActivity<ActivityChatSettingBinding, UserInfoViewModel> {
    Intent finishIntent = new Intent();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_chat_setting;
    }

    @Override
    public UserInfoViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(UserInfoViewModel.class);
    }

    private int friendId;
    private String imgUrl;

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.title.setText("聊天设置");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            friendId = bundle.getInt(ChatRoomActivity.TAG_FRIEND, -1);
        }
        if (friendId == -1) {
            showShortToast("打开好友中心失败！");
            finish();
            return;
        }

        imgUrl = intent.getStringExtra(ChatRoomActivity.TAG_HEAD_IMAGE);
        ImgLoader.GlideLoadRoundedCorners(imgUrl, mBinding.ivAvatar, R.mipmap.default_avatar);
        mBinding.tvName.setText(intent.getStringExtra(ChatRoomActivity.TAG_TITLE));

        mBinding.cardDeleteFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog.build(ChatSettingActivity.this)
                        .setMessage(getResources().getString(R.string.delete_confirm))
                        .setOkButton(getResources().getString(R.string.confirm), new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                mViewModel.deleteFriend(friendId);
                                return false;
                            }
                        })
                        .setStyle(DialogSettings.STYLE.STYLE_IOS)
                        .setCancelButton(getResources().getString(R.string.cancel))
                        .show();
            }
        });

        mBinding.rlViewChatHistory.setOnClickListener(v -> {
            Bundle historyBundle = new Bundle();
            historyBundle.putInt("friendId", friendId);
            historyBundle.putInt("type", ChatRoomActivity.TYPE_FRIEND);
            startActivity(ChatHistoryListActivity.class, historyBundle);
        });

        mBinding.rlDeleteChatHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog.build(ChatSettingActivity.this)
                        .setMessage("您确定要清除聊天记录吗？")
                        .setOkButton(getResources().getString(R.string.confirm), new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                SendMessageResponse sendMessageResponse = ConversationDaoManager.getInstance().getByFriendId(friendId);
                                mViewModel.deleteConversation(friendId, 1, sendMessageResponse.getMessageId());

                                return false;
                            }
                        })
                        .setStyle(DialogSettings.STYLE.STYLE_IOS)
                        .setCancelButton(getResources().getString(R.string.cancel))
                        .show();
            }
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.deletFriend.observe(this, this::handleDeleteFriend);
        mViewModel.uc.deleteConversation.observe(this, this::handleDeleteConversation);
    }

    private void handleDeleteFriend(Object o) {
        showShortToast(getResources().getString(R.string.delete_success));
        FriendDaoManager.getInstance().deleteFriendById(friendId);
        ConversationDaoManager.getInstance().deleteByFriendID(friendId);
        finishIntent.putExtra("needFinish", true);
        setResult(RESULT_OK, finishIntent);
        finish();
    }

    private void handleDeleteConversation(Object o) {
        ConversationDaoManager.getInstance().deleteByFriendID(friendId);
        finishIntent.putExtra("needFinish", true);
        setResult(RESULT_OK, finishIntent);
        finish();
    }
}
