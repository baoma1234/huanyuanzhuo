package caixin.android.com.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityGroupBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import caixin.android.com.adapter.GroupAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.entity.ContactResponse;
import caixin.android.com.entity.GroupEntity;
import caixin.android.com.viewmodel.GroupViewModel;
import caixin.android.com.base.BaseActivity;

import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;


public class GroupActivity extends BaseActivity<ActivityGroupBinding, GroupViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_group;
    }

    @Override
    public GroupViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(GroupViewModel.class);
    }

    private GroupAdapter groupAdapter;
    private static final String TAG = "GroupActivity";

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.titleBar.title.setText("群组");
        mBinding.list.setLayoutManager(new LinearLayoutManager(GroupActivity.this));
        groupAdapter = new GroupAdapter(GroupActivity.this);
        groupAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.list.setAdapter(groupAdapter);
        groupAdapter.setOnItemChildClickListener((adapter, view, position) -> mViewModel.applyGroup(((GroupEntity) adapter.getData().get(position)).getId().intValue()));
        groupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (((GroupEntity)adapter.getData().get(position)).getStatus() == GroupEntity.STATE_ADDED){
                    Bundle bundle = new Bundle();
                    bundle.putInt(ChatRoomActivity.CHATROOM_TYPE, ChatRoomActivity.TYPE_GROUP);
                    bundle.putInt(ChatRoomActivity.TAG_GROUP, ((GroupEntity) adapter.getData().get(position)).getId().intValue());
                    bundle.putString(ChatRoomActivity.TAG_TITLE,  ((GroupEntity) adapter.getData().get(position)).getName());
                    startActivity(ChatRoomActivity.class, bundle);
//                }
            }
        });
        ClassicsHeader classicsHeader = new ClassicsHeader(this);
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srlRefresh.setRefreshHeader(classicsHeader);
        mBinding.srlRefresh.setHeaderHeight(30);
        mBinding.srlRefresh.setEnableRefresh(true);
        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(5000); //这个记得设置，否则一直转圈
            mViewModel.init();
        });
        mViewModel.init();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.init.observe(this, this::handleInit);
//        mViewModel.uc.applyGroup.observe(this, this::handleApplyGroup);
    }

   /* private void handleApplyGroup(Object o) {
        for (GroupEntity item : groupEntities) {
            if (item.getId() == (Integer) o) {
                item.setStatus(GroupEntity.STATE_APPLYING);
            }
        }
        groupAdapter.setNewData(groupEntities);
    }*/

    List<GroupEntity> groupEntities;

    private void handleInit(ContactResponse o) {
        if (o == null){
            groupAdapter.setNewData(null);
            return;
        }
        groupEntities = o.getGroups();
        groupAdapter.setNewData(groupEntities);
        mBinding.srlRefresh.finishRefresh();
    }
}
