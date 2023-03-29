package caixin.android.com.view.fragment;

import android.content.Intent;
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
import com.caixin.huanyu.databinding.FragmentCodeQueryBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

import caixin.android.com.adapter.CodeQueryAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.HelperLRHMRequest;
import caixin.android.com.entity.LRHMModel;
import caixin.android.com.view.activity.QueryCodeDetailActivity;
import caixin.android.com.view.activity.QueryCommonActivity;
import caixin.android.com.viewmodel.LiuHeQueryViewModel;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * 冷热号码查询
 */
public class CodeQueryFragment extends BaseFragment<FragmentCodeQueryBinding, LiuHeQueryViewModel> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    private CodeQueryAdapter mAdapter;
    private HelperLRHMRequest mRequest;
    private int mSubType;
    private LRHMModel mLRHMModel;
    private int mCurrentQiShu = 50;

    public static CodeQueryFragment newInstance(int subType) {
        CodeQueryFragment codeQueryFragment = new CodeQueryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Extras.SUB_TYPE, subType);
        codeQueryFragment.setArguments(bundle);
        return codeQueryFragment;
    }

    public static final int AOMEN_TYPE = 1;
    public static final int XIANGGANG_TYPE = 2;

    public void initData(int num) {
        mRequest.setNums(num);
        if (mSubType == 0) {
            //热号
            mRequest.setSortType("hot");
            mRequest.setSwitchtype(AOMEN_TYPE);
        } else if (mSubType == 1) {
            //冷号
            mRequest.setSortType("sxCold");
            mAdapter.setType(1);
            mRequest.setSwitchtype(AOMEN_TYPE);
        } else if (mSubType == 2) {
            mRequest.setSortType("hot");
            mRequest.setSwitchtype(XIANGGANG_TYPE);
        } else if (mSubType == 3) {
            mRequest.setSwitchtype(XIANGGANG_TYPE);
            mRequest.setSortType("sxCold");
            mAdapter.setType(1);
        }
        mViewModel.getHelperLRHM(mRequest);
    }

    @Override
    public int initContentView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        return R.layout.fragment_code_query;
    }

    private void handleLRHM(LRHMModel lrhmModel) {
        mLRHMModel = lrhmModel;
        if (mBinding.srlRefresh.isRefreshing())
            mBinding.srlRefresh.setRefreshing(false);
        mAdapter.setNewData(lrhmModel.getCold_hot_data());
        ((QueryCommonActivity) getActivity()).updateQiShu(lrhmModel.getHotdata().get(0), lrhmModel.getNums());
    }

    @Override
    public LiuHeQueryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(LiuHeQueryViewModel.class);
    }

    @Override
    public void onRefresh() {
        initData(mCurrentQiShu);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LRHMModel.ColdHotDataBean coldHotDataBean = (LRHMModel.ColdHotDataBean) adapter.getItem(position);
        if (coldHotDataBean == null || mLRHMModel == null || mLRHMModel.getHotdata() == null)
            return;
        Intent intent = new Intent(getActivity(), QueryCodeDetailActivity.class);
        intent.putExtra(Extras.LRHMMODEL_ITEM, (Serializable) mLRHMModel.getHotdata());
        intent.putExtra(Extras.CURRENT_MODEL, coldHotDataBean);
        startActivity(intent);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        assert arguments != null;
        mSubType = arguments.getInt(Extras.SUB_TYPE, 0);
        mRequest = new HelperLRHMRequest();
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CodeQueryAdapter(null);
        mAdapter.setOnItemClickListener(this);
        mBinding.rvContent.setAdapter(mAdapter);
        mBinding.srlRefresh.setOnRefreshListener(this);
        initData(mCurrentQiShu);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.mHelperLRHMLiveData.observe(this, this::handleLRHM);
    }


}
