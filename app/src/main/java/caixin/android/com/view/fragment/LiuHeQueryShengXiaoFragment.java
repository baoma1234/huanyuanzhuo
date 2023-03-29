package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentLiuheQueryShengxiaoBinding;
import com.caixin.huanyu.databinding.FragmentMineBinding;

import caixin.android.com.adapter.LiuHeQueryShengXiaoAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.entity.LHCYModel;
import caixin.android.com.viewmodel.LiuHeQueryViewModel;
import caixin.android.com.viewmodel.MineViewModel;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * 六合常识(生肖)
 */
public class LiuHeQueryShengXiaoFragment extends BaseFragment<FragmentLiuheQueryShengxiaoBinding, LiuHeQueryViewModel> implements SwipeRefreshLayout.OnRefreshListener {
    private LiuHeQueryShengXiaoAdapter mAdapter;

    public static LiuHeQueryShengXiaoFragment newInstance() {
        return new LiuHeQueryShengXiaoFragment();
    }

    private void handleLHCY(LHCYModel lhcyModel) {
        if (mBinding.srlRefresh.isRefreshing())
            mBinding.srlRefresh.setRefreshing(false);
        mAdapter.setNewData(lhcyModel.getSx());
    }

    @Override
    public int initContentView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        return R.layout.fragment_liuhe_query_shengxiao;
    }

    @Override
    public LiuHeQueryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(LiuHeQueryViewModel.class);
    }

    @Override
    public void onRefresh() {
        mViewModel.getHelperLHCY();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAdapter = new LiuHeQueryShengXiaoAdapter(null);
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvContent.setAdapter(mAdapter);
        mBinding.srlRefresh.setOnRefreshListener(this);
        mViewModel.getHelperLHCY();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.mHelperLHCYLiveData.observe(this, this::handleLHCY);
    }
}
