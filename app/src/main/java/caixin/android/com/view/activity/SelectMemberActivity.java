package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivitySelectMemberBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import caixin.android.com.adapter.SelectMemberAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.TextUtils;
import caixin.android.com.viewmodel.SendRedPackViewModel;

@RequiresApi(api = Build.VERSION_CODES.N)
public class SelectMemberActivity extends BaseActivity<ActivitySelectMemberBinding, SendRedPackViewModel> {

    public static final int REQUEST_CODE = 100;

    public static void navToForResult(Activity context, List<MemberEntity> memberEntities, MemberEntity selectedMember, int groupId) {
        Intent intent = new Intent(context, SelectMemberActivity.class);
        if (selectedMember != null) {
            intent.putExtra(Extras.SELECTED_MEMBER, selectedMember);
        }
        if (memberEntities != null && memberEntities.size() > 0) {
            intent.putExtra(Extras.GROUP_MEMBERS, (Serializable) memberEntities);
        }
        intent.putExtra(Extras.GROUP_ID, groupId);
        context.startActivityForResult(intent, REQUEST_CODE);
        context.overridePendingTransition(R.anim.translate_in, R.anim.translate_out2);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_select_member;
    }

    @Override
    public SendRedPackViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SendRedPackViewModel.class);
    }

    private SelectMemberAdapter mAdapter;
    private List<MemberEntity> memberEntities;
    private MemberEntity selectedMember;
    private int groupId;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initData(Bundle savedInstanceState) {
        memberEntities = (List<MemberEntity>) getIntent().getSerializableExtra(Extras.GROUP_MEMBERS);
        selectedMember = (MemberEntity) getIntent().getSerializableExtra(Extras.SELECTED_MEMBER);
        groupId = getIntent().getIntExtra(Extras.GROUP_ID, 0);
        if (groupId == 0) {
            finish();
        }
        mBinding.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.rvMembers.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SelectMemberAdapter(this, mBinding.rvMembers);
        mAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.rvMembers.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mBinding.editQuery.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mBinding.editQuery.getWindowToken(), 0);
            if (((MemberEntity) adapter.getData().get(position)).isSelected()) {
                mAdapter.setItemChecked((MemberEntity) adapter.getData().get(position), false);
            } else {
                mAdapter.setItemChecked((MemberEntity) adapter.getData().get(position), true);
            }
            if (mAdapter.getSelectedItem() != null) {
                mBinding.buttonConfirm.setEnabled(true);
            } else {
                mBinding.buttonConfirm.setEnabled(false);
            }
            mAdapter.notifyItemChanged(position);
        });
        mBinding.rvMembers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBinding.editQuery.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mBinding.editQuery.getWindowToken(), 0);
                return false;
            }
        });

        mBinding.sideView.setOnTouchLetterChangeListener(letter -> {
            int pos = mAdapter.getLetterPosition(letter);
            if (pos != -1) {
                mBinding.rvMembers.scrollToPosition(pos);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) mBinding.rvMembers.getLayoutManager();
                assert mLayoutManager != null;
                mLayoutManager.scrollToPositionWithOffset(pos, 0);
            }
        });

        mBinding.editQuery.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        if (memberEntities != null && memberEntities.size() > 0) {
            handleInit(memberEntities, selectedMember);
        } else {
            mViewModel.getGroupMember(groupId);
        }

        mBinding.buttonConfirm.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(Extras.SELECTED_MEMBER_RESULT, mAdapter.getSelectedItem());
            setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
            finish();//此处一定要调用finish()方法
        });

        if (selectedMember != null) {
            mBinding.buttonConfirm.setEnabled(true);
        }
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getGroupMembers.observe(this, this::handleGetGroupMembers);
    }

    private void handleGetGroupMembers(List<MemberEntity> memberEntities) {
        this.memberEntities = memberEntities;
        handleInit(this.memberEntities, selectedMember);
    }

    private static final String TAG = "AddMemberActivity";

    private void handleInit(List<MemberEntity> memberEntities, MemberEntity selectedMember) {
        if (selectedMember != null && memberEntities != null && memberEntities.size() > 0) {
            for (int i = 0; i < memberEntities.size(); i++) {
                if (memberEntities.get(i).getId() == selectedMember.getId()) {
                    memberEntities.get(i).setSelected(true);
                    break;
                }
            }
        }
        mAdapter.setNewData(initFriends(memberEntities));
    }

    private List<MemberEntity> initFriends(List<MemberEntity> friendEntities) {
        friendEntities.removeIf(new Predicate<MemberEntity>() {
            @Override
            public boolean test(MemberEntity memberEntity) {
                if (memberEntity.getId() == MMKVUtil.getUserInfo().getId())
                    return true;
                else
                    return false;
            }
        });
        for (MemberEntity item : friendEntities) {
            item.setLetter(TextUtils.getLetter(item.getNikename()));
            if (!android.text.TextUtils.isEmpty(item.getTag())) {
                item.setLetter(TextUtils.getLetter(item.getTag()));
            }
            item.setItemType(1);
        }

        sort(friendEntities);
        List<MemberEntity> newMembers = new ArrayList<>();
        for (int i = 0; i < friendEntities.size(); i++) {
            if (i == 0) {
                MemberEntity friendEntity = new MemberEntity();
                friendEntity.setItemType(0);
                friendEntity.setLetter(friendEntities.get(0).getLetter());
                newMembers.add(friendEntity);
            } else if (!friendEntities.get(i).getLetter().equals(friendEntities.get(i - 1).getLetter())) {
                MemberEntity friendEntity = new MemberEntity();
                friendEntity.setItemType(0);
                friendEntity.setLetter(friendEntities.get(i).getLetter());
                newMembers.add(friendEntity);
            }
            newMembers.add(friendEntities.get(i));
        }
        return newMembers;
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.translate_out);
    }
}
