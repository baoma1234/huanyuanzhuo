package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentItemGroupBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

import caixin.android.com.adapter.GroupAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.daomanager.GroupDaoManager;
import caixin.android.com.entity.ContactResponse;
import caixin.android.com.entity.GroupEntity;
import caixin.android.com.view.activity.ChatRoomActivity;
import caixin.android.com.viewmodel.ContactViewModel;


public class GroupFragment extends BaseFragment<FragmentItemGroupBinding, ContactViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_item_group;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private GroupAdapter groupAdapter;
    List<GroupEntity> groupEntities;

    private void handleGetGroups(ContactResponse o) {
        if (o == null) {
            groupAdapter.setNewData(null);
            return;
        }
        groupEntities = o.getGroups();
        GroupDaoManager.getInstance().insertOrReplaceAll(groupEntities);
        groupAdapter.setNewData(groupEntities);
        mBinding.srlRefresh.finishRefresh();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        groupAdapter = new GroupAdapter(getContext());
        groupAdapter.setEmptyView(View.inflate(getContext(), R.layout.empty_layout, null));
        mBinding.list.setAdapter(groupAdapter);
        groupAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            mViewModel.applyGroup(((GroupEntity) adapter.getData().get(position)).getId());
        });
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (((GroupEntity)adapter.getData().get(position)).getStatus() == GroupEntity.STATE_ADDED){
                Bundle bundle = new Bundle();
                bundle.putInt(ChatRoomActivity.CHATROOM_TYPE, ChatRoomActivity.TYPE_GROUP);
                bundle.putInt(ChatRoomActivity.TAG_GROUP, ((GroupEntity) adapter.getData().get(position)).getId().intValue());
                bundle.putString(ChatRoomActivity.TAG_TITLE, ((GroupEntity) adapter.getData().get(position)).getName());
                startActivity(ChatRoomActivity.class, bundle);
//                }
            }
        });
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srlRefresh.setRefreshHeader(classicsHeader);
        mBinding.srlRefresh.setHeaderHeight(30);
        mBinding.srlRefresh.setEnableRefresh(true);
        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(5000); //这个记得设置，否则一直转圈
            mViewModel.getGroups();
        });
        mViewModel.getGroups();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }


    @Override
    public void initViewObservable() {
        mViewModel.uc.getGroupsLiveData.observe(this, this::handleGetGroups);
    }

    private static final String TAG = "ContactFragment";

}
