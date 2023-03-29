package caixin.android.com.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentFindBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

import caixin.android.com.adapter.FindListAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.view.activity.WebContainerActivity;
import caixin.android.com.viewmodel.MineViewModel;

public class FindFragment extends BaseFragment<FragmentFindBinding, MineViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_find;
    }

    @Override
    public MineViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MineViewModel.class);
    }

    private FindListAdapter findListAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.title.setText(getResources().getString(R.string.find));
        mBinding.ivClose.setVisibility(View.GONE);
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srFind.setRefreshHeader(classicsHeader);
        mBinding.srFind.setHeaderHeight(30);
        mBinding.srFind.setEnableRefresh(true);
        mBinding.srFind.setOnRefreshListener(refreshLayout -> {
            mViewModel.getFindList();
            mBinding.srFind.finishRefresh(5000);
        });
        mBinding.rvFind.setLayoutManager(new LinearLayoutManager(getContext()));
        findListAdapter = new FindListAdapter();
        findListAdapter.setEmptyView(View.inflate(getContext(), R.layout.empty_layout, null));
        mBinding.rvFind.setAdapter(findListAdapter);
        findListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), WebContainerActivity.class);
                intent.putExtra(Extras.TITLE,((FindItemModel) adapter.getData().get(position)).getTitle());
                intent.putExtra(Extras.URL, ((FindItemModel) adapter.getData().get(position)).getHref());
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mBinding.srFind.autoRefresh();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getFindList.observe(this, this::handleGetFindList);
    }

    private static final String TAG = "FindFragment";

    private void handleGetFindList(List<FindItemModel> o) {
        mBinding.srFind.finishRefresh();
        findListAdapter.setNewData(o);
    }
}
