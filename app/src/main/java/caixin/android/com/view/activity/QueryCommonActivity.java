package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;


import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityQueryCommonBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.LRHMModel;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.view.fragment.CodeQueryFragment;
import caixin.android.com.view.fragment.LiuHeQueryBoSeFragment;
import caixin.android.com.view.fragment.LiuHeQueryShengXiaoFragment;
import caixin.android.com.widget.SelectQiShuDialog;

//常识、号码查询
public class QueryCommonActivity extends BaseActivity<ActivityQueryCommonBinding, BaseViewModel> implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener {
    private int mAction;

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                mBinding.rgContainer.check(R.id.rbtn_left);
                break;
            case 1:
                mBinding.rgContainer.check(R.id.rbtn_right);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_left:
                mBinding.vpContent.setCurrentItem(0, false);
                break;
            case R.id.rbtn_right:
                mBinding.vpContent.setCurrentItem(1, false);
                break;
        }
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_query_common;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mAction = getIntent().getIntExtra(Extras.TYPE, ActionUtil.ACTION_QUERY_NUMBER_XIANGGANG);
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (mAction == ActionUtil.ACTION_QUERY_NUMBER_XIANGGANG) {
            mBinding.title.setText("追号助手（香港）");
            mBinding.rbtnLeft.setText("冷码特码");
            mBinding.rbtnRight.setText("冷肖特肖");
            mBinding.tvQishu.setVisibility(View.VISIBLE);
            mBinding.tvQishu.setOnClickListener(v -> showSelectQiShuDialog());
        } else if (mAction == ActionUtil.ACTION_QUERY_LIUHE) {
            mBinding.title.setText("六合常识查询");
            mBinding.rbtnLeft.setText("生肖号码");
            mBinding.rbtnRight.setText("玻色号码");
            mBinding.tvQishu.setVisibility(View.GONE);
        } else if (mAction == ActionUtil.ACTION_QUERY_NUMBER_AOMEN) {
            mBinding.title.setText("追号助手（澳门）");
            mBinding.rbtnLeft.setText("冷码特码");
            mBinding.rbtnRight.setText("冷肖特肖");
            mBinding.tvQishu.setVisibility(View.VISIBLE);
            mBinding.tvQishu.setOnClickListener(v -> showSelectQiShuDialog());
        }
        mAdapter = new MyViewPager(getSupportFragmentManager());
        mBinding.vpContent.setAdapter(mAdapter);
        mBinding.vpContent.addOnPageChangeListener(this);
        mBinding.rgContainer.setOnCheckedChangeListener(this);
        mBinding.vpContent.setCurrentItem(0);
    }

    MyViewPager mAdapter;

    @Override
    public void initViewObservable() {

    }

    private List<String> mNumbers;
    private int mNumber;

    public void updateQiShu(LRHMModel.HotdataBean hotdataBean, int number) {
        mNumber = number;
        mBinding.tvQishu.setText(number + "期");
    }

    private void showSelectQiShuDialog() {
        if (mNumber == 0) return;
        if (mNumbers == null) {
            mNumbers = new ArrayList<>();
            mNumbers.add("50");
            mNumbers.add("100");
            mNumbers.add("150");
            mNumbers.add("200");
        }
        SelectQiShuDialog qiShuDialog = new SelectQiShuDialog(this);
        qiShuDialog.setData(mNumbers);
        qiShuDialog.setQiShu(mNumber);
        qiShuDialog.setOnSelectQiShuListener(qishu -> {
            if (mAdapter != null) {
                ((CodeQueryFragment) mAdapter.mBaseFragments.get(0)).initData(Integer.valueOf(qishu));
                ((CodeQueryFragment) mAdapter.mBaseFragments.get(1)).initData(Integer.valueOf(qishu));
            }
        });
        qiShuDialog.show();
    }

    private class MyViewPager extends FragmentPagerAdapter {
        private List<BaseFragment> mBaseFragments = new ArrayList<>();

        MyViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mBaseFragments.add((BaseFragment) object);
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    if (mAction == ActionUtil.ACTION_QUERY_NUMBER_AOMEN) {
                        return CodeQueryFragment.newInstance(0);
                    } else if (mAction == ActionUtil.ACTION_QUERY_LIUHE) {
                        return LiuHeQueryShengXiaoFragment.newInstance();
                    } else if (mAction == ActionUtil.ACTION_QUERY_NUMBER_XIANGGANG) {
                        return CodeQueryFragment.newInstance(2);
                    }
                case 1:
                    if (mAction == ActionUtil.ACTION_QUERY_NUMBER_AOMEN) {
                        return CodeQueryFragment.newInstance(1);
                    } else if (mAction == ActionUtil.ACTION_QUERY_LIUHE) {
                        return LiuHeQueryBoSeFragment.newInstance();
                    } else if (mAction == ActionUtil.ACTION_QUERY_NUMBER_XIANGGANG) {
                        return CodeQueryFragment.newInstance(3);
                    }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}