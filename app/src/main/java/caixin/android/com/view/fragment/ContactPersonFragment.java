package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentContactPersonBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.downtail.plus.decorations.FloaterItemDecoration;
import com.downtail.plus.decorations.FloaterView;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import caixin.android.com.adapter.ContactAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.daomanager.GroupDaoManager;
import caixin.android.com.entity.ContactResponse;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.TextUtils;
import caixin.android.com.view.activity.ChatRoomActivity;
import caixin.android.com.view.activity.FriendInfoActivity;
import caixin.android.com.view.activity.GroupActivity;
import caixin.android.com.viewmodel.ContactViewModel;


public class ContactPersonFragment extends BaseFragment<FragmentContactPersonBinding, ContactViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_contact_person;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private ContactAdapter contactAdapter;
    private List<FriendEntity> friendEntities = new ArrayList<>();
    FloaterItemDecoration floaterItemDecoration;

    @Override
    public void initData(Bundle savedInstanceState) {
        showDialog("");
        mViewModel.getFriends();
        mBinding.rvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        contactAdapter = new ContactAdapter(friendEntities, getContext());
        mBinding.rvContact.setAdapter(contactAdapter);
        FloaterView floaterView = FloaterView.init(mBinding.rvContact)
                .addItemType(0, R.layout.item_letter, R.id.tv_letter)
                .setOnBindViewListener((view, position) -> {
                    TextView tvHead = view.findViewById(R.id.tv_letter);
                    tvHead.setText(contactAdapter.getData().get(position).getLetter());
                });
        floaterItemDecoration = new FloaterItemDecoration(contactAdapter, floaterView);
//        mBinding.rvContact.addItemDecoration(floaterItemDecoration);
        mBinding.sideView.setOnTouchLetterChangeListener(letter -> {
            int pos = contactAdapter.getLetterPosition(letter);
            if (pos != -1) {
                mBinding.rvContact.scrollToPosition(pos);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) mBinding.rvContact.getLayoutManager();
                assert mLayoutManager != null;
                mLayoutManager.scrollToPositionWithOffset(pos, 0);
            }
        });

        contactAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FriendEntity friendEntity = (FriendEntity) adapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ChatRoomActivity.TAG_FRIEND, friendEntity);
                startActivity(FriendInfoActivity.class, bundle);
            }
        });
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
    /*    mBinding.srlRefresh.setRefreshHeader(classicsHeader);
        mBinding.srlRefresh.setHeaderHeight(30);
        mBinding.srlRefresh.setEnableRefresh(true);
        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> {
            mBinding.srlRefresh.finishRefresh(5000);
            mViewModel.getFriends();
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        mViewModel.getFriends();
        friendEntities = FriendDaoManager.getInstance().searchAll();
        if (friendEntities == null || friendEntities.isEmpty() || friendEntities.size() == 0)
            return;
        contactAdapter.setNewData(initFriends());
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getFriendsLiveData.observe(this, this::handleGetFriends);
    }

    private static final String TAG = "ContactFragment";

    private void handleGetFriends(ContactResponse o) {
//        mBinding.srlRefresh.finishRefresh();
        if (o == null) {
            contactAdapter.setNewData(null);
            return;
        }
        friendEntities = o.getFriends();
        if (friendEntities == null)
            friendEntities = new ArrayList<>();
        GroupDaoManager.getInstance().insertOrReplaceAll(o.getGroups());
        Log.e(TAG, "handleGetFriends: " + friendEntities.size());
        FriendDaoManager.getInstance().insertOrReplaceAll(friendEntities);
        contactAdapter.setNewData(initFriends());
    }

    private List<FriendEntity> initFriends() {
        List<FriendEntity> oldFriends = new ArrayList<>();
        for (FriendEntity item : friendEntities) {
            if (item.getIsBlack() == 1) {
                oldFriends.add(item);
            }
        }
        for (FriendEntity item : oldFriends) {
            item.setLetter(TextUtils.getLetter(item.getNikeName()));
            if (!android.text.TextUtils.isEmpty(item.getTag())) {
                item.setLetter(TextUtils.getLetter(item.getTag()));
            }
            item.setLayout_type(1);
        }
        sort(oldFriends);
        List<FriendEntity> newFriends = new ArrayList<>();
        for (int i = 0; i < oldFriends.size(); i++) {
            if (i == 0) {
                FriendEntity friendEntity = new FriendEntity();
                friendEntity.setLayout_type(0);
                friendEntity.setLetter(oldFriends.get(0).getLetter());
                newFriends.add(friendEntity);
            } else if (!oldFriends.get(i).getLetter().equals(oldFriends.get(i - 1).getLetter())) {
                FriendEntity friendEntity = new FriendEntity();
                friendEntity.setLayout_type(0);
                friendEntity.setLetter(oldFriends.get(i).getLetter());
                newFriends.add(friendEntity);
            }
            newFriends.add(oldFriends.get(i));
        }
        return newFriends;
    }

    public void sort(List<FriendEntity> contactList) {
        // 排序
        Collections.sort(contactList, (lhs, rhs) -> {
            if (android.text.TextUtils.isEmpty(lhs.getNikeName()) || android.text.TextUtils.isEmpty(rhs.getNikeName())) {
                return -1;
            }
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
