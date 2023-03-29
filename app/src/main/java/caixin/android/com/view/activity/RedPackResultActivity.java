package caixin.android.com.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityRedPackResultBinding;

import caixin.android.com.adapter.RedPackResultAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.chatroom.RedPackInformationResponse;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.viewmodel.RedpackResultViewModel;

public class RedPackResultActivity extends BaseActivity<ActivityRedPackResultBinding, RedpackResultViewModel> {

    private static final String TAG = "RedPackResultActivity";
    private int pgid;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_red_pack_result;
    }

    @Override
    public RedpackResultViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RedpackResultViewModel.class);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.getRedbag(pgid);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData(Bundle savedInstanceState) {
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(R.color.red_pack_bg));
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Bundle bundle = getIntent().getExtras();
        pgid = bundle.getInt("pgid");
        mBinding.titleBar.setBackgroundColor(getResources().getColor(R.color.red_pack_bg));
        mBinding.titleBar.setLeftLayoutClickListener(v -> finish());
        mBinding.titleBar.setTitle("来自" + bundle.getString("user") + "的红包");
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getRedBag.observe(this, this::handleGetRedBagResult);
    }

    private void handleGetRedBagResult(RedPackInformationResponse redPackInformationResponse) {
        RedPackInformationResponse.RedbagBean redbagBean = redPackInformationResponse.getRedbag();
        RedPackInformationResponse.RedbagBean.UserBean userBean = redbagBean.getUser();
        runOnUiThread(() -> {
            ImgLoader.GlideLoadCircle(mBinding.avatar, userBean.getImg(), R.mipmap.img_user_head);
            mBinding.name.setText(userBean.getNikeName());
            if (redPackInformationResponse.getGrab() == null || redPackInformationResponse.getGrab().size() == 0) {
                mBinding.num.setText(String.format(getResources().getString(R.string.red_pack_19),
                        "0/" + redbagBean.getSize(),
                        redbagBean.getMoney()));
            } else {
                mBinding.num.setText(String.format(getResources().getString(R.string.red_pack_19),
                        redPackInformationResponse.getGrab().size() + "/" + redbagBean.getSize(),
                        redbagBean.getMoney()));
            }
            if (redPackInformationResponse.getGrab() != null && redPackInformationResponse.getGrab().size() > 0) {
                boolean win = false;
                for (RedPackInformationResponse.GrabBean item : redPackInformationResponse.getGrab()) {
                    if (item.getUid() == MMKVUtil.getUserInfo().getId()) {
                        if (mBinding.winGroup.getVisibility() != View.VISIBLE) {
                            mBinding.winGroup.setVisibility(View.VISIBLE);
                        }
                        mBinding.winCoin.setText(item.getMoney());
                        mBinding.name.setText(String.format(getResources().getString(R.string.red_pack_18), "红包零钱"));
                        win = true;
                    }
                }
                if (!win)
                    if (mBinding.notWin.getVisibility() != View.VISIBLE) {
                        mBinding.notWin.setVisibility(View.VISIBLE);
                    }
                RedPackResultAdapter adapter = new RedPackResultAdapter(RedPackResultActivity.this, redPackInformationResponse.getGrab());
                mBinding.recyclerView.setAdapter(adapter);
            }
        });
    }
}
