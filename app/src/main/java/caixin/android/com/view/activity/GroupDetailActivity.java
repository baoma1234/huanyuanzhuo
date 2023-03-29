package caixin.android.com.view.activity;

import static caixin.android.com.view.activity.ChatRoomActivity.TAG_IS_NO_TALK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityGroupDetailBinding;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import caixin.android.com.adapter.GroupMemberAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.ConversationDaoManager;
import caixin.android.com.daomanager.GroupDaoManager;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.viewmodel.ContactViewModel;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class GroupDetailActivity extends BaseActivity<ActivityGroupDetailBinding, ContactViewModel> {

    private static final int REQUEST_CODE = 1000;
    private static final int EDIT_GROUP_NAME_REQUEST_CODE = 1001;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_group_detail;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private GroupMemberAdapter groupMemberAdapter;
    private GroupMemberAdapter groupOwnManagerAdapter;
    private int groupId = -1;
    private boolean isShowOwnAndManager = false;
    private boolean isShowMembers = false;
    private int messageId;
    private boolean isNoTalk = false;
    private String groupName;
    private String groupImg;

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            groupId = bundle.getInt(ChatRoomActivity.TAG_GROUP, -1);
            messageId = bundle.getInt("messageId", -1);
            isNoTalk = bundle.getBoolean(TAG_IS_NO_TALK);
        }
        if (groupId == -1) {
            showShortToast("打开群列表错误，请联系客服！");
            finish();
            return;
        }
        groupImg = intent.getStringExtra(ChatRoomActivity.TAG_HEAD_IMAGE);
        ImgLoader.GlideLoadRoundedCorners(groupImg, mBinding.ivGroupAvatar, R.mipmap.default_avatar);
        if (isNoTalk) {
            mBinding.switchIsNoTalk.setChecked(true);
        } else {
            mBinding.switchIsNoTalk.setChecked(false);
        }

        mBinding.switchIsNoTalk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isNoTalk) {
                    mViewModel.setGroupNoTalk(1, groupId);
                } else {
                    mViewModel.setGroupNoTalk(2, groupId);
                }
            }
        });
        groupName = intent.getStringExtra(ChatRoomActivity.TAG_TITLE);
        mBinding.tvGroupName.setText("群组名称： " + groupName);
        mBinding.titleBar.title.setText(getResources().getString(R.string.group_info));
        mBinding.titleBar.ivClose.setOnClickListener(v -> finish());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvGroupMember.setLayoutManager(layoutManager);
        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 5);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvGroupOwnManager.setLayoutManager(layoutManager2);
        mBinding.rlGroupMember.setOnClickListener(v -> changeGroupMemberView());
        mBinding.rlGroupOwnManager.setOnClickListener(v -> changeGroupOwnManagerView());
        mBinding.rlAddGroupMember.setOnClickListener(v -> {
            Bundle addBundle = new Bundle();
            addBundle.putInt(ChatRoomActivity.TAG_GROUP, groupId);
            startActivity(AddMemberActivity.class, addBundle);
        });
        mBinding.rlRemoveGroupMember.setOnClickListener(v -> {
            Bundle addBundle = new Bundle();
            addBundle.putInt(ChatRoomActivity.TAG_GROUP, groupId);
            startActivity(DeleteMembersActivity.class, addBundle);
        });
        mBinding.tvLeaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog.build(GroupDetailActivity.this)
                        .setStyle(DialogSettings.STYLE.STYLE_IOS)
                        .setMessage(getResources().getString(R.string.is_leave_group))
                        .setOkButton(getResources().getString(R.string.confirm), new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                mViewModel.leaveGroup(groupId);
                                return false;
                            }
                        })
                        .setCancelButton(getResources().getString(R.string.cancel))
                        .show();
            }
        });
        groupMemberAdapter = new GroupMemberAdapter(this);
        groupOwnManagerAdapter = new GroupMemberAdapter(this);
        mBinding.rvGroupOwnManager.setAdapter(groupOwnManagerAdapter);
        mBinding.rvGroupMember.setAdapter(groupMemberAdapter);
        mBinding.rlFindChatHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle historyBundle = new Bundle();
                historyBundle.putInt("groupId", groupId);
                historyBundle.putInt("type", ChatRoomActivity.TYPE_GROUP);
                startActivity(ChatHistoryListActivity.class, historyBundle);
            }
        });
        mBinding.rlClearChatHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog.build(GroupDetailActivity.this)
                        .setStyle(DialogSettings.STYLE.STYLE_IOS)
                        .setMessage(getResources().getString(R.string.if_clean_chat_history))
                        .setOkButton(getResources().getString(R.string.confirm), new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                mViewModel.deleteConversation(groupId, 2, messageId);
                                return false;
                            }
                        })
                        .setCancelButton(getResources().getString(R.string.cancel))
                        .show();
            }
        });
        mBinding.rlGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle editBundle = new Bundle();
                editBundle.putInt(ChatRoomActivity.TAG_GROUP, groupId);
                editBundle.putString(ChatRoomActivity.TAG_TITLE, groupName);
                startActivityForResult(EditGroupNameActivity.class, editBundle, EDIT_GROUP_NAME_REQUEST_CODE);
            }
        });
        mBinding.rlGroupAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   EasyPhotos.createAlbum(GroupDetailActivity.this, false, GlideEngine.getInstance())
                        .filter(Type.image())
                        .setGif(false)
                        .isCrop(true)
                        .setPuzzleMenu(false)
                        .setCleanMenu(false)
                        .start(new SelectCallback() {
                            @Override
                            public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
                                new Thread(() -> zipPic(paths)).start();
                            }
                        });*/
            }
        });
        showDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getGroupMember(groupId);
    }


    private void zipPic(List<String> list) {
        Luban.with(this)
                .load(list)
                .ignoreBy(100)
                .setFocusAlpha(false)
                .filter(path -> !(TextUtils.isEmpty(path)))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(file.getAbsolutePath(), options); // 此时返回的bitmap为null
                        groupImg = file.getAbsolutePath();
                        mViewModel.publishPicture(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }
                }).launch();
    }


    private void changeGroupMemberView() {
        if (isShowMembers) {
            mBinding.rvGroupMember.setVisibility(View.GONE);
            mBinding.ivGroupMemberRight.setImageResource(R.mipmap.jiantou_down);
        } else {
            mBinding.rvGroupMember.setVisibility(View.VISIBLE);
            mBinding.ivGroupMemberRight.setImageResource(R.mipmap.jiantou_up);
        }
        isShowMembers = !isShowMembers;
    }


    private void changeGroupOwnManagerView() {
        if (isShowOwnAndManager) {
            mBinding.rvGroupOwnManager.setVisibility(View.GONE);
            mBinding.ivGroupOwnManagerRight.setImageResource(R.mipmap.jiantou_down);
        } else {
            mBinding.rvGroupOwnManager.setVisibility(View.VISIBLE);
            mBinding.ivGroupOwnManagerRight.setImageResource(R.mipmap.jiantou_up);
        }
        isShowOwnAndManager = !isShowOwnAndManager;
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getGroupMember.observe(this, this::handleGetGroupMember);
        mViewModel.uc.leaveGroup.observe(this, this::handleLeaveGroup);
        mViewModel.uc.deleteConversation.observe(this, this::handleDeleteConversation);
        mViewModel.uc.setGroupNoTalk.observe(this, this::handleSetGroupNoTalk);
        mViewModel.uc.publishImg.observe(this, this::handlePublishImg);
        mViewModel.uc.editGroupAvatar.observe(this, this::handleEditGroupAvatar);
    }

    private void handleEditGroupAvatar(Object o) {
        ImgLoader.GlideLoadRoundedCorners(groupImg, mBinding.ivGroupAvatar, R.mipmap.default_avatar);
        finishIntent.putExtra(ChatRoomActivity.TAG_HEAD_IMAGE, groupImg);
        setResult(RESULT_OK, finishIntent);
    }

    private void handlePublishImg(List<String> strings) {
        mViewModel.editGroupAvatar(strings.get(0), groupId);
    }

    private void handleSetGroupNoTalk(Boolean o) {
        if (o) {
            isNoTalk = !isNoTalk;
            if (isNoTalk) {
                showShortToast("设置禁言成功");
            } else {
                showShortToast("解除禁言成功");
            }
        } else {
            mBinding.switchIsNoTalk.setOnCheckedChangeListener(null);
            if (isNoTalk) {
                mBinding.switchIsNoTalk.setChecked(true);
            } else {
                mBinding.switchIsNoTalk.setChecked(false);
            }
            mBinding.switchIsNoTalk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isNoTalk) {
                        mViewModel.setGroupNoTalk(1, groupId);
                    } else {
                        mViewModel.setGroupNoTalk(2, groupId);
                    }
                }
            });
        }
    }

    Intent finishIntent = new Intent();

    private void handleDeleteConversation(Object o) {
        showShortToast(getResources().getString(R.string.delete_success));
        ConversationDaoManager.getInstance().deleteByGroupId(groupId);
        finishIntent.putExtra("isLeaveGroup", true);
        setResult(RESULT_OK, finishIntent);
        finish();
    }

    private void handleLeaveGroup(Object o) {
        GroupDaoManager.getInstance().deleteByGroupId(groupId);
        ConversationDaoManager.getInstance().deleteByGroupId(groupId);
        //把返回数据存入Intent
        finishIntent.putExtra("isLeaveGroup", true);
        setResult(RESULT_OK, finishIntent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void handleGetGroupMember(List<MemberEntity> memberEntities) {
        if (memberEntities == null || memberEntities.size() == 0)
            return;
        List<MemberEntity> members = new ArrayList<>();
        List<MemberEntity> ownManager = new ArrayList<>();
        boolean isManager = false;
        mBinding.llEditManager.setVisibility(View.GONE);
        for (MemberEntity item : memberEntities) {
            if (item.getManager() == MemberEntity.MANAGER_MANAGER || item.getManager() == MemberEntity.MANAGER_BOSS) {
                ownManager.add(item);
                if (item.getId() == MMKVUtil.getUserInfo().getId()) {
                    isManager = true;
                    if (item.getManager() == MemberEntity.MANAGER_BOSS) {
                        mBinding.llEditManager.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                members.add(item);
            }
        }

        mBinding.rlAddGroupManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ChatRoomActivity.TAG_GROUP, groupId);
                bundle.putSerializable(AddGroupManagerActivity.DATAS, (Serializable) members);
                startActivity(AddGroupManagerActivity.class,bundle);
            }
        });
        mBinding.rlDeleteManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ChatRoomActivity.TAG_GROUP, groupId);
                bundle.putSerializable(DeleteGroupManagerActivity.DATAS, (Serializable) ownManager);
                startActivity(DeleteGroupManagerActivity.class,bundle);
            }
        });

        if (isManager) {
            mBinding.rlIsNoTalk.setVisibility(View.VISIBLE);
            mBinding.llEditMember.setVisibility(View.VISIBLE);
            mBinding.rlGroupName.setClickable(true);
            mBinding.rlGroupAvatar.setClickable(true);
        } else {
            mBinding.llEditMember.setVisibility(View.GONE);
            mBinding.rlIsNoTalk.setVisibility(View.GONE);
            mBinding.rlGroupAvatar.setClickable(false);
            mBinding.rlGroupName.setClickable(false);
        }
        groupMemberAdapter.setNewData(members);
        groupOwnManagerAdapter.setNewData(ownManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    finish();
                    break;
                case EDIT_GROUP_NAME_REQUEST_CODE:
                    groupName = data.getStringExtra(ChatRoomActivity.TAG_TITLE);
                    mBinding.tvGroupName.setText("群组名称： " + groupName);
                    finishIntent.putExtra(ChatRoomActivity.TAG_TITLE, groupName);
                    setResult(RESULT_OK, finishIntent);
                    break;
            }
        }
    }

}
