package caixin.android.com.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityWebContainerBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.constant.Extras;
import caixin.android.com.view.fragment.webview.AgentWebFragment;
import caixin.android.com.view.fragment.webview.VasSonicFragment;
import caixin.android.com.viewmodel.HomeViewModel;

import static caixin.android.com.view.fragment.webview.SonicJavaScriptInterface.PARAM_CLICK_TIME;


public class WebContainerActivity extends BaseActivity<ActivityWebContainerBinding, HomeViewModel> {
    FragmentTransaction ft;

    public static void navTo(Context context, String url, String title) {
        Intent intent = new Intent(context, WebContainerActivity.class);
        intent.putExtra(Extras.URL, url);
        intent.putExtra(Extras.TITLE, title);
        context.startActivity(intent);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_web_container;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    private static final String TAG = "WebContainerActivity";

    private String oldUrl = "";
    private boolean isOnNewIntent = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnNewIntent) {
            isOnNewIntent = false;
        } else {
            title = getIntent().getStringExtra(Extras.TITLE);
            url = getIntent().getStringExtra(Extras.URL);
        }
        Log.e(TAG, "onResume: oldUrl = " + oldUrl + ",url = " + url);
        if (!oldUrl.equals(url)) {
            Bundle mBundle = null;
            ft = mFragmentManager.beginTransaction();
            if (mAgentWebFragment == null) {
                ft.add(R.id.container_framelayout, mAgentWebFragment = VasSonicFragment.create(mBundle = new Bundle()), AgentWebFragment.class.getName());
            } else {
                ft.replace(R.id.container_framelayout, mAgentWebFragment = VasSonicFragment.create(mBundle = new Bundle()), AgentWebFragment.class.getName());
            }
            mBundle.putLong(PARAM_CLICK_TIME, getIntent().getLongExtra(PARAM_CLICK_TIME, -1L));
            mBundle.putString(AgentWebFragment.URL_KEY, url);
            mBundle.putString(AgentWebFragment.TITLE, title);
            ft.commit();
            oldUrl = url;
        }
    }

    String title;
    String url;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        title = intent.getStringExtra(Extras.TITLE);
        isOnNewIntent = true;
        url = intent.getStringExtra(Extras.URL);
        Log.e(TAG, "onNewIntent: oldUrl = " + oldUrl + ",url = " + url);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private FragmentManager mFragmentManager;
    private AgentWebFragment mAgentWebFragment;

    @Override
    public void initData(Bundle savedInstanceState) {
        Log.e(TAG, "initData: ");
        mFragmentManager = this.getSupportFragmentManager();
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
