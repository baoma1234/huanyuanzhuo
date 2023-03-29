package caixin.android.com.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityMoneyRecodeBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import caixin.android.com.adapter.MoneyRecodeAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.MoneyRecodeEntity;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.MomoExchangeViewModel;


public class MoneyRecodeActivity extends BaseActivity<ActivityMoneyRecodeBinding, MomoExchangeViewModel> {
    private int mNextPage = 1;
    private MoneyRecodeAdapter moneyRecodeAdapter;
    private boolean isLoadMore = false;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_money_recode;
    }

    @Override
    public MomoExchangeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MomoExchangeViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.setLeftLayoutClickListener(v -> onBackPressed());
        
        mBinding.titleBar.setTitle("余额明细").setTextColor(getResources().getColor(R.color.white));
        mBinding.srWithdrawRecode.setOnLoadMoreListener(refreshLayout -> {
            mViewModel.getMoneyRecodeList(mNextPage);
            isLoadMore = true;
        });
        ClassicsHeader classicsHeader = new ClassicsHeader(this);
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srWithdrawRecode.setRefreshHeader(classicsHeader);
        mBinding.srWithdrawRecode.setHeaderHeight(30);
        mBinding.srWithdrawRecode.setEnableRefresh(true);
        mBinding.srWithdrawRecode.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isLoadMore = false;
                mViewModel.getMoneyRecodeList(mNextPage);
            }
        });
        mBinding.rvWithdrawRecode.setLayoutManager(new LinearLayoutManager(this));
        moneyRecodeAdapter = new MoneyRecodeAdapter();
        mBinding.rvWithdrawRecode.setAdapter(moneyRecodeAdapter);
        View empty = LayoutInflater.from(this).inflate(R.layout.empty_layout, mBinding.rvWithdrawRecode, false);
        moneyRecodeAdapter.setEmptyView(empty);
        moneyRecodeAdapter.removeAllHeaderView();
        moneyRecodeAdapter.addHeaderView(getLayoutInflater().inflate(R.layout.money_recode_header, mBinding.rvWithdrawRecode, false));
        Intent intent = getIntent();
        String money = intent.getStringExtra("money");
        ((TextView) moneyRecodeAdapter.getHeaderLayout().findViewById(R.id.tv_header)).setText(money);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getMoneyRecodeList.observe(this, this::handleGetWithDrawRecode);
    }

    private void handleGetWithDrawRecode(List<MoneyRecodeEntity> withdrawRecodeBeans) {
        mBinding.srWithdrawRecode.finishLoadMore();
        mBinding.srWithdrawRecode.finishRefresh();
        if (withdrawRecodeBeans == null || withdrawRecodeBeans.size() == 0)
            return;
        if (!isLoadMore) {
            mNextPage = 1;
            moneyRecodeAdapter.setNewData(withdrawRecodeBeans);
            moneyRecodeAdapter.removeAllFooterView();
            mBinding.srWithdrawRecode.setEnableLoadMore(true);
        } else {
            moneyRecodeAdapter.addData(withdrawRecodeBeans);
        }
        mNextPage++;
        if (withdrawRecodeBeans.size() < 20) {
            moneyRecodeAdapter.removeAllFooterView();
            moneyRecodeAdapter.addFooterView(getLayoutInflater().inflate(R.layout.content_foot_view, mBinding.rvWithdrawRecode, false));
            mBinding.srWithdrawRecode.setEnableLoadMore(false);
        }
        moneyRecodeAdapter.setNewData(withdrawRecodeBeans);
    }

    @Override
    public void onResume() {
        super.onResume();
        isLoadMore = false;
        mBinding.srWithdrawRecode.autoRefresh();
    }
}
