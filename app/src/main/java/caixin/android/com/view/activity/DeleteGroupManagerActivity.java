package caixin.android.com.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityDeleteGroupManagerBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import caixin.android.com.adapter.AddManagerVerticalAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.EditMangerRequest;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.TextUtils;
import caixin.android.com.viewmodel.AddMemberViewModel;


public class DeleteGroupManagerActivity extends BaseActivity<ActivityDeleteGroupManagerBinding, AddMemberViewModel> {

    public static final String DATAS = "DeleteGroupManagerActivity_DATAS";

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_delete_group_manager;
    }

    @Override
    public AddMemberViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AddMemberViewModel.class);
    }

    private AddManagerVerticalAdapter addMemberVerticalAdapter;
    private int groupId = -1;
    private List<MemberEntity> memberEntities;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            groupId = bundle.getInt(ChatRoomActivity.TAG_GROUP, -1);
            memberEntities = (List<MemberEntity>) bundle.getSerializable(DATAS);
        }
        if (memberEntities == null) {
            finish();
        }
        memberEntities.removeIf(new Predicate<MemberEntity>() {
            @Override
            public boolean test(MemberEntity memberEntity) {
                if (memberEntity.getId() == MMKVUtil.getUserInfo().getId())
                    return true;
                else
                    return false;
            }
        });
        if (memberEntities.size() == 0) {
            showShortToast("没有可操作的对象");
            finish();
            return;
        }
        mBinding.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.buttonConfirm.setOnClickListener(v -> {
            String group_uids = "";
            List<Integer> groupUids = new ArrayList<>();
            for (MemberEntity item : addMemberVerticalAdapter.getSelectedItem()) {
                groupUids.add((int) item.getId());
            }
            EditMangerRequest editMangerRequest = new EditMangerRequest();
            editMangerRequest.setGid(groupId);
            editMangerRequest.setIds(groupUids);
            editMangerRequest.setToken(MMKVUtil.getToken());
            mViewModel.deleteManager(editMangerRequest);
        });
        LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(this);
        horizontalLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mBinding.rvVerticalContact.setLayoutManager(new LinearLayoutManager(this));
        addMemberVerticalAdapter = new AddManagerVerticalAdapter(this, mBinding.rvVerticalContact);
        addMemberVerticalAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.rvVerticalContact.setAdapter(addMemberVerticalAdapter);
        addMemberVerticalAdapter.setOnItemClickListener((adapter, view, position) -> {
            mBinding.editQuery.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mBinding.editQuery.getWindowToken(), 0);
            if (((MemberEntity) adapter.getData().get(position)).isSelected()) {
                addMemberVerticalAdapter.setItemChecked((MemberEntity) adapter.getData().get(position), false);
            } else {
                addMemberVerticalAdapter.setItemChecked((MemberEntity) adapter.getData().get(position), true);
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

        mBinding.editQuery.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addMemberVerticalAdapter.getFilter().filter(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        init(memberEntities);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.deleteManager.observe(this, this::handleDeleteManager);
    }

    private void handleDeleteManager(Boolean aBoolean) {
        showShortToast("删除成功");
        //设置返回数据
        this.setResult(RESULT_OK);
        finish();
    }

    private void init(List<MemberEntity> memberEntities) {
        addMemberVerticalAdapter.setNewData(initFriends(memberEntities));
    }

    private List<MemberEntity> initFriends(List<MemberEntity> friendEntities) {
        for (MemberEntity item : friendEntities) {
            item.setLetter(TextUtils.getLetter(item.getNikename()));
            if (!android.text.TextUtils.isEmpty(item.getTag())) {
                item.setLetter(TextUtils.getLetter(item.getTag()));
            }
            item.setItemType(1);
        }
        sort(friendEntities);
        List<MemberEntity> newFriends = new ArrayList<>();
        for (int i = 0; i < friendEntities.size(); i++) {
            if (i == 0) {
                MemberEntity friendEntity = new MemberEntity();
                friendEntity.setItemType(0);
                friendEntity.setLetter(friendEntities.get(0).getLetter());
                newFriends.add(friendEntity);
            } else if (!friendEntities.get(i).getLetter().equals(friendEntities.get(i - 1).getLetter())) {
                MemberEntity friendEntity = new MemberEntity();
                friendEntity.setItemType(0);
                friendEntity.setLetter(friendEntities.get(i).getLetter());
                newFriends.add(friendEntity);
            }
            newFriends.add(friendEntities.get(i));
        }
        return newFriends;
    }

    public void sort(List<MemberEntity> contactList) {
        // 排序
        Collections.sort(contactList, (lhs, rhs) -> {
            if (lhs.getLetter().equals(rhs.getLetter())) {
                return lhs.getNikename().compareTo(rhs.getNikename());
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
