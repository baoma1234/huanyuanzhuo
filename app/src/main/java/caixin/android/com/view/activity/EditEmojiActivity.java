package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityEditEmojiBinding;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.util.TextInfo;
import com.kongzue.dialog.v3.MessageDialog;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import caixin.android.com.adapter.EmojiEditAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.DeleteEmojiRequest;
import caixin.android.com.entity.EditEmojiRequest;
import caixin.android.com.entity.LikeEmojiEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.viewmodel.ChatViewModel;
import caixin.android.com.widget.friendcicle.GridDividerItemDecoration;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class EditEmojiActivity extends BaseActivity<ActivityEditEmojiBinding, ChatViewModel> {

    public static final int REQUEST_CODE = 2003;

    public static void navToForResult(Activity context, List<LikeEmojiEntity> likeEmojiEntities) {
        Intent intent = new Intent(context, EditEmojiActivity.class);
        if (likeEmojiEntities != null && likeEmojiEntities.size() > 0) {
            intent.putExtra(Extras.MINE_EMOJIS, (Serializable) likeEmojiEntities);
        }
        context.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_edit_emoji;
    }

    @Override
    public ChatViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ChatViewModel.class);
    }

    private EmojiEditAdapter mAdapter;
    private List<LikeEmojiEntity> likeEmojiEntities;
    private boolean isEditing = false;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void initData(Bundle savedInstanceState) {
        likeEmojiEntities = (List<LikeEmojiEntity>) getIntent().getSerializableExtra(Extras.MINE_EMOJIS);
        mBinding.ivClose.setOnClickListener(v -> onBackPressed());
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvEmojis.setLayoutManager(layoutManager);
        mAdapter = new EmojiEditAdapter(EditEmojiActivity.this, mBinding.rvEmojis);
        mAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.rvEmojis.setAdapter(mAdapter);
        mBinding.rvEmojis.addItemDecoration(new GridDividerItemDecoration(1, getResources().getColor(R.color.gray)));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isEditing) {
                mAdapter.setItemChecked(position);
                ArrayList<LikeEmojiEntity> selectList = mAdapter.getSelectedItems();
                if (selectList != null && selectList.size() > 0) {
                    mBinding.tvRemoveToFront.setEnabled(true);
                    mBinding.tvDelete.setEnabled(true);
                    mBinding.title.setText("已选择" + selectList.size() + "个表情");
                } else {
                    mBinding.tvRemoveToFront.setEnabled(false);
                    mBinding.tvDelete.setEnabled(false);
                    mBinding.title.setText("已选择0个表情");
                }
            } else {
                if (position == 0) {
                  /*  EasyPhotos.createAlbum(EditEmojiActivity.this, true, GlideEngine.getInstance())
                            .filter(Type.image())
                            .setGif(true)
                            .setPuzzleMenu(false)
                            .setCleanMenu(false)
                            .start(new SelectCallback() {
                                @Override
                                public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
                                    new Thread(() -> zipPic(paths)).start();
                                }
                            });*/
                }
            }
            mAdapter.notifyItemChanged(position);
        });
        if (likeEmojiEntities != null && likeEmojiEntities.size() > 0) {
            mAdapter.setNewData(likeEmojiEntities);
            mBinding.title.setText("添加的单个表情（" + (likeEmojiEntities.size() - 1) + "）");
        } else {
            showDialog();
            mViewModel.getLikeEmoji();
        }
        mBinding.tvEdit.setOnClickListener(v -> {
            isEditing = !isEditing;
            mAdapter.setIsEditing(isEditing, likeEmojiEntities);
            if (isEditing) {
                mBinding.title.setText("已选择0个表情");
                mBinding.tvEdit.setText("完成");
                mBinding.rlEdit.setVisibility(View.VISIBLE);
            } else {
                mBinding.title.setText("添加的单个表情（" + (likeEmojiEntities.size() - 1) + "）");
                mBinding.tvEdit.setText("整理");
                mBinding.rlEdit.setVisibility(View.GONE);
            }
        });
        mBinding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialog messageDialog = MessageDialog.build(EditEmojiActivity.this)
                        .setStyle(DialogSettings.STYLE.STYLE_IOS)
                        .setMessage(getResources().getString(R.string.emoji_confirm_delete))
                        .setOkButton(getResources().getString(R.string.delete), new OnDialogButtonClickListener() {
                            @Override
                            public boolean onClick(BaseDialog baseDialog, View v) {
                                deleteEmojis();
                                return false;
                            }
                        })
                        .setCancelButton(getResources().getString(R.string.cancel));
                TextInfo okTextInfo = new TextInfo();
                okTextInfo.setFontColor(getResources().getColor(R.color.red));
                messageDialog.setButtonPositiveTextInfo(okTextInfo);
                TextInfo messageTextInfo = new TextInfo();
                messageTextInfo.setFontSize(16);
                messageDialog.setMessageTextInfo(messageTextInfo);
                messageDialog.show();
            }
        });

        mBinding.tvRemoveToFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEmojis();
            }
        });
    }

    private ArrayList<LikeEmojiEntity> editEmojis = new ArrayList<>();

    private void editEmojis() {
        EditEmojiRequest editEmojiRequest = new EditEmojiRequest();
        editEmojiRequest.setToken(MMKVUtil.getToken());
        ArrayList<LikeEmojiEntity> selectList = mAdapter.getSelectedItems();
        if (selectList != null && selectList.size() > 0) {
            editEmojis.clear();
            editEmojis.addAll(likeEmojiEntities);
            editEmojis.removeAll(selectList);
            editEmojis.remove(0);
            editEmojis.addAll(0, selectList);
            ArrayList<Integer> selectListId = new ArrayList<>();
            for (int i = 0; i < editEmojis.size(); i++) {
                editEmojis.get(i).setSelected(false);
                selectListId.add(editEmojis.get(i).getId());
            }
            editEmojiRequest.setId(selectListId);
            Log.e(TAG, "editEmojis: " +editEmojiRequest.toString());
            mViewModel.editEmoji(editEmojiRequest);
        } else {
            isEditing = true;
            mAdapter.setIsEditing(true, likeEmojiEntities);
            mBinding.tvRemoveToFront.setEnabled(false);
            mBinding.tvDelete.setEnabled(false);
            mBinding.title.setText("已选择0个表情");
            mBinding.tvEdit.setText("完成");
            mBinding.rlEdit.setVisibility(View.VISIBLE);
        }

    }

    private void deleteEmojis() {
        DeleteEmojiRequest deleteEmojiRequest = new DeleteEmojiRequest();
        deleteEmojiRequest.setToken(MMKVUtil.getToken());
        ArrayList<LikeEmojiEntity> selectList = mAdapter.getSelectedItems();
        if (selectList != null && selectList.size() > 0) {
            ArrayList<Integer> selectListId = new ArrayList<>();
            for (int i = 0; i < selectList.size(); i++) {
                selectListId.add(selectList.get(i).getId());
            }
            deleteEmojiRequest.setId(selectListId);
            mViewModel.deleteEmoji(deleteEmojiRequest);
        } else {
            isEditing = true;
            mAdapter.setIsEditing(true, likeEmojiEntities);
            mBinding.tvRemoveToFront.setEnabled(false);
            mBinding.tvDelete.setEnabled(false);
            mBinding.title.setText("已选择0个表情");
            mBinding.tvEdit.setText("完成");
            mBinding.rlEdit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getLikeEmoji.observe(this, this::handleGetLikeEmoji);
        mViewModel.uc.publishEmojiPics.observe(this, this::handlePublishEmojis);
        mViewModel.uc.AddEmojis.observe(this, this::handleAddEmojis);
        mViewModel.uc.deleteEmoji.observe(this, this::handleDeleteEmojis);
        mViewModel.uc.editEmoji.observe(this, this::handleEditEmojis);
    }

    private void handleEditEmojis(Boolean aBoolean) {
        if (aBoolean) {
            likeEmojiEntities.clear();
            LikeEmojiEntity likeEmojiEntity = new LikeEmojiEntity();
            likeEmojiEntity.setLocalSrc(R.mipmap.add);
            likeEmojiEntities.add(likeEmojiEntity);
            likeEmojiEntities.addAll(editEmojis);
            isEditing = true;
            mAdapter.setIsEditing(true, likeEmojiEntities);
            mBinding.tvRemoveToFront.setEnabled(false);
            mBinding.tvDelete.setEnabled(false);
            mBinding.title.setText("已选择0个表情");
            mBinding.tvEdit.setText("完成");
            mBinding.rlEdit.setVisibility(View.VISIBLE);
        }
        editEmojis.clear();
    }

    private void handleDeleteEmojis(Object o) {
        likeEmojiEntities.removeAll(mAdapter.getSelectedItems());
        isEditing = true;
        mAdapter.setIsEditing(true, likeEmojiEntities);
        mBinding.tvRemoveToFront.setEnabled(false);
        mBinding.tvDelete.setEnabled(false);
        mBinding.title.setText("已选择0个表情");
        mBinding.tvEdit.setText("完成");
        mBinding.rlEdit.setVisibility(View.VISIBLE);
    }

    private static final String TAG = "EditEmojiActivity";

    private void handleAddEmojis(LikeEmojiEntity o) {
        likeEmojiEntities.add(1, o);
        mAdapter.setNewData(likeEmojiEntities);
        mBinding.title.setText("添加的单个表情（" + (likeEmojiEntities.size() - 1) + "）");
    }

    private void handlePublishEmojis(List<String> strings) {
        mViewModel.addEmoji(strings.get(0));
    }

    private void handleGetLikeEmoji(List<LikeEmojiEntity> o) {
        dismissDialog();
        if (likeEmojiEntities == null) {
            likeEmojiEntities = new ArrayList<>();
        }
        likeEmojiEntities.clear();
        LikeEmojiEntity likeEmojiEntity = new LikeEmojiEntity();
        likeEmojiEntity.setLocalSrc(R.mipmap.add);
        likeEmojiEntities.add(likeEmojiEntity);
        if (o != null && o.size() > 0) {
            likeEmojiEntities.addAll(o);
        }
        if (mAdapter != null) {
            mAdapter.setNewData(likeEmojiEntities);
            mBinding.rvEmojis.setAdapter(mAdapter);
        }
    }

    private void zipPic(List<String> list) {
        showDialog("上传中，请稍候...");
        Luban.with(this)
                .load(list)
                .ignoreBy(200)
                .setFocusAlpha(false)
                .filter(path -> !(TextUtils.isEmpty(path)))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        mViewModel.publishEmojiPictures(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }
                }).launch();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(Extras.MINE_EMOJIS, (Serializable) likeEmojiEntities);
        setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
        super.finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
