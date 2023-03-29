package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityGroupMembersBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import caixin.android.com.adapter.GroupMemberAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.viewmodel.GroupMembersViewModel;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.utils.MMKVUtil;

import java.util.List;


public class GroupMembersActivity extends BaseActivity<ActivityGroupMembersBinding, GroupMembersViewModel> {

    private static final int REQUEST_CODE = 1000;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_group_members;
    }

    @Override
    public GroupMembersViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(GroupMembersViewModel.class);
    }

    private GroupMemberAdapter groupMemberAdapter;
    private int groupId = -1;

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
            groupId = bundle.getInt(ChatRoomActivity.TAG_GROUP, -1);
        if (groupId == -1) {
            showShortToast("打开群列表错误，请联系客服！");
            finish();
            return;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mBinding.rvContact.setLayoutManager(layoutManager);
        groupMemberAdapter = new GroupMemberAdapter(this);
        mBinding.rvContact.setAdapter(groupMemberAdapter);
        mBinding.titleBar.title.setText("群成员");
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.searchBarView.query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                groupMemberAdapter.getFilter().filter(s);
                if (s.length() > 0) {
                    mBinding.searchBarView.searchClear.setVisibility(View.VISIBLE);
                } else {
                    mBinding.searchBarView.searchClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.searchBarView.searchClear.setOnClickListener(v -> mBinding.searchBarView.query.getText().clear());
        groupMemberAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (((MemberEntity) adapter.getData().get(position)).getItemType() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ChatRoomActivity.TAG_GROUP, groupId);
                    startActivityForResult(AddMemberActivity.class, bundle, REQUEST_CODE);
                } else if (((MemberEntity) adapter.getData().get(position)).getItemType() == 2) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ChatRoomActivity.TAG_GROUP, groupId);
                    startActivityForResult(DeleteMembersActivity.class, bundle, REQUEST_CODE);
                }
            }
        });
        mViewModel.init(groupId);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.init.observe(this, this::handleInit);
    }

    @SuppressLint("SetTextI18n")
    private void handleInit(List<MemberEntity> memberEntities) {
        if (memberEntities == null || memberEntities.size() == 0)
            return;
        mBinding.titleBar.title.setText("群成员" + memberEntities.size());
        MemberEntity addMemberEntity = new MemberEntity();
        MemberEntity subtractMemberEntity = null;
        addMemberEntity.setItemType(1);
        memberEntities.add(addMemberEntity);
        for (MemberEntity item : memberEntities) {
            if (item.getManager() == MemberEntity.MANAGER_MANAGER || item.getManager() == MemberEntity.MANAGER_BOSS) {
                if (item.getId() == MMKVUtil.getUserInfo().getId()) {
                    subtractMemberEntity = new MemberEntity();
                    subtractMemberEntity.setItemType(2);
                    break;
                }
            }
        }
        if (subtractMemberEntity != null)
            memberEntities.add(subtractMemberEntity);
        groupMemberAdapter.setNewData(memberEntities);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            finish();
        }
    }
}
