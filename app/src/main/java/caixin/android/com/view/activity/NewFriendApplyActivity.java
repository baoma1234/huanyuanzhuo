package caixin.android.com.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityCollectListBinding;
import com.caixin.huanyu.databinding.ActivityNewFriendApplyBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

import caixin.android.com.adapter.CollectListAdapter;
import caixin.android.com.adapter.NewFriendApplyAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.CollectEntity;
import caixin.android.com.entity.NewFriendApplyEntity;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.ContactViewModel;
import caixin.android.com.viewmodel.HomeViewModel;


public class NewFriendApplyActivity extends BaseActivity<ActivityNewFriendApplyBinding, ContactViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_new_friend_apply;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private static final String TAG = "GroupActivity";
    private NewFriendApplyAdapter newFriendApplyAdapter;
    private int acceptFriendPosition;

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.ivClose.setOnClickListener(v -> finish());
        mBinding.titleBar.title.setText(getResources().getString(R.string.new_friend));
        mBinding.list.setLayoutManager(new LinearLayoutManager(NewFriendApplyActivity.this));
        newFriendApplyAdapter = new NewFriendApplyAdapter();
        newFriendApplyAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.list.setAdapter(newFriendApplyAdapter);
        newFriendApplyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mViewModel.acceptFriend(newFriendApplyAdapter.getData().get(position).getId());
                acceptFriendPosition = position;
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
            mViewModel.getNewFriendApplyList();
        });

        UserInfoEntity userInfo = MMKVUtil.getUserInfo();
        userInfo.setIsNew(0);
        MMKVUtil.setUserInfo(userInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.srlRefresh.autoRefresh();
    }

    @Override
    public void initViewObservable() {
//        mViewModel.uc.blackFriend.observe(this, this::handleBlackFriend);
        mViewModel.uc.getNewFriendApplyList.observe(this, this::handleGetNewFriendApplyList);
        mViewModel.uc.acceptFriend.observe(this, this::handleAcceptFriend);
    }

    private void handleAcceptFriend(Object o) {
        newFriendApplyAdapter.getData().get(acceptFriendPosition).setStatus(1);
        newFriendApplyAdapter.notifyItemChanged(acceptFriendPosition);
    }

    private void handleGetNewFriendApplyList(List<NewFriendApplyEntity> newFriendApplyEntities) {
        mBinding.srlRefresh.finishRefresh();
        if (newFriendApplyEntities == null || newFriendApplyEntities.size() == 0)
            return;
        newFriendApplyAdapter.setNewData(newFriendApplyEntities);
    }
}
