package caixin.android.com.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityPostImageDetailBinding;

import caixin.android.com.view.fragment.PostImageDetailFragment;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.constant.Extras;

import java.io.Serializable;
import java.util.List;


public class PostImageDetailActivity extends BaseActivity<ActivityPostImageDetailBinding, BaseViewModel> {
    private List<String> mImgList;
    private int mCurrentIndex = 0;//当前小圆点的位置

    public static void navTo(Context context, List<String> images, int pos) {
        Intent intent = new Intent(context, PostImageDetailActivity.class);
        intent.putExtra(Extras.IMAGES, (Serializable) images);
        intent.putExtra(Extras.POSITION, pos);
        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int initContentView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility()
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        return R.layout.activity_post_image_detail;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mImgList = getIntent().getStringArrayListExtra(Extras.IMAGES);
        int mPos = getIntent().getIntExtra(Extras.POSITION, 0);
        if (mImgList.size() < 20) {
            mBinding.tvNumber.setVisibility(View.GONE);
            for (int i = 0; i < mImgList.size(); i++) {
                ImageView dot = new ImageView(this);
                if (i == mCurrentIndex) {
                    dot.setImageResource(R.drawable.page_now);//设置当前页的圆点
                } else {
                    dot.setImageResource(R.drawable.page);//其余页的圆点
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i > 0) {
                    params.leftMargin = 10;//设置圆点边距
                }
                dot.setLayoutParams(params);
                mBinding.llContainer.addView(dot);//将圆点添加到容器中
            }
        } else {
            mBinding.tvNumber.setVisibility(View.VISIBLE);
            mBinding.tvNumber.setText(mPos + "/" + mImgList.size());
        }
        mBinding.vpContent.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        //添加监听
        mBinding.vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据监听的页面改变当前页对应的小圆点
                mCurrentIndex = position;
                mBinding.tvNumber.setText(position + "/" + mImgList.size());
                for (int i = 0; i < mBinding.llContainer.getChildCount(); i++) {
                    ImageView imageView = (ImageView) mBinding.llContainer.getChildAt(i);
                    if (i == position) {
                        imageView.setImageResource(R.drawable.page_now);
                    } else {
                        imageView.setImageResource(R.drawable.page);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.vpContent.setCurrentItem(mPos);
    }

    @Override
    public void initViewObservable() {

    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return PostImageDetailFragment.newInstance(mImgList.get(i));
        }

        @Override
        public int getCount() {
            return mImgList.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        }
    }
}
