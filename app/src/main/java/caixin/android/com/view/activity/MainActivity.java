package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityMainBinding;
import com.kongzue.dialog.v3.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.constant.Extras;
import caixin.android.com.daomanager.ConversationDaoManager;
import caixin.android.com.entity.AppVersion;
import caixin.android.com.entity.ApplyStatusResponse;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.entity.HomeImageAdModel;
import caixin.android.com.entity.NoticePopRequest;
import caixin.android.com.entity.PopModel;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.entity.base.BaseWebSocketResponse;
import caixin.android.com.http.WebSocketManager;
import caixin.android.com.utils.AppUtil;
import caixin.android.com.utils.DateUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.WebUtils;
import caixin.android.com.utils.runtimepermissions.PermissionsManager;
import caixin.android.com.utils.runtimepermissions.PermissionsResultAction;
import caixin.android.com.view.fragment.ContactFragment;
import caixin.android.com.view.fragment.ConversationFragment;
import caixin.android.com.view.fragment.MineFragment;
import caixin.android.com.viewmodel.MainViewModel;
import caixin.android.com.widget.HomeAdDialog;
import caixin.android.com.widget.HomeMenuPop;
import caixin.android.com.widget.UpdateDialog;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private static final String CONVERSATION_FRAGMENT = "CONVERSATION_FRAGMENT";
    private static final String LATEST_EVENTS_FRAGMENT = "LATEST_EVENTS_FRAGMENT";
    private static final String MINE_FRAGMENT = "MINE_FRAGMENT";
    private static final String FRIEND_UPDATE_FRAGMENT = "FRIEND_UPDATE_FRAGMENT";
    private NavigationController navigationController;
    Fragment contactFragment;
    Fragment conversationFragment;
    Fragment mineFragment;
    Fragment friendUpdateFragment;

    private Handler mApplyHandler;

    public static MainActivity INSTANCE;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    List<SendMessageResponse> conversationReponses;

    @Override
    public MainViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initData(Bundle savedInstanceState) {
        INSTANCE = this;
        conversationReponses = ConversationDaoManager.getInstance().searchAll();

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            contactFragment = getSupportFragmentManager().getFragment(savedInstanceState, CONVERSATION_FRAGMENT);
            conversationFragment = getSupportFragmentManager().getFragment(savedInstanceState, LATEST_EVENTS_FRAGMENT);
            mineFragment = getSupportFragmentManager().getFragment(savedInstanceState, MINE_FRAGMENT);
            friendUpdateFragment = getSupportFragmentManager().getFragment(savedInstanceState, FRIEND_UPDATE_FRAGMENT);
            addToList(contactFragment);
            addToList(mineFragment);
            addToList(conversationFragment);
            addToList(friendUpdateFragment);
        } else {
            initFragment();
        }
        initBottomTab();
        requestPermissions();
        mViewModel.getVersion();
        NoticePopRequest noticePopRequest = new NoticePopRequest();
        noticePopRequest.setType(1);
        noticePopRequest.setVersion(AppUtil.getAppVersion(this));
        mViewModel.getPopInfo(noticePopRequest);
        mViewModel.getHomeImageAd();
        mViewModel.getHomeMenuList();

        mApplyHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                mViewModel.getApplyStatus();
                mApplyHandler.removeMessages(0);
                mApplyHandler.sendEmptyMessageDelayed(0, 5000);
            }
        };
        mApplyHandler.sendEmptyMessage(0);
    }

    private HomeAdDialog mHomeAdDialog;

    private void handleGetHomeImageAd(HomeImageAdModel homeImageAdModel) {
        if (DateUtil.isItToday(Long.valueOf(MMKVUtil.getHomeImageAdTime()))) {
            return;
        }
        if (homeImageAdModel.getTanch() == null)
            return;
        if (mHomeAdDialog == null) {
            mHomeAdDialog = new HomeAdDialog(homeImageAdModel);
        }
        mHomeAdDialog.show(getSupportFragmentManager(), "chatIntroduce");
    }


    private void popInfo(PopModel popModel) {
        List<CustomDialog> list = new ArrayList<>();

        if (popModel.getTcggs() == null)
            return;

        for (int i = 0; i < popModel.getTcggs().size(); i++) {
            int j = i;
            CustomDialog customDialog = CustomDialog.build(this, R.layout.layout_ads_popup, (dialog, v) -> {
                LinearLayout llContent = v.findViewById(R.id.ll_content);
                WebUtils webUtils = new WebUtils();
                webUtils.setWebView(MainActivity.this, llContent, popModel.getTcggs().get(j).getContent());
                TextView tvAd = v.findViewById(R.id.tv_ad);
                if (TextUtils.isEmpty(popModel.getTcggs().get(j).getUrl())) {
                    tvAd.setVisibility(View.GONE);
                }
                tvAd.setOnClickListener(view -> {
                    try {
                        MainActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(popModel.getTcggs().get(j).getUrl())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                ImageView btnOk = v.findViewById(R.id.iv_close);
                btnOk.setOnClickListener(v1 -> dialog.doDismiss());
            }).setAlign(CustomDialog.ALIGN.DEFAULT).setCancelable(false);
            list.add(customDialog);
        }
        show(list);
    }

    private void show(List<CustomDialog> list) {
        for (int i = 0; i < list.size(); i++) {
            int j = i;
            new Handler().postDelayed(() -> list.get(j).show(), 1000 * i);
        }
    }

    public static MainActivity getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(MainActivity INSTANCE) {
        MainActivity.INSTANCE = INSTANCE;
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
    protected void onDestroy() {
        super.onDestroy();
        INSTANCE = null;
    }

    private void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            mFragmentManager.beginTransaction().add(R.id.frameLayout, fragment).commit();
            mFragments.add(fragment);
        }
    }

    private void addToList(Fragment fragment) {
        if (fragment != null) {
            mFragments.add(fragment);
        }
    }

    private void initFragment() {
        conversationFragment = new ConversationFragment();
        addFragment(conversationFragment);
        showFragment(conversationFragment);
    }

    private void showFragment(Fragment fragment) {
        for (Fragment frag : mFragments) {
            if (frag != fragment) {
                /*先隐藏其他fragment*/
                getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        }
        mFragmentManager.beginTransaction().show(fragment).commit();
    }

    private void showFragment(int index) {
        switch (index) {
            case 0:
                if (conversationFragment == null) {
                    conversationFragment = new ConversationFragment();
                }
                addFragment(conversationFragment);
                showFragment(conversationFragment);
                break;
            case 1:
                if (contactFragment == null) {
                    contactFragment = new ContactFragment();
                }
                addFragment(contactFragment);
                showFragment(contactFragment);
                break;
         /*   case 2:
                StatusBarUtils.immersive(MainActivity.this, getResources().getColor(R.color.colorPrimary));
                if (friendUpdateFragment == null) {
                    friendUpdateFragment = ActivityCenterFragment.newInstance();
                }
                addFragment(friendUpdateFragment);
                showFragment(friendUpdateFragment);
                break;*/
            case 2:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
                addFragment(mineFragment);
                showFragment(mineFragment);
                ImageView imageView = new ImageView(this);
                imageView.setBackground(getResources().getDrawable(R.drawable.thumb));
                break;
        }
    }

    private HomeMenuPop mHomeMenuPop;

    private void initBottomTab() {
        navigationController = mBinding.pagerBottomTabHome.custom()
                .addItem(newItem(R.mipmap.ic_message, R.mipmap.ic_message_selected, "消息"))
                .addItem(newItem(R.mipmap.ic_contacts, R.mipmap.ic_contacts_selected, "通讯录"))
                .addItem(newItem(R.mipmap.ic_mine, R.mipmap.ic_mine_selected, "我的"))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                showFragment(index);
                if (index == 1) {
                    mViewModel.getApplyStatus();
                }
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }

    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(getResources().getColor(R.color.black));
        return normalItemView;
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getVersion.observe(this, this::update);
        mViewModel.uc.mPopLiveData.observe(this, this::popInfo);
        mViewModel.uc.mImageAdLiveData.observe(this, this::handleGetHomeImageAd);
        mViewModel.uc.getHomeMenuList.observe(this, this::handleGetHomeMenuList);
        mViewModel.uc.getApplyStatus.observe(this, this::handleGetApplyStatus);
    }

    private void handleGetApplyStatus(ApplyStatusResponse o) {
        if (o.getStatus() == 1 || MMKVUtil.getUserInfo().getIsNew() > 0) {
            navigationController.setHasMessage(1, true);
            if (contactFragment != null) {
                ((ContactFragment) contactFragment).setOvalVisibility(true);
            }
        } else {
            navigationController.setHasMessage(1, false);
            if (contactFragment != null) {
                ((ContactFragment) contactFragment).setOvalVisibility(false);
            }
        }
    }

    private void handleGetHomeMenuList(List<FindItemModel> findItemModels) {
        if (mHomeMenuPop == null) {
            mHomeMenuPop = new HomeMenuPop(MainActivity.this);
        }
        mHomeMenuPop.setFindItemModels(findItemModels);
    }

    private void update(AppVersion appVersion) {
        Log.e(TAG, "update: " + appVersion.getVersion());
        int version = 0;
        try {
            version = Integer.valueOf(appVersion.getVersion());
        } catch (Exception ignore) {
        }
        if (version <= AppUtil.getPackageCode(this)) {
            return;
        }
        UpdateDialog dialog = new UpdateDialog();
        if (appVersion.getIs_update() == 1) {
            dialog.setCancelable(false);
        }
        dialog.setAppVersion(appVersion);
        dialog.show(getSupportFragmentManager(), UpdateDialog.class.getSimpleName());
    }

    private static final String TAG = "MainActivity";
    private WebSocketManager.ServerMessage serverMessage = new WebSocketManager.ServerMessage() {
        @Override
        public void onReceiveMessageFromServer(BaseWebSocketResponse baseWebSocketResponse) {
            if (baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_SEND_MESSAGE)
                    || baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_SEND_PIC)
                    || baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_FRIEND_AGREE)
                    || baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_FRIEND_APPLY)) {
                if (baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_FRIEND_APPLY)) {
                    UserInfoEntity userInfo = MMKVUtil.getUserInfo();
                    userInfo.setIsNew(1);
                    MMKVUtil.setUserInfo(userInfo);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            navigationController.setHasMessage(1, true);
                            if (contactFragment != null)
                                ((ContactFragment) contactFragment).setOvalVisibility(true);
                        }
                    });
                }
                new Handler(getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((ConversationFragment) conversationFragment).refresh();
                        if (contactFragment != null)
                            ((ContactFragment) contactFragment).refresh();
                    }
                }, 1000);
            }
        }
    };

    public void setUnreadMessage(List<SendMessageResponse> searchAll) {
        if (searchAll != null && searchAll.size() > 0) {
            for (SendMessageResponse item : searchAll) {
                if (item.getUnread() > 0) {
                    navigationController.setHasMessage(0, true);
                    return;
                }
            }
        }
        navigationController.setHasMessage(0, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WebSocketManager.getInstance().addServerReceive(TAG, serverMessage);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WebSocketManager.getInstance().removeServerReceive(TAG);
    }
}
