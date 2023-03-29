package caixin.android.com.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityMomoexchangeRecordBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import caixin.android.com.adapter.MOMOExchangeRecordAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.MOMOExchangeRecord;
import caixin.android.com.viewmodel.MomoExchangeViewModel;

public class MOMOExchangeRecordActivity extends BaseActivity<ActivityMomoexchangeRecordBinding, MomoExchangeViewModel> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private MOMOExchangeRecordAdapter mAdapter;

    private void initData() {
        mViewModel.getMOMOExchangeRecords();
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_momoexchange_record;
    }

    @Override
    public MomoExchangeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MomoExchangeViewModel.class);
    }

    private void handleRecords(MOMOExchangeRecord momoExchangeRecord) {
        if (mBinding.srlRefresh.isRefreshing())
            mBinding.srlRefresh.setRefreshing(false);
        if (momoExchangeRecord == null) return;
        mAdapter.setNewData(momoExchangeRecord.getRecords());
//        mBinding.tvCurrentExchange.setText(DoubleUtils.m2(momoExchangeRecord.getMoney())+"元");
        mBinding.tvCurrentExchange.setText(momoExchangeRecord.getMoney() + "元");
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMoreRequested() {
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.setLeftLayoutClickListener(v -> finish());
        mBinding.titleBar.setTitle("我的彩金兑换");
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(this));
        mBinding.srlRefresh.setOnRefreshListener(this);
        mAdapter = new MOMOExchangeRecordAdapter(null);
        mBinding.rvContent.setAdapter(mAdapter);
        initData();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.moneyRecode.observe(this, this::handleRecords);
    }
}
