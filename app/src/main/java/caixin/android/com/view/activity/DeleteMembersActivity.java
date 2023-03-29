package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityDeleteMembersBinding;

import caixin.android.com.adapter.DeleteMemberAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.viewmodel.DeleteMemberViewModel;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.utils.MMKVUtil;

import java.util.List;


public class DeleteMembersActivity extends BaseActivity<ActivityDeleteMembersBinding, DeleteMemberViewModel> {
    private int groupId = -1;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_delete_members;
    }

    @Override
    public DeleteMemberViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(DeleteMemberViewModel.class);
    }

    private DeleteMemberAdapter deleteMemberAdapter;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
            groupId = bundle.getInt(ChatRoomActivity.TAG_GROUP, -1);
        mBinding.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.buttonConfirm.setOnClickListener(v -> {
            StringBuilder group_uids = new StringBuilder();
            for (MemberEntity item : deleteMemberAdapter.getSelectedItem()) {
                group_uids.append(item.getId()).append(",");
            }
            mViewModel.deleteMembers(groupId, group_uids.toString());
        });

        mBinding.rvVerticalContact.setLayoutManager(new LinearLayoutManager(this));
        deleteMemberAdapter = new DeleteMemberAdapter(this, mBinding.rvVerticalContact);
        deleteMemberAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.rvVerticalContact.setAdapter(deleteMemberAdapter);
        deleteMemberAdapter.setOnItemClickListener((adapter, view, position) -> {
            mBinding.searchBarView.query.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mBinding.searchBarView.query.getWindowToken(), 0);
            if (((MemberEntity) adapter.getData().get(position)).isSelected()) {
                deleteMemberAdapter.setItemChecked((MemberEntity) adapter.getData().get(position), false);
            } else {
                deleteMemberAdapter.setItemChecked((MemberEntity) adapter.getData().get(position), true);
            }
            deleteMemberAdapter.notifyItemChanged(position);
            if (deleteMemberAdapter.getSelectedItem().size() > 0) {
                mBinding.buttonConfirm.setEnabled(true);
                mBinding.buttonConfirm.setText("删除(" + deleteMemberAdapter.getSelectedItem().size() + ")");
            } else {
                mBinding.buttonConfirm.setEnabled(false);
                mBinding.buttonConfirm.setText("删除");
            }
        });

        mBinding.rvVerticalContact.setOnTouchListener((v, event) -> {
            mBinding.searchBarView.query.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mBinding.searchBarView.query.getWindowToken(), 0);
            return false;
        });

        mBinding.searchBarView.query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deleteMemberAdapter.getFilter().filter(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        mViewModel.init(groupId);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.init.observe(this, this::handleInit);
        mViewModel.uc.delete.observe(this,this::handleDelete);
    }

    private void handleDelete(Object o) {
        showShortToast("删除成功");
        //设置返回数据
        this.setResult(RESULT_OK);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleInit(List<MemberEntity> memberEntities) {
        mBinding.title.setText("聊天成员("+memberEntities.size()+")");
        memberEntities.removeIf(memberEntity -> {
            if (memberEntity.getId() == MMKVUtil.getUserInfo().getId()) {
                return true;
            }
            return false;
        });
        deleteMemberAdapter.setNewData(memberEntities);
    }
}
