package caixin.android.com.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityAddMemberBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import caixin.android.com.adapter.AddMemberHorizontalAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.adapter.AddMemberVerticalAdapter;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.TextUtils;
import caixin.android.com.viewmodel.AddMemberViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class AddMemberActivity extends BaseActivity<ActivityAddMemberBinding, AddMemberViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_add_member;
    }

    @Override
    public AddMemberViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AddMemberViewModel.class);
    }

    private AddMemberVerticalAdapter addMemberVerticalAdapter;
    private AddMemberHorizontalAdapter addmemberHorizontalAdapter;
    private int groupId = -1;

    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
            groupId = bundle.getInt(ChatRoomActivity.TAG_GROUP, -1);
        mBinding.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.buttonConfirm.setOnClickListener(v -> {
            String group_uids = "";
            for (FriendEntity item : addMemberVerticalAdapter.getSelectedItem()) {
                group_uids += (item.getId() + ",");
            }
            mViewModel.addMembers(groupId, group_uids);
        });
        LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(this);
        horizontalLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rvHorizontalContact.setLayoutManager(horizontalLinearLayoutManager);
        addmemberHorizontalAdapter = new AddMemberHorizontalAdapter(mBinding.rvHorizontalContact);
        mBinding.rvHorizontalContact.setAdapter(addmemberHorizontalAdapter);
        mBinding.rvVerticalContact.setLayoutManager(new LinearLayoutManager(this));
        addMemberVerticalAdapter = new AddMemberVerticalAdapter(this, mBinding.rvVerticalContact);
        addMemberVerticalAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.rvVerticalContact.setAdapter(addMemberVerticalAdapter);
        addMemberVerticalAdapter.setOnItemClickListener((adapter, view, position) -> {
            mBinding.editQuery.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mBinding.editQuery.getWindowToken(), 0);
            if (((FriendEntity) adapter.getData().get(position)).isJoined())
                return;
            if (((FriendEntity) adapter.getData().get(position)).isSelected()) {
                addMemberVerticalAdapter.setItemChecked((FriendEntity) adapter.getData().get(position), false);
                addmemberHorizontalAdapter.remove((FriendEntity) adapter.getData().get(position));
            } else {
                addMemberVerticalAdapter.setItemChecked((FriendEntity) adapter.getData().get(position), true);
                addmemberHorizontalAdapter.addData((FriendEntity) adapter.getData().get(position));
                mBinding.rvHorizontalContact.smoothScrollToPosition(addmemberHorizontalAdapter.getData().size() - 1);
            }
            addMemberVerticalAdapter.notifyItemChanged(position);
            if (addMemberVerticalAdapter.getSelectedItem().size() > 0) {
                mBinding.buttonConfirm.setEnabled(true);
                mBinding.buttonConfirm.setText("确定(" + addMemberVerticalAdapter.getSelectedItem().size() + ")");
            } else {
                mBinding.buttonConfirm.setEnabled(false);
                mBinding.buttonConfirm.setText("确定");
            }
        });

        mBinding.rvVerticalContact.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.editQuery.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mBinding.editQuery.getWindowToken(), 0);
                return false;
            }
        });

        mBinding.sideView.setOnTouchLetterChangeListener(letter -> {
            int pos = addMemberVerticalAdapter.getLetterPosition(letter);
            if (pos != -1) {
                mBinding.rvVerticalContact.scrollToPosition(pos);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) mBinding.rvVerticalContact.getLayoutManager();
                assert mLayoutManager != null;
                mLayoutManager.scrollToPositionWithOffset(pos, 0);
            }
        });

        addmemberHorizontalAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mBinding.editQuery.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mBinding.editQuery.getWindowToken(), 0);
                addMemberVerticalAdapter.setItemChecked((FriendEntity) adapter.getData().get(position), false);
                addmemberHorizontalAdapter.remove(position);
                if (addMemberVerticalAdapter.getSelectedItem().size() > 0) {
                    mBinding.buttonConfirm.setEnabled(true);
                    mBinding.buttonConfirm.setText("确定(" + addMemberVerticalAdapter.getSelectedItem().size() + ")");
                } else {
                    mBinding.buttonConfirm.setEnabled(false);
                    mBinding.buttonConfirm.setText("确定");
                }
            }
        });

        mBinding.editQuery.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addMemberVerticalAdapter.getFilter().filter(s);
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
        mViewModel.uc.addMembers.observe(this, this::handleAddMember);
    }

    private void handleAddMember(Boolean aBoolean) {
        showShortToast("添加成功");
        //设置返回数据
        this.setResult(RESULT_OK);
        finish();
    }

    private static final String TAG = "AddMemberActivity";

    private void handleInit(List<MemberEntity> memberEntities) {
        List<FriendEntity> localFriendEntities = FriendDaoManager.getInstance().searchAll();
        HashMap<String, String> memberHashMap = new HashMap<>();
        List<String> memberIDs = new ArrayList<>();
        if (memberEntities != null && memberEntities.size() > 0) {
            for (MemberEntity item : memberEntities) {
                memberHashMap.put(item.getId() + "", "");
            }
        }
        if (localFriendEntities != null && localFriendEntities.size() > 0) {
            for (FriendEntity item : localFriendEntities) {
                item.setIsJoined(false);
                item.setSelected(false);
                memberHashMap.put(item.getId() + "", "");
            }
        }
        if (memberEntities != null && memberEntities.size() > 0) {
            for (MemberEntity item : memberEntities) {
                memberHashMap.remove(item.getId() + "");
            }
        }
        Log.e(TAG, "handleInit:memberEntityHashMap =  " + memberIDs.size() + ",memberEntities = " + memberEntities.size());
        if (localFriendEntities != null && localFriendEntities.size() > 0) {
            for (int i = 0; i < localFriendEntities.size(); i++) {
                if (!memberHashMap.containsKey(localFriendEntities.get(i).getId() + "")) {
                    localFriendEntities.get(i).setIsJoined(true);
                } else {
                    localFriendEntities.get(i).setIsJoined(false);
                }
            }
        }
        Log.e(TAG, "handleInit: " + localFriendEntities.size());
        addMemberVerticalAdapter.setNewData(initFriends(localFriendEntities));
    }

    private List<FriendEntity> initFriends(List<FriendEntity> friendEntities) {
        for (FriendEntity item : friendEntities) {
            item.setLetter(TextUtils.getLetter(item.getNikeName()));
            if (!android.text.TextUtils.isEmpty(item.getTag())) {
                item.setLetter(TextUtils.getLetter(item.getTag()));
            }
            item.setLayout_type(1);
        }
        sort(friendEntities);
        List<FriendEntity> newFriends = new ArrayList<>();
        for (int i = 0; i < friendEntities.size(); i++) {
            if (i == 0) {
                FriendEntity friendEntity = new FriendEntity();
                friendEntity.setLayout_type(0);
                friendEntity.setLetter(friendEntities.get(0).getLetter());
                newFriends.add(friendEntity);
            } else if (!friendEntities.get(i).getLetter().equals(friendEntities.get(i - 1).getLetter())) {
                FriendEntity friendEntity = new FriendEntity();
                friendEntity.setLayout_type(0);
                friendEntity.setLetter(friendEntities.get(i).getLetter());
                newFriends.add(friendEntity);
            }
            newFriends.add(friendEntities.get(i));
        }
        return newFriends;
    }

    public void sort(List<FriendEntity> contactList) {
        // 排序
        Collections.sort(contactList, (lhs, rhs) -> {
            if (lhs.getLetter().equals(rhs.getLetter())) {
                return lhs.getNikeName().compareTo(rhs.getNikeName());
            } else {
                if ("#".equals(lhs.getLetter())) {
                    return 1;
                } else if ("#".equals(rhs.getLetter())) {
                    return -1;
                }
                return lhs.getLetter().compareTo(rhs.getLetter());
            }
        });
    }
}
