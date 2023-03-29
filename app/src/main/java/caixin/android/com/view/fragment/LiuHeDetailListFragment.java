package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentLiuheItemBinding;

import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.LiuHeDataResponse;
import caixin.android.com.entity.LiuHeInfoIssue;
import caixin.android.com.entity.LiuHeRequest;
import caixin.android.com.entity.YearAdapter;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.view.activity.LiuHeGalleryActivity;
import caixin.android.com.viewmodel.LiuHeGalleryViewModel;

public class LiuHeDetailListFragment extends BaseFragment<FragmentLiuheItemBinding, LiuHeGalleryViewModel> {
    private int mType;
    private String picYear;
    private LiuHeRequest mLiuHeDataRequest = new LiuHeRequest();
    private int mPostId;
    private LiuHeItemItemFragment liuHeItemItemFragment;
    private YearAdapter yearAdapter;

    /**
     * @param type   {@link ActionUtil}
     * @param postId 动态跳转会有这个值
     */
    public static LiuHeDetailListFragment newInstance(int type, int typeId, int postId, String year) {
        LiuHeDetailListFragment forumItemFragment = new LiuHeDetailListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Extras.TYPE, type);
        bundle.putInt(Extras.POST_ID, postId);
        bundle.putInt(Extras.ID, typeId);
        bundle.putString(Extras.PIC_YEAR, year);
        forumItemFragment.setArguments(bundle);
        return forumItemFragment;
    }

    private void handleYearInfo(List<String> list) {
        yearAdapter.setNewData(list);
        if (!TextUtils.isEmpty(picYear)) {
            for (int i = 0; i < list.size(); i++) {
                if (picYear.equals(list.get(i))) {
                    yearAdapter.selectPosition(i);
                    mLiuHeDataRequest.setYear(list.get(i));
                }
            }
        } else {
            yearAdapter.selectPosition(0);
            mLiuHeDataRequest.setYear(list.get(0));
        }
        getLiuHeData();
        if (list.size() > 1) {
            yearAdapter.setOnItemClickListener((adapter, view, position) -> {
                yearAdapter.selectPosition(position);
                yearAdapter.notifyDataSetChanged();
                mLiuHeDataRequest.setYear(list.get(position));
                showDialog();
                getLiuHeData();
            });
        }
    }

    public void getLiuHeData() {
        switch (mType) {
            case LiuHeGalleryActivity.AOMENLIUHE_GALLERY:
                mViewModel.getAOMENLiuHeData(mLiuHeDataRequest);//获取期数年
                break;
            case LiuHeGalleryActivity.XIANGGANGLIUHE_GALLERY:
                mViewModel.getXIANGGANGLiuHeData(mLiuHeDataRequest);
                break;
        }
    }

    public void getLiuHeYear(int typeid) {
        switch (mType) {
            case LiuHeGalleryActivity.AOMENLIUHE_GALLERY:
                mViewModel.getAOMENLiuHeYear(typeid);
                break;
            case LiuHeGalleryActivity.XIANGGANGLIUHE_GALLERY:
                mViewModel.getXIANGGANGLiuHeYear(typeid);
                break;
        }
    }

    private void handleMediaInfo(LiuHeDataResponse liuHeDataResponse) {
        if (liuHeDataResponse == null) return;
        List<LiuHeInfoIssue> mediaInfoIssues = liuHeDataResponse.getQscount();
        if (mediaInfoIssues == null || mediaInfoIssues.isEmpty()) return;
        MyPageAdapter mMyPageAdapter = new MyPageAdapter(getChildFragmentManager(), mediaInfoIssues);
        mBinding.vpContent.setAdapter(mMyPageAdapter);
        mBinding.tlTab.setupWithViewPager(mBinding.vpContent);
        mBinding.vpContent.setOffscreenPageLimit(1);
        if (mPostId != -1) {
            for (int i = 0; i < mediaInfoIssues.size(); i++) {
                if (mPostId == mediaInfoIssues.get(i).getId()) {
                    //跳转到当前id的帖子
                    mBinding.vpContent.setCurrentItem(i, false);
                }
            }
        } else {
            mBinding.vpContent.setCurrentItem(0, false);
        }
        mBinding.vpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        assert arguments != null;
        mType = arguments.getInt(Extras.TYPE, 0);
        mPostId = arguments.getInt(Extras.POST_ID, -1);
        int typeid = arguments.getInt(Extras.ID, 0);
        picYear = arguments.getString(Extras.PIC_YEAR);
        mLiuHeDataRequest.setTypeid(typeid);
        yearAdapter = new YearAdapter();
        mBinding.rvYear.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.rvYear.setAdapter(yearAdapter);
        getLiuHeYear(typeid);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.mLiuHeYearResponseLiveData.observe(this, this::handleYearInfo);
        mViewModel.uc.mLiuHeDataResponseLiveData.observe(this, this::handleMediaInfo);
    }

    private class MyPageAdapter extends FragmentStatePagerAdapter {
        private List<LiuHeInfoIssue> mMediaInfoIssues;

        MyPageAdapter(FragmentManager fm, List<LiuHeInfoIssue> mediaInfoIssues) {
            super(fm);
            mMediaInfoIssues = mediaInfoIssues;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        }

        @Override
        public Fragment getItem(int i) {
            liuHeItemItemFragment = LiuHeItemItemFragment.newInstance(mType, mMediaInfoIssues.get(i));
            return liuHeItemItemFragment;
        }

        @Override
        public int getCount() {
            return mMediaInfoIssues.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mMediaInfoIssues.get(position).getQishu();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        liuHeItemItemFragment = null;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_liuhe_item;
    }

    @Override
    public LiuHeGalleryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(LiuHeGalleryViewModel.class);
    }
}