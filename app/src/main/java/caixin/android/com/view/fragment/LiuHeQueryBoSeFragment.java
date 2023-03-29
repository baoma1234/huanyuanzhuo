package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentLiuheQueryBoseBinding;

import caixin.android.com.adapter.LiuHeQueryBoSeAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.entity.LHCYModel;
import caixin.android.com.viewmodel.LiuHeQueryViewModel;

/**
 * 六合常识查询(波色)
 */
public class LiuHeQueryBoSeFragment extends BaseFragment<FragmentLiuheQueryBoseBinding, LiuHeQueryViewModel> implements SwipeRefreshLayout.OnRefreshListener {
    private LiuHeQueryBoSeAdapter mAdapter;

    public static LiuHeQueryBoSeFragment newInstance() {
        return new LiuHeQueryBoSeFragment();
    }

    private void handleLHCY(LHCYModel lhcyModel) {
        if (mBinding.srlRefresh.isRefreshing())
            mBinding.srlRefresh.setRefreshing(false);
        mAdapter.setNewData(lhcyModel.getBo());
    }

    @Override
    public int initContentView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        return R.layout.fragment_liuhe_query_bose;
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
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new LiuHeQueryBoSeAdapter(null);
        mBinding.rvContent.setAdapter(mAdapter);
        mBinding.srlRefresh.setOnRefreshListener(this);
        mViewModel.getHelperLHCY();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.mHelperLHCYLiveData.observe(this, this::handleLHCY);
    }
}