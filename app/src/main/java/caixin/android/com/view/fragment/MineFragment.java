package caixin.android.com.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.AppUtils;
import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentMineBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.view.activity.CollectListActivity;
import caixin.android.com.view.activity.ForgetPasswordActivity;
import caixin.android.com.view.activity.LoginActivity;
import caixin.android.com.view.activity.NormalSettingsActivity;
import caixin.android.com.view.activity.UserInfoActivity;
import caixin.android.com.viewmodel.MineViewModel;


public class MineFragment extends BaseFragment<FragmentMineBinding, MineViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_mine;
    }

    @Override
    public MineViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MineViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        update(MMKVUtil.getUserInfo());
//        mBinding.rlAbout.setOnClickListener(v -> startActivity(AboutUsActivity.class));
        mBinding.btnLogout.setOnClickListener(v -> showLogoutDialog());
        mBinding.rlMineInfo.setOnClickListener(v -> startActivity(UserInfoActivity.class));
        mBinding.tvUseAccount.setText("账号：" + MMKVUtil.getUserInfo().getMobile());
        mBinding.rlSecuritySetting.setOnClickListener(v -> startActivity(ForgetPasswordActivity.class));
        mBinding.tvVersionInfo.setText("当前版本：" + AppUtils.getAppVersionName());
        mBinding.rlNormalSetting.setOnClickListener(v -> startActivity(NormalSettingsActivity.class));
        mBinding.rlCollect.setOnClickListener(v -> startActivity(CollectListActivity.class));

    }

    @SuppressLint("ResourceAsColor")
    private void showLogoutDialog() {
        AlertDialog.Builder normalMoreButtonDialog = new AlertDialog.Builder(getContext());
        normalMoreButtonDialog.setMessage("您确定要退出？");
        //设置按钮
        normalMoreButtonDialog.setPositiveButton("确定", (dialog, which) -> {
            mViewModel.logout();
        });
        normalMoreButtonDialog.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = normalMoreButtonDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.black);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.black);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.logout.observe(this, this::handleLogout);
    }

    private void handleLogout(Object o) {
        MMKVUtil.setToken("");
        MMKVUtil.setUserInfo(null);
        caixin.android.com.Application.getInstance().setIsHttpLogin(false);
        startActivity(LoginActivity.class);
        getActivity().finish();
    }

    private void update(UserInfoEntity userResponse) {
        ImgLoader.loadGif(mBinding.ivAvatar,userResponse.getImg());
//        ImgLoader.GlideLoadCircle(mBinding.ivAvatar, userResponse.getImg(), R.mipmap.img_user_head);
        mBinding.userNickname.setText(userResponse.getNikeName());
    }

    public void refresh() {
        update(MMKVUtil.getUserInfo());
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }
}
