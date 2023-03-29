package caixin.android.com.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityLiuHeGalleryDetailBinding;


import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.constant.Extras;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.view.fragment.LiuHeDetailListFragment;
import caixin.android.com.viewmodel.LiuHeGalleryViewModel;

public class LiuHeGalleryDetailActivity extends BaseActivity<ActivityLiuHeGalleryDetailBinding, LiuHeGalleryViewModel> {

    private int mType;
    private int mTypeid;

    public static void navTo(Context context, int type, int galleryId, int postId, String title, String year ) {
        Intent intent = new Intent(context, LiuHeGalleryDetailActivity.class);
        intent.putExtra(Extras.TYPE, type);
        intent.putExtra(Extras.POST_ID, postId);
        intent.putExtra(Extras.ID, galleryId);
        intent.putExtra(Extras.TITLE, title);
        intent.putExtra(Extras.PIC_YEAR, year);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_liu_he_gallery_detail;
    }

    @Override
    public LiuHeGalleryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LiuHeGalleryViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mType = getIntent().getIntExtra(Extras.TYPE, ActionUtil.ACTION_LIUHE_GALLERY);
        mTypeid = getIntent().getIntExtra(Extras.ID, 0);
        int mPostId = getIntent().getIntExtra(Extras.POST_ID, -1);
        String title = getIntent().getStringExtra(Extras.TITLE);
        String picYear = getIntent().getStringExtra(Extras.PIC_YEAR);
        mBinding.tvTitle.setText(title);
        mBinding.ivClose.setOnClickListener(v -> finish());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, LiuHeDetailListFragment.newInstance(mType, mTypeid, mPostId, picYear), LiuHeDetailListFragment.class.getSimpleName());
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void initViewObservable() {

    }
}
