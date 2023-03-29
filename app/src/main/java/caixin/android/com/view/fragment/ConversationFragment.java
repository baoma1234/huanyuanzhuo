package caixin.android.com.view.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentConversationBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import caixin.android.com.adapter.ConversationAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.daomanager.ConversationDaoManager;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.entity.ZhuanPanStatusEntity;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.ToastUtils;
import caixin.android.com.view.activity.ChatRoomActivity;
import caixin.android.com.view.activity.MainActivity;
import caixin.android.com.viewmodel.ConversationViewModel;
import caixin.android.com.widget.AddPopWindow;
import caixin.android.com.widget.HomeAdView;


public class ConversationFragment extends BaseFragment<FragmentConversationBinding, ConversationViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_conversation;
    }

    @Override
    public ConversationViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ConversationViewModel.class);
    }

    private ConversationAdapter conversationAdapter;
    private int deleteConversationPosition;

    @Override
    public void initData(Bundle savedInstanceState) {
        initRecycleView();
        mBinding.searchBarView.query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                conversationAdapter.getFilter().filter(s);
                if (s.length() > 0) {
                    mBinding.searchBarView.searchClear.setVisibility(View.VISIBLE);
                } else {
                    mBinding.searchBarView.searchClear.setVisibility(View.INVISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        mBinding.searchBarView.searchClear.setOnClickListener(v -> mBinding.searchBarView.query.getText().clear());
        ClassicsHeader classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setTextSizeTitle(12.0f);
        classicsHeader.setTextSizeTime(10.0f);
        mBinding.srConversation.setRefreshHeader(classicsHeader);
        mBinding.srConversation.setHeaderHeight(30);
        mBinding.srConversation.setEnableRefresh(true);
        mBinding.srConversation.setOnRefreshListener(refreshLayout -> {
            mViewModel.init();
        });

        mBinding.ivAddMore.setOnClickListener(v -> {
            AddPopWindow popWindow = new AddPopWindow(getActivity());
            popWindow.showPopupWindow(mBinding.ivAddMore);
        });
        showDialog("");

        mViewModel.getZhuanpanState();
    }

    private long oldTime;
    private int mLastX;
    private int mLastY;

    public void initRecycleView() {
        mBinding.rvConversation.setLayoutManager(new LinearLayoutManager(getContext()));
        conversationAdapter = new ConversationAdapter(getContext());
        conversationAdapter.setEmptyView(View.inflate(getContext(), R.layout.empty_layout, null));
        mBinding.rvConversation.setAdapter(conversationAdapter);
        conversationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SendMessageResponse conversationReponse = (SendMessageResponse) adapter.getData().get(position);
                conversationReponse.setIsread(1);
                ConversationDaoManager.getInstance().insertOrReplace(conversationReponse, false);
                Bundle bundle = new Bundle();
                if (conversationReponse.getTotype() == SendMessageResponse.TOTYPE_GROUP) {
                    bundle.putInt(ChatRoomActivity.CHATROOM_TYPE, ChatRoomActivity.TYPE_GROUP);
                    bundle.putInt(ChatRoomActivity.TAG_GROUP, Integer.valueOf(conversationReponse.getGroupId()));
                    bundle.putString(ChatRoomActivity.TAG_TITLE, conversationReponse.getGroupName());
                    bundle.putString(ChatRoomActivity.TAG_HEAD_IMAGE, conversationReponse.getGroupImage());
                } else {
                    bundle.putInt(ChatRoomActivity.CHATROOM_TYPE, ChatRoomActivity.TYPE_FRIEND);
                    bundle.putString(ChatRoomActivity.TAG_HEAD_IMAGE, conversationReponse.getHeadImg());
                    int otherId;
                    if (conversationReponse.getSendId() == MMKVUtil.getUserInfo().getId()) {
                        otherId = conversationReponse.getGetId();
                    } else {
                        otherId = conversationReponse.getSendId();
                    }
                    bundle.putInt(ChatRoomActivity.TAG_FRIEND, otherId);
                    bundle.putString(ChatRoomActivity.TAG_TITLE, conversationReponse.getNikeName());
                }
                startActivity(ChatRoomActivity.class, bundle);
            }
        });
        conversationAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.delete:
                        SendMessageResponse sendMessageResponse = conversationAdapter.getData().get(position);
                        int type;//1个人 2群
                        int id;
                        if (sendMessageResponse.getTotype() == SendMessageResponse.TOTYPE_GROUP) {
                            type = 2;
                            id = sendMessageResponse.getGroupId();
                        } else {
                            type = 1;
                            if (sendMessageResponse.getSendId() == MMKVUtil.getUserInfo().getId()) {
                                id = sendMessageResponse.getGetId();
                            } else {
                                id = sendMessageResponse.getSendId();
                            }
                        }
                        mViewModel.deleteConversation(id, type, sendMessageResponse.getMessageId());
                        deleteConversationPosition = position;
                        break;
                }
            }
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.init.observe(this, this::handleInit);
        mViewModel.uc.deleteConversation.observe(this, this::handleDeleteConversation);
        mViewModel.uc.zhuanPanStatusEntityMutableLiveData.observe(this, this::handleZhuanPan);
    }

    private void handleZhuanPan(ZhuanPanStatusEntity zhuanPanStatusEntity) {
        if (zhuanPanStatusEntity.getStatus().equals("1")) {
            mBinding.homeAd.setVisibility(View.VISIBLE);
            mBinding.homeAd.setADListener(new HomeAdView.MyClick() {
                @Override
                public void onClick() {
                    openBrose(getContext(), zhuanPanStatusEntity.getUrl());
                }
            });
        } else {
            mBinding.homeAd.setVisibility(View.GONE);

        }
    }

    private void openBrose(Context context, String url) {

        try {
            Uri content_url = null;
            if (null == url) {
                ToastUtils.show("网址错误！");
                return;
            } else {
                content_url = Uri.parse(url);
            }

            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(content_url);
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(browserIntent);
            startActivity(browserIntent);
        } catch (ActivityNotFoundException a) {
            a.getMessage();
        }

    }

    private void handleDeleteConversation(Object o) {
        conversationAdapter.remove(deleteConversationPosition);
    }

    private static final String TAG = "ConversationFragment";

    private void handleInit(List<SendMessageResponse> o) {
        mBinding.srConversation.finishRefresh();
//        initRecycleView();
        if (o == null || o.size() == 0) {
            ConversationDaoManager.getInstance().deleteAll();
            conversationAdapter.setNewData(null);
            return;
        }
        sortInit(o);
        ConversationDaoManager.getInstance().insertOrReplaceAll(o);
        Log.e(TAG, "handleInit: " + ConversationDaoManager.getInstance().searchAll().size());
    }

    private void sortInit(List<SendMessageResponse> conversationReponses) {
        Collections.sort(conversationReponses, (o1, o2) -> {
            Date d1 = o1.getDate();
            Date d2 = o2.getDate();
            if (d1 == null && d2 == null)
                return 0;
            if (d1 == null)
                return -1;
            if (d2 == null)
                return 1;
            return d2.compareTo(d1);
        });
        List<SendMessageResponse> newConversations = new ArrayList<>();
        List<SendMessageResponse> notSortConversations = new ArrayList<>();
        for (int i = 0; i < conversationReponses.size(); i++) {
            if (conversationReponses.get(i).getSort() > 0) {
                newConversations.add(conversationReponses.get(i));
            } else {
                notSortConversations.add(conversationReponses.get(i));
            }
        }
        newConversations.addAll(notSortConversations);
        conversationAdapter.setNewData(newConversations);
        ((MainActivity) getActivity()).setUnreadMessage(newConversations);
    }

    public void refresh() {
        List<SendMessageResponse> searchAll = ConversationDaoManager.getInstance().searchAll();
        sortInit(searchAll);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.init();
    }
}
