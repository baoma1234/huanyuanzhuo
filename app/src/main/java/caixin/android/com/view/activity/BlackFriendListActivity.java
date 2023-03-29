package caixin.android.com.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityBlackFriendListBinding;
import com.caixin.huanyu.databinding.ActivityGroupBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

import caixin.android.com.adapter.BlackListAdapter;
import caixin.android.com.adapter.GroupAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.entity.BlackFriendResponse;
import caixin.android.com.entity.ContactResponse;
import caixin.android.com.entity.GroupEntity;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.GroupViewModel;
import caixin.android.com.viewmodel.HomeViewModel;


public class BlackFriendListActivity extends BaseActivity<ActivityBlackFriendListBinding, HomeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_black_friend_list;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    private static final String TAG = "GroupActivity";
    private boolean isLoadMore;
    private int mNextPage = 1;
    private BlackListAdapter blackListAdapter;
    private int deletePosition;
    private BlackFriendEntity blackFriendEntity;

    @Override
    public void initData(Bundle savedInstanceState) {
        
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.titleBar.title.setText(getResources().getString(R.string.black_list));
        mBinding.list.setLayoutManager(new LinearLayoutManager(BlackFriendListActivity.this));
        blackListAdapter = new BlackListAdapter();
        blackListAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.list.setAdapter(blackListAdapter);
        blackListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                deletePosition = position;
                blackFriendEntity = blackListAdapter.getData().get(position);
                mViewModel.blackFriend(blackFriendEntity.getId());
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
            mNextPage = 1;
            mViewModel.getBlackList(mNextPage);
            isLoadMore = false;
        });
        mBinding.srlRefresh.setRefreshFooter(new ClassicsFooter(this));
        mBinding.srlRefresh.setOnLoadMoreListener(refreshLayout -> {
            mViewModel.getBlackList(mNextPage);
            isLoadMore = true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.srlRefresh.autoRefresh();
        mNextPage = 1;
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getBlackList.observe(this, this::handleGetBlackList);
        mViewModel.uc.blackFriend.observe(this, this::handleBlackFriend);
    }

    private void handleGetBlackList(List<BlackFriendEntity> blackFriendEntities) {
        mBinding.srlRefresh.finishRefresh();
        mBinding.srlRefresh.finishLoadMore();
        if (blackFriendEntities == null || blackFriendEntities.size() == 0)
            return;
        if (!isLoadMore) {
            mNextPage = 1;
            blackListAdapter.setNewData(blackFriendEntities);
            mBinding.srlRefresh.setEnableLoadMore(true);
        } else {
            blackListAdapter.addData(blackFriendEntities);
        }
        mNextPage++;
        if (blackFriendEntities.size() < 20) {
            mBinding.srlRefresh.setEnableLoadMore(false);
        }
    }

    private void handleBlackFriend(BlackFriendResponse o) {
        if (!o.getAct().equals(BlackFriendResponse.ADD_BLACK_FRINED)) {
            showShortToast(getResources().getString(R.string.remove_black_friend_success));
            FriendDaoManager.getInstance().removeToBlackList(blackFriendEntity.getId());
            blackListAdapter.remove(deletePosition);
        }
    }
}
