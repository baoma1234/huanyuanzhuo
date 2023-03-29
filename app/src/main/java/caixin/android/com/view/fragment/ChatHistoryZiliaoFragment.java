package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentChatHistoryImageBinding;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.adapter.GridAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.view.activity.PostImageDetailActivity;
import caixin.android.com.viewmodel.ChatViewModel;


public class ChatHistoryZiliaoFragment extends BaseFragment<FragmentChatHistoryImageBinding, ChatViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_chat_history_image;
    }

    @Override
    public ChatViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ChatViewModel.class);
    }

    GridAdapter gridAdapter;
    private boolean isLoadMore;
    private int mNextPage = 1;
    private int mType;
    private int friendId;
    private int groupId;

    public ChatHistoryZiliaoFragment(int mType, int friendId, int groupId) {
        this.mType = mType;
        this.friendId = friendId;
        this.groupId = groupId;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        gridAdapter = new GridAdapter(R.layout.activity_gridview_layout);
        gridAdapter.setEmptyView(View.inflate(getContext(), R.layout.empty_layout, null));
        mBinding.list.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mBinding.list.setAdapter(gridAdapter);
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srlRefresh.setRefreshHeader(classicsHeader);
        mBinding.srlRefresh.setHeaderHeight(30);
        mBinding.srlRefresh.setEnableRefresh(true);
        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(5000); //这个记得设置，否则一直转圈
            mNextPage = 1;
            mViewModel.getZiliaoHistoryMessage(friendId, groupId, mNextPage, 32);
            isLoadMore = false;
        });
        mBinding.srlRefresh.setRefreshFooter(new ClassicsFooter(getContext()));
        mBinding.srlRefresh.setOnLoadMoreListener(refreshLayout -> {
            mViewModel.getZiliaoHistoryMessage(friendId, groupId, mNextPage, 20);
            isLoadMore = true;
        });
        mBinding.srlRefresh.autoRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNextPage = 1;
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getImgHistory.observe(this, this::handleGetImgHistory);
    }

    private void handleGetImgHistory(List<SendMessageResponse.ImgBean> imgBeans) {
        mBinding.srlRefresh.finishRefresh();
        mBinding.srlRefresh.finishLoadMore();
        if (imgBeans == null || imgBeans.isEmpty()) {
            return;
        }
        List<String> imgs = new ArrayList<>();
        List<String> thumbs = new ArrayList<>();
        for (SendMessageResponse.ImgBean imgBean : imgBeans) {
            thumbs.add(imgBean.getThumbImgUrl());
            imgs.add(imgBean.getImgurl());
        }
        if (!isLoadMore) {
            mNextPage = 1;
            gridAdapter.setNewData(thumbs, imgs);
            mBinding.srlRefresh.setEnableLoadMore(true);
        } else {
            gridAdapter.addData(thumbs, imgs);
        }
        mNextPage++;
        if (imgBeans.size() < 20) {
            mBinding.srlRefresh.setEnableLoadMore(false);
        }
        gridAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.item_img) {
                PostImageDetailActivity.navTo(getContext(), imgs, position);
            }
        });
    }
}
