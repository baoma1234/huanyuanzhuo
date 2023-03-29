package caixin.android.com.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentActivityCenterBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

import caixin.android.com.adapter.DailyActivityAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.entity.ActiveModel;
import caixin.android.com.http.basic.config.HttpConfig;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.view.activity.MainActivity;
import caixin.android.com.viewmodel.ActivityViewModel;
import caixin.android.com.widget.ActivityDetailDialog;

import static android.content.Intent.ACTION_VIEW;

public class ActivityCenterFragment extends BaseFragment<FragmentActivityCenterBinding, ActivityViewModel> {
    private DailyActivityAdapter dailyActivityAdapter;

    public static ActivityCenterFragment newInstance() {
        ActivityCenterFragment codeQueryFragment = new ActivityCenterFragment();
        Bundle bundle = new Bundle();
        codeQueryFragment.setArguments(bundle);
        return codeQueryFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_activity_center;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        dailyActivityAdapter = new DailyActivityAdapter();
        mBinding.rvActivityList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvActivityList.setAdapter(dailyActivityAdapter);
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srAct.setRefreshHeader(classicsHeader);
        mBinding.srAct.setHeaderHeight(30);
        mBinding.srAct.setEnableRefresh(true);
        mBinding.srAct.setOnRefreshListener(refreshLayout -> {
            mViewModel.getActivities();
            mBinding.srAct.finishRefresh(5000);
        });
        mBinding.srAct.autoRefresh();
        dailyActivityAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (ActionUtil.isNeedLogin(getActivity())) return;
            showActivityDetailDialog((ActiveModel) adapter.getData().get(position));
        });
    }

    private ActivityDetailDialog mActivityDetailDialog;

    private void showActivityDetailDialog(ActiveModel activityDetailResponse) {
        dismissDialog();
        if (mActivityDetailDialog == null) {
            mActivityDetailDialog = new ActivityDetailDialog(activityDetailResponse);
        } else {
            mActivityDetailDialog.setActivityDetailResponse(activityDetailResponse);
        }
        mActivityDetailDialog.setMyClick(new ActivityDetailDialog.MyClick() {
            @Override
            public void onClick(ActiveModel activityDetailResponse) {
               getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(activityDetailResponse.getAction_url())));
            }
        });
        mActivityDetailDialog.show(getFragmentManager(), "ActivityCenterFragment");
    }

    private static final String TAG = "ActivityCenterFragment";

    @Override
    public ActivityViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ActivityViewModel.class);
    }

    private void handleDrawNewerMOMO(Object o) {
        showShortToast("领取成功");
        mBinding.srAct.autoRefresh();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getActivities.observe(this, this::handleActivities);
    }

    private void handleActivities(List<ActiveModel> activeModels) {
        mBinding.srAct.finishRefresh();
        dailyActivityAdapter.setNewData(activeModels);
    }
}
