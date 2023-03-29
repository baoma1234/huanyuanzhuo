/*
package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentFriendUpdatesBinding;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.view.activity.BlackFriendListActivity;
import caixin.android.com.view.activity.CollectListActivity;
import caixin.android.com.view.activity.MultipleSendActivity;
import caixin.android.com.view.activity.FriendCommunityActivity;
import caixin.android.com.view.activity.MyWalletActivity;
import caixin.android.com.view.activity.ScanActivity;
import caixin.android.com.view.activity.SignActivity;
import caixin.android.com.viewmodel.MineViewModel;

public class FriendUpdateFragment extends BaseFragment<FragmentFriendUpdatesBinding, MineViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_friend_updates;
    }

    @Override
    public MineViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(MineViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.title.setText(getResources().getString(R.string.updates));
        mBinding.ivClose.setVisibility(View.GONE);
        mBinding.rlFriendUpdates.setOnClickListener(v -> startActivity(FriendCommunityActivity.class));
        mBinding.llBlacklist.setOnClickListener(v -> startActivity(BlackFriendListActivity.class));
        mBinding.llScan.setOnClickListener(v -> startActivity(ScanActivity.class));
        mBinding.llDailySign.setOnClickListener(v -> startActivity(SignActivity.class));
        mBinding.llGroupSend.setOnClickListener(v -> startActivity(MultipleSendActivity.class));
        mBinding.llWallet.setOnClickListener(v -> startActivity(MyWalletActivity.class));
        mBinding.llCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CollectListActivity.class);
            }
        });
    }

    @Override
    public void initViewObservable() {

    }
}
*/
