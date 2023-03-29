package caixin.android.com.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentChatHistoryMessageBinding;
import com.caixin.huanyu.databinding.FragmentContactPersonBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.downtail.plus.decorations.FloaterItemDecoration;
import com.downtail.plus.decorations.FloaterView;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import caixin.android.com.adapter.BlackListAdapter;
import caixin.android.com.adapter.ChatHistoryMessageAdapter;
import caixin.android.com.adapter.ContactAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.constant.Contact;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.daomanager.GroupDaoManager;
import caixin.android.com.entity.ContactResponse;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.TextUtils;
import caixin.android.com.view.activity.BlackFriendListActivity;
import caixin.android.com.view.activity.ChatRoomActivity;
import caixin.android.com.view.activity.FriendInfoActivity;
import caixin.android.com.view.activity.GroupDetailActivity;
import caixin.android.com.viewmodel.ChatViewModel;
import caixin.android.com.viewmodel.ContactViewModel;

import static caixin.android.com.view.activity.ChatRoomActivity.TYPE_FRIEND;
import static caixin.android.com.view.activity.ChatRoomActivity.TYPE_GROUP;


public class ChatHistoryMessageFragment extends BaseFragment<FragmentChatHistoryMessageBinding, ChatViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_chat_history_message;
    }

    @Override
    public ChatViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ChatViewModel.class);
    }

    ChatHistoryMessageAdapter chatHistoryMessageAdapter;
    private int mType;
    private int friendId;
    private int groupId;

    public ChatHistoryMessageFragment(int mType, int friendId, int groupId) {
        this.mType = mType;
        this.friendId = friendId;
        this.groupId = groupId;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        chatHistoryMessageAdapter = new ChatHistoryMessageAdapter();
        chatHistoryMessageAdapter.setEmptyView(View.inflate(getContext(), R.layout.empty_layout, null));
        mBinding.list.setAdapter(chatHistoryMessageAdapter);
        mBinding.srlRefresh.setHeaderHeight(30);
        mBinding.srlRefresh.setEnableRefresh(true);
        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> {
            SendMessageResponse oldestBean;
            if (chatHistoryMessageAdapter.getData() == null || chatHistoryMessageAdapter.getData().size() == 0) {
                if (mType == TYPE_FRIEND) {
                    mViewModel.getFriendHistoryMessage(friendId, 30);
                } else if (mType == TYPE_GROUP) {
                    mViewModel.getGroupHistoryMessage(groupId, 30);
                }
                return;
            } else {
                oldestBean = chatHistoryMessageAdapter.getData().get(0);
            }
            if (chatHistoryMessageAdapter != null && oldestBean != null) {
                refreshLayout.finishRefresh(5000); //这个记得设置，否则一直转圈
                if (mType == TYPE_FRIEND) {
                    mViewModel.getMoreFriendMessage(friendId, Contact.HISTORY_MESSAGE_SIZE, oldestBean.getId().intValue());
                } else if (mType == TYPE_GROUP) {
                    mViewModel.getMoreGroupMessage(groupId, Contact.HISTORY_MESSAGE_SIZE, oldestBean.getId().intValue());
                }
            }
        });
        if (mType == TYPE_FRIEND) {
            mViewModel.getFriendHistoryMessage(friendId, 30);
        } else if (mType == TYPE_GROUP) {
            mViewModel.getGroupHistoryMessage(groupId, 30);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void handleGetMoreFriendMessage(List<SendMessageResponse> o) {
        if (o == null) {
            mBinding.srlRefresh.setNoMoreData(true);
            return;
        }
        mBinding.srlRefresh.finishRefresh();
        chatHistoryMessageAdapter.addData(0, o);
    }

    //处理获取历史消息回调
    private void handleGetFriendHistory(List<SendMessageResponse> o) {
        if (o == null) {
            mBinding.srlRefresh.setNoMoreData(true);
            return;
        }
        chatHistoryMessageAdapter.setNewData(o);
        ((LinearLayoutManager) mBinding.list.getLayoutManager()).scrollToPositionWithOffset(o.size() - 1, -DpUtil.dp2px(20));
        mBinding.srlRefresh.finishRefresh();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getHistoryFriendMessage.observe(this, this::handleGetFriendHistory);
        mViewModel.uc.getMoreFriendMessage.observe(this, this::handleGetMoreFriendMessage);
    }
}
