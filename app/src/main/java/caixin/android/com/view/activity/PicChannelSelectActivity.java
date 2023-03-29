package caixin.android.com.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityPicChannelSelectBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.adapter.PicChannelSelectAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.PicChannel;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.UserInfoViewModel;

public class PicChannelSelectActivity extends BaseActivity<ActivityPicChannelSelectBinding, UserInfoViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        
        return R.layout.activity_pic_channel_select;
    }

    @Override
    public UserInfoViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(UserInfoViewModel.class);
    }

    private int remind;

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.title.setText(getResources().getString(R.string.pic_channel_select));
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.rvPicChannel.setLayoutManager(new LinearLayoutManager(this));
        mViewModel.getPicChannel();
    }


    @Override
    public void initViewObservable() {
        mViewModel.uc.getPicChannel.observe(this, this::handleGetPicChannel);
    }

    PicChannelSelectAdapter mAdapter;

    private void handleGetPicChannel(PicChannel picChannel) {
        if (picChannel.getData() == null || picChannel.getData().isEmpty() || picChannel.getData().size() <= 1)
            return;
        MMKVUtil.setPicChannel(picChannel);
        List<String> picChannels = new ArrayList<>();
        picChannels.add("默认线路");
        for (int i = 1; i < picChannel.getData().size(); i++) {
            picChannels.add("图片线路：" + i);
        }
        if (mAdapter == null)
            mAdapter = new PicChannelSelectAdapter();
        mAdapter.setNewData(picChannels);
        mAdapter.setSelectedPosition(MMKVUtil.getSelectedPicChannel());
        mBinding.rvPicChannel.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e(TAG, "onItemChildClick: " + position);
                mAdapter.setSelectedPosition(position);
                MMKVUtil.setSelectedPicChannel(position);
            }
        });
        mBinding.tvExplain.setText(picChannel.getExplain());
    }

    private static final String TAG = "NormalSettingsActivity";
}
