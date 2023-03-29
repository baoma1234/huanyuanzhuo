package caixin.android.com.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityAddFriendBinding;
import com.caixin.huanyu.databinding.ActivityEditGroupNameBinding;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.ContactViewModel;

public class EditGroupNameActivity extends BaseActivity<ActivityEditGroupNameBinding, ContactViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_edit_group_name;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private int groupID = -1;

    @Override
    public void initData(Bundle savedInstanceState) {
        
        groupID = getIntent().getIntExtra(ChatRoomActivity.TAG_GROUP, -1);
        if (groupID == -1)
            finish();
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.etName.setHint(getIntent().getStringExtra(ChatRoomActivity.TAG_TITLE));
        mBinding.tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mBinding.etName.getText().toString())) {
                    showShortToast("群名称不能为空");
                    return;
                }
                mViewModel.editGroupName(mBinding.etName.getText().toString(), groupID);
            }
        });

    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.editGroupName.observe(this, this::handleEditGroupName);
    }

    private void handleEditGroupName(Object o) {
        showShortToast("修改成功");
        Intent intent = new Intent();
        intent.putExtra(ChatRoomActivity.TAG_TITLE, mBinding.etName.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    public static final String TAG_FRIEND = "friend";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
