package caixin.android.com.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityFriendInfoBinding;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.dao.FriendEntityDao;
import caixin.android.com.daomanager.ConversationDaoManager;
import caixin.android.com.entity.BlackFriendResponse;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.FriendInfoViewModel;
import caixin.android.com.widget.EditNoteDialog;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.ImgLoader;


import static caixin.android.com.view.activity.ChatRoomActivity.TAG_FRIEND;

public class FriendInfoActivity extends BaseActivity<ActivityFriendInfoBinding, FriendInfoViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_friend_info;
    }

    @Override
    public FriendInfoViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(FriendInfoViewModel.class);
    }

    private FriendEntity friendEntity;

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        StatusBarUtils.immersive(this, this.getResources().getColor(R.color.white));

        friendEntity = (FriendEntity) intent.getSerializableExtra(TAG_FRIEND);
        ImgLoader.GlideLoadCircle(mBinding.ivAvatar, friendEntity.getImg(), R.mipmap.img_user_head);
        if (TextUtils.isEmpty(friendEntity.getTag())) {
            mBinding.userNickname.setText(friendEntity.getNikeName());
        } else {
            mBinding.userNickname.setText(friendEntity.getTag());
        }
        mBinding.rlFindChatHistory.setOnClickListener(v -> {
            Bundle historyBundle = new Bundle();
            historyBundle.putInt("friendId", (int) friendEntity.getId());
            historyBundle.putInt("type", ChatRoomActivity.TYPE_FRIEND);
            startActivity(ChatHistoryListActivity.class, historyBundle);
        });
        mBinding.titleBar.title.setText(getResources().getString(R.string.friend_info));
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.rlInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditNote();
            }
        });
        mBinding.btnLogout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(ChatRoomActivity.CHATROOM_TYPE, ChatRoomActivity.TYPE_FRIEND);
            bundle.putInt(ChatRoomActivity.TAG_FRIEND, (int) friendEntity.getId());
            bundle.putString(ChatRoomActivity.TAG_TITLE, friendEntity.getNikeName());
            bundle.putString(ChatRoomActivity.TAG_HEAD_IMAGE, friendEntity.getImg());
            startActivity(ChatRoomActivity.class, bundle);
            finish();
        });

        if (friendEntity.getIsBlack() == 2) {
            mBinding.switchAddBlack.setChecked(true);
        } else {
            mBinding.switchAddBlack.setChecked(false);
        }

        mBinding.switchAddBlack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mViewModel.blackFriend((int) friendEntity.getId());
            }
        });

        mBinding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog.build(FriendInfoActivity.this)
                        .setMessage(getResources().getString(R.string.delete_confirm))
                        .setOkButton(getResources().getString(R.string.confirm), new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                mViewModel.deleteFriend((int) friendEntity.getId());
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
        mViewModel.uc.modifyNote.observe(this, this::handleModify);
        mViewModel.uc.blackFriend.observe(this, this::handleBlackFriend);
        mViewModel.uc.deletFriend.observe(this, this::handleDeleteFriend);
    }

    private void handleDeleteFriend(Object o) {
        showShortToast(getResources().getString(R.string.delete_success));
        FriendDaoManager.getInstance().deleteFriend(friendEntity);
        ConversationDaoManager.getInstance().deleteByFriend(friendEntity);
        finish();
    }

    private void handleBlackFriend(BlackFriendResponse o) {
        if (o.getAct().equals(BlackFriendResponse.ADD_BLACK_FRINED)) {
            showShortToast(getResources().getString(R.string.add_black_friend_success));
            friendEntity.setIsBlack(2);
            FriendDaoManager.getInstance().insertOrReplace(friendEntity);
        } else {
            showShortToast(getResources().getString(R.string.remove_black_friend_success));
            friendEntity.setIsBlack(1);
            ConversationDaoManager.getInstance().deleteByFriend(friendEntity);
            FriendDaoManager.getInstance().insertOrReplace(friendEntity);
        }
    }

    private void handleModify(Object o) {
        friendEntity.setTag((String) o);
        if (TextUtils.isEmpty(friendEntity.getTag())) {
            mBinding.userNickname.setText(friendEntity.getNikeName());
        } else {
            mBinding.userNickname.setText(friendEntity.getTag());
        }
        FriendDaoManager.getInstance().insertOrReplace(friendEntity);
    }

    private void showEditNote() {
        EditNoteDialog editNoteDialog = new EditNoteDialog(FriendInfoActivity.this);
        editNoteDialog.setTitle("修改昵称");
        editNoteDialog.setYesOnclickListener("确定", new EditNoteDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String note) {
                editNoteDialog.dismiss();
                mViewModel.modifyNote((int) friendEntity.getId(), note);
            }
        });
        editNoteDialog.setNoOnclickListener("取消", new EditNoteDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                editNoteDialog.dismiss();
            }
        });
        editNoteDialog.show();
    }
}
