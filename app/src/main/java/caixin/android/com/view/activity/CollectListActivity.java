package caixin.android.com.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityCollectListBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

import caixin.android.com.adapter.CollectListAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.CollectEntity;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.HomeViewModel;


public class CollectListActivity extends BaseActivity<ActivityCollectListBinding, HomeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_collect_list;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    private static final String TAG = "GroupActivity";
    private boolean isLoadMore;
    private int mNextPage = 1;
    private int deletePosition;
    private CollectListAdapter collectListAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        
        mBinding.titleBar.ivClose.setOnClickListener(v -> finish());
        mBinding.titleBar.title.setText(getResources().getString(R.string.collect));
        mBinding.list.setLayoutManager(new LinearLayoutManager(CollectListActivity.this));
        collectListAdapter = new CollectListAdapter();
        collectListAdapter.setEmptyView(View.inflate(this, R.layout.empty_layout, null));
        mBinding.list.setAdapter(collectListAdapter);
        collectListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                deletePosition = position;
                mViewModel.deleteCollect(collectListAdapter.getData().get(position).getId());
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
            mViewModel.getCollect(mNextPage);
            isLoadMore = false;
        });
        mBinding.srlRefresh.setRefreshFooter(new ClassicsFooter(this));
        mBinding.srlRefresh.setOnLoadMoreListener(refreshLayout -> {
            mViewModel.getCollect(mNextPage);
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
//        mViewModel.uc.blackFriend.observe(this, this::handleBlackFriend);
        mViewModel.uc.getCollect.observe(this, this::handleGetCollect);
        mViewModel.uc.deleteCollect.observe(this, this::handleDeleteCollect);
    }

    private void handleDeleteCollect(Object o) {
        collectListAdapter.remove(deletePosition);
    }

    private void handleGetCollect(List<CollectEntity> collectEntities) {
        mBinding.srlRefresh.finishRefresh();
        mBinding.srlRefresh.finishLoadMore();
        if (collectEntities == null || collectEntities.size() == 0)
            return;
        if (!isLoadMore) {
            mNextPage = 1;
            collectListAdapter.setNewData(collectEntities);
            mBinding.srlRefresh.setEnableLoadMore(true);
        } else {
            collectListAdapter.addData(collectEntities);
        }
        mNextPage++;
        if (collectEntities.size() < 20) {
            mBinding.srlRefresh.setEnableLoadMore(false);
        }
    }
}
