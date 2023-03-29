package caixin.android.com.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivitySplashBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.http.WebSocketManager;
import caixin.android.com.http.basic.config.HttpConfig;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.utils.runtimepermissions.PermissionsManager;
import caixin.android.com.utils.runtimepermissions.PermissionsResultAction;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.viewmodel.SplashViewModel;

/**
 * 开屏页
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SplashViewModel.class);
    }

    AlphaAnimation animation;

    private static final String TAG = "SplashActivity";

    @Override
    public void initData(Bundle savedInstanceState) {
        isFirstTime = false;
        StatusBarUtils.immersive(this, getResources().getColor(R.color.transparent), 0);
        animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(2000);
        mViewModel.getUrlAddress();
        if (PermissionsManager.getInstance().hasPermission(this, Manifest.permission.READ_PHONE_STATE)) {
            mBinding.splashRoot.startAnimation(animation);
        } else {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, new String[]{Manifest.permission.READ_PHONE_STATE}, new PermissionsResultAction() {
                @Override
                public void onGranted() {
                    Log.e(TAG, "onGranted: ");
                    mBinding.splashRoot.startAnimation(animation);
                }

                @Override
                public void onDenied(String permission) {
                    requestPermissions();
                }
            });
        }
    }

    private void login() {
        mViewModel.httpLogin();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.login.observe(this, this::handlerLogin);
        mViewModel.uc.addressURLs.observe(this, this::handlerGetAddresses);
        mViewModel.uc.testUrl.observe(this, this::handlerTestAddresses);
    }

    private boolean isFirstTime = false;

    private void handlerTestAddresses(String s) {
        if (!TextUtils.isEmpty(s) && !isFirstTime) {
            isFirstTime = true;
            HttpConfig.WEBSOCKET_ADDRESS = "ws://" + s + ":" + HttpConfig.WEBSOCKET_PROTOCOL;
            HttpConfig.HOST = "http://" + s+":6161";
            WebSocketManager.getInstance().connectWebSocket(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    login();
                }
            }, 2000);
        }
    }

    private void handlerGetAddresses(List<String> strings) {
        if (strings == null || strings.size() == 0) {
            ToastUtils.showLong("获取线路失败，请稍后再试！");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 3000);
        } else {
            for (int i = 0; i < strings.size(); i++) {
                mViewModel.getTestAddress("http://" + strings.get(i) + ":6161/ceshiceshi");
            }
        }
    }

    private void handlerLogin(boolean o) {
        if (o) {
            startActivity(MainActivity.class);
        } else {
            startActivity(LoginActivity.class);
        }
        finish();
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                ToastUtils.showShort("所有的权限被拒绝");
            }

            @Override
            public void onDenied(String permission) {
                ToastUtils.showShort("权限 " + permission + "被拒绝");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //用户同意，执行操作
            mBinding.splashRoot.startAnimation(animation);
        } else {
            //用户不同意，向用户展示该权限作用
            finish();
        }
    }
}
