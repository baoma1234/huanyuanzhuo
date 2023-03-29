package caixin.android.com.view.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityAboutUsBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.viewmodel.AboutUsViewModel;

public class AboutUsActivity extends BaseActivity<ActivityAboutUsBinding, AboutUsViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_about_us;
    }

    @Override
    public AboutUsViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(AboutUsViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ImgLoader.GlideLoadRoundedCorners(R.mipmap.ic_caixin, mBinding.image, R.mipmap.ic_caixin);
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.titleBar.title.setText("关于我们");
        mBinding.text.setText("BC"+getAppName(this) + " V:" + getAppVersionName(this));
    }

    @Override
    public void initViewObservable() {

    }

    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前app version name
     */
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        return appVersionName;
    }
}
