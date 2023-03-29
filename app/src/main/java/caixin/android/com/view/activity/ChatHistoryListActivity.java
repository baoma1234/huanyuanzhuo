package caixin.android.com.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityBlackFriendListBinding;
import com.caixin.huanyu.databinding.ActivityChatHistoryListBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

import caixin.android.com.adapter.BlackListAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.entity.BlackFriendResponse;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.view.fragment.ChatHistoryImageFragment;
import caixin.android.com.view.fragment.ChatHistoryMessageFragment;
import caixin.android.com.view.fragment.ChatHistoryZiliaoFragment;
import caixin.android.com.view.fragment.MineFragment;
import caixin.android.com.viewmodel.HomeViewModel;


public class ChatHistoryListActivity extends BaseActivity<ActivityChatHistoryListBinding, HomeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_chat_history_list;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    private int mType;
    private int friendId;
    private int groupId;

    @Override
    public void initData(Bundle savedInstanceState) {
        
        Bundle bundle = getIntent().getExtras();
        mType = bundle.getInt("type");
        friendId = bundle.getInt("friendId");
        groupId = bundle.getInt("groupId");
        mBinding.titleBar.ivClose.setOnClickListener(v -> onBackPressed());
        mBinding.titleBar.title.setText(getResources().getString(R.string.chat_history));
        mBinding.vpContent.setAdapter(new MyViewPager(getSupportFragmentManager()));
        mBinding.vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBinding.rgContainer.check(R.id.rbtn_message);
                        break;
                    case 1:
                        mBinding.rgContainer.check(R.id.rbtn_img);
                        break;
                    case 2:
                        mBinding.rgContainer.check(R.id.rbtn_ziliao);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.vpContent.setCurrentItem(0);
        mBinding.rgContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_message:
                        mBinding.vpContent.setCurrentItem(0, true);
                        break;
                    case R.id.rbtn_img:
                        mBinding.vpContent.setCurrentItem(1, true);
                        break;
                    case R.id.rbtn_ziliao:
                        mBinding.vpContent.setCurrentItem(2, true);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initViewObservable() {
    }

    private ChatHistoryImageFragment chatHistoryImageFragment;
    private ChatHistoryMessageFragment chatHistoryMessageFragment;
    private ChatHistoryZiliaoFragment chatHistoryZiliaoFragment;

    private class MyViewPager extends FragmentPagerAdapter {
        MyViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    if (chatHistoryMessageFragment == null) {
                        chatHistoryMessageFragment = new ChatHistoryMessageFragment(mType, friendId, groupId);
                    }
                    return chatHistoryMessageFragment;
                case 1:
                    if (chatHistoryImageFragment == null) {
                        chatHistoryImageFragment = new ChatHistoryImageFragment(mType, friendId, groupId);
                    }
                    return chatHistoryImageFragment;
                case 2:
                    if (chatHistoryZiliaoFragment == null) {
                        chatHistoryZiliaoFragment = new ChatHistoryZiliaoFragment(mType, friendId, groupId);
                    }
                    return chatHistoryZiliaoFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
