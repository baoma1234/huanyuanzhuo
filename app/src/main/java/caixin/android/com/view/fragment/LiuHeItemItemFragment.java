package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentLiuheItemItemBinding;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.LiuHeInfoIssue;
import caixin.android.com.entity.LiuHeInfoRequest;
import caixin.android.com.entity.MediaInfo;
import caixin.android.com.view.activity.LiuHeGalleryActivity;
import caixin.android.com.view.activity.PostImageDetailActivity;
import caixin.android.com.viewmodel.LiuHeGalleryViewModel;

//六合图库详情
//六合帖子详情
public class LiuHeItemItemFragment extends BaseFragment<FragmentLiuheItemItemBinding, LiuHeGalleryViewModel> {
    private int mType;
    private LiuHeInfoRequest mMediaInfoRequest = new LiuHeInfoRequest();
    private MediaInfo mMediaInfo;
    LiuHeInfoIssue mMediaInfoIssue;


    public static LiuHeItemItemFragment newInstance(int type, LiuHeInfoIssue mediaInfoIssue) {
        LiuHeItemItemFragment forumItemItemFragment = new LiuHeItemItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Extras.TYPE, type);
        bundle.putSerializable(Extras.MEDIA_INFO_ISSUE, mediaInfoIssue);
        forumItemItemFragment.setArguments(bundle);
        return forumItemItemFragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_liuhe_item_item;
    }

    @Override
    public LiuHeGalleryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(LiuHeGalleryViewModel.class);
    }

    private void handleMediaInfoDetail(MediaInfo mediaInfo) {
        mBinding.srlRefresh.finishRefresh();
        mMediaInfo = mediaInfo;
        mBinding.tvDate.setText(mMediaInfo.getContent().getTime());
        mBinding.tvSee.setText(String.valueOf(mMediaInfo.getContent().getDianji()));
        RequestOptions option = new RequestOptions().error(R.mipmap.web_default).placeholder(R.mipmap.web_default);
        Glide.with(this)
                .load(mediaInfo.getContent().getImg())
                .apply(option)
                .into(mBinding.ivImage);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        assert arguments != null;
        mType = arguments.getInt(Extras.TYPE);
        LiuHeDetailListFragment mParentFragment = (LiuHeDetailListFragment) getParentFragment();
        assert mParentFragment != null;
        mMediaInfoIssue = (LiuHeInfoIssue) arguments.getSerializable(Extras.MEDIA_INFO_ISSUE);
        assert mMediaInfoIssue != null;
        mMediaInfoRequest.setId(mMediaInfoIssue.getId());
        mBinding.ivImage.setOnClickListener(v -> {
            if (mMediaInfo == null || mMediaInfo.getContent() == null || TextUtils.isEmpty(mMediaInfo.getContent().getImg()))
                return;
            List<String> images = new ArrayList<>();
            images.add(mMediaInfo.getContent().getImg());
            PostImageDetailActivity.navTo(getContext(), images, 0);
        });
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srlRefresh.setRefreshHeader(classicsHeader);
        mBinding.srlRefresh.setHeaderHeight(30);
        mBinding.srlRefresh.autoRefresh();
        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> {
            switch (mType) {
                case LiuHeGalleryActivity.AOMENLIUHE_GALLERY:
                    mViewModel.getAOMENLiuHeArticle(mMediaInfoRequest);
                    break;
                case LiuHeGalleryActivity.XIANGGANGLIUHE_GALLERY:
                    mViewModel.getXIANGGANGLiuHeArticle(mMediaInfoRequest);
                    break;
            }
            mBinding.srlRefresh.setEnableLoadMore(false);
            mBinding.srlRefresh.finishRefresh(5000);
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.mMediaInfoLiveData.observe(this, this::handleMediaInfoDetail);
    }
}