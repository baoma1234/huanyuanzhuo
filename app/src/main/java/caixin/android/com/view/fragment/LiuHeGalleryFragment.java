package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentLiuheGalleryBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import caixin.android.com.adapter.LiuHeGalleryAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.LiuHeGalleryIndex;
import caixin.android.com.entity.LiuHeIndexItem;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.view.activity.LiuHeGalleryActivity;
import caixin.android.com.view.activity.LiuHeGalleryDetailActivity;
import caixin.android.com.viewmodel.LiuHeGalleryViewModel;
import caixin.android.com.widget.PinyinComparator;
import caixin.android.com.widget.TitleItemDecoration;

/**
 * 六合图库列表
 */
public class LiuHeGalleryFragment extends BaseFragment<FragmentLiuheGalleryBinding, LiuHeGalleryViewModel> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    private LiuHeGalleryAdapter mAdapter;
    private PinyinComparator mComparator;
    private List<LiuHeIndexItem> mLiuHeIndexModels;
    private TitleItemDecoration mDecoration;
    private int type;

    public static LiuHeGalleryFragment newInstance(int type) {
        LiuHeGalleryFragment liuHeGalleryFragment = new LiuHeGalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Extras.TYPE, type);
        liuHeGalleryFragment.setArguments(bundle);
        return liuHeGalleryFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_liuhe_gallery;
    }

    @Override
    public LiuHeGalleryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(LiuHeGalleryViewModel.class);
    }

    private void handleLiuHeIndex(LiuHeGalleryIndex liuHeGalleryIndex) {
        if (mBinding.srlRefresh.isRefreshing())
            mBinding.srlRefresh.setRefreshing(false);
        mLiuHeIndexModels = liuHeGalleryIndex.getList();
        if (liuHeGalleryIndex.getHot() != null) {
            for (int i = 0; i < liuHeGalleryIndex.getHot().size(); i++) {
                liuHeGalleryIndex.getHot().get(i).setKey("#");
            }
            mLiuHeIndexModels.addAll(0, liuHeGalleryIndex.getHot());
        }
        if (mDecoration != null)
            mBinding.rvContent.removeItemDecoration(mDecoration);
        mDecoration = new TitleItemDecoration(Objects.requireNonNull(getContext()), mLiuHeIndexModels);
        Collections.sort(mLiuHeIndexModels, mComparator);
        mBinding.rvContent.addItemDecoration(mDecoration);
        mAdapter.setNewData(mLiuHeIndexModels);
    }

    @Override
    public void onRefresh() {
        switch (type) {
            case LiuHeGalleryActivity.AOMENLIUHE_GALLERY:
                mViewModel.getAOMENLiuHeIndex();
                break;
            case LiuHeGalleryActivity.XIANGGANGLIUHE_GALLERY:
                mViewModel.getXIANGGANGLiuHeIndex();
                break;
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LiuHeIndexItem indexItem = (LiuHeIndexItem) adapter.getItem(position);
        if (indexItem == null) return;
        LiuHeGalleryDetailActivity.navTo(getActivity(), type, indexItem.getId(), -1, indexItem.getTypename(), null);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.rvContent.setLayoutManager(linearLayoutManager);
        mBinding.rvContent.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        mBinding.vsbBar.setOnTouchLetterChangeListener(letter -> {
            int pos = mAdapter.getPositionForSection(letter.charAt(0));
            if (pos != -1) {
                mBinding.rvContent.scrollToPosition(pos);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) mBinding.rvContent.getLayoutManager();
                assert mLayoutManager != null;
                mLayoutManager.scrollToPositionWithOffset(pos, 0);
            }
        });
        mComparator = new PinyinComparator();
        mAdapter = new LiuHeGalleryAdapter(mLiuHeIndexModels);
        View empty = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, mBinding.rvContent, false);
        mAdapter.setEmptyView(empty);
        mBinding.rvContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mBinding.srlRefresh.setOnRefreshListener(this);

        Bundle arguments = getArguments();
        assert arguments != null;
        type = arguments.getInt(Extras.TYPE);
        switch (type) {
            case LiuHeGalleryActivity.AOMENLIUHE_GALLERY:
                mViewModel.getAOMENLiuHeIndex();
                break;
            case LiuHeGalleryActivity.XIANGGANGLIUHE_GALLERY:
                mViewModel.getXIANGGANGLiuHeIndex();
                break;
        }
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.mLiuHeIndexLiveData.observe(this, this::handleLiuHeIndex);
    }
}