/*
package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityMultipleSendChatRoomBinding;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import caixin.android.com.adapter.ImChatFacePagerAdapter;
import caixin.android.com.adapter.ImRoomAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.constant.Extras;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.daomanager.GroupDaoManager;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.GroupEntity;
import caixin.android.com.entity.MultipleSendRequest;
import caixin.android.com.entity.MultipleSendResponse;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.entity.base.BaseWebSocketResponse;
import caixin.android.com.entity.chatroom.MessageResponse;
import caixin.android.com.http.WebSocketManager;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.utils.ClickUtil;
import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.FaceUtil;
import caixin.android.com.utils.KeyBoardHeightUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.TextRender;
import caixin.android.com.viewmodel.HomeViewModel;
import caixin.android.com.widget.ChatFaceDialog;
import caixin.android.com.widget.ChatMoreDialog;
import caixin.android.com.widget.KeyBoardHeightChangeListener;
import caixin.android.com.widget.MyImageView;
import caixin.android.com.widget.OnFaceClickListener;
import caixin.android.com.widget.TopGradual;
import caixin.android.com.widget.ait.MyTextSpan;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static caixin.android.com.entity.SendMessageResponse.TYPE_IMAGE;
import static caixin.android.com.entity.SendMessageResponse.TYPE_TEXT;

@SuppressLint("ClickableViewAccessibility")
public class MultipleSendChatRoomActivity extends BaseActivity<ActivityMultipleSendChatRoomBinding, HomeViewModel> implements View.OnClickListener, KeyBoardHeightChangeListener, OnFaceClickListener, ChatFaceDialog.ActionListener, ChatMoreDialog.ActionListener, ImRoomAdapter.ActionListener, DataCallback, WebSocketManager.ServerMessage {

    private InputMethodManager imm;
    private ChatFaceDialog mChatFaceDialog;//表情弹窗
    private ChatMoreDialog mMoreDialog;//更多弹窗
    private View mMoreView;//更多控件
    private View mFaceView;//表情控件
    protected KeyBoardHeightUtil mKeyBoardHeightUtil;
    private int mFaceViewHeight;//表情控件高度
    private int mMoreViewHeight;//更多控件的高度
    private ImRoomAdapter mAdapter;
    private List<FriendEntity> friendEntities = new ArrayList<>();
    private List<GroupEntity> groupEntities = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        mKeyBoardHeightUtil.release();
    }

    private int mType;
    List<Integer> friendIds;
    List<Integer> groupIds;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initData(Bundle savedInstanceState) {
//        isLogin = false;
        
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Intent intent = getIntent();
        mType = intent.getIntExtra("type", 0);
        if (mType == MultipleSendActivity.TYPE_FRIEND) {
            mBinding.title.setText(getResources().getString(R.string.multiple_send_friend));
        } else if (mType == MultipleSendActivity.TYPE_FRIEND_AND_GROUP) {
            mBinding.title.setText(getResources().getString(R.string.multiple_send_group_and_friend));
        } else if (mType == MultipleSendActivity.TYPE_GROUP) {
            mBinding.title.setText(getResources().getString(R.string.multiple_send_group));
        } else {
            finish();
        }
        friendEntities = FriendDaoManager.getInstance().searchAll();
        groupEntities = GroupDaoManager.getInstance().searchAll();
        friendIds = new ArrayList<>();
        for (FriendEntity friendEntity : friendEntities) {
            Long id = friendEntity.getId();
            friendIds.add(id.intValue());
        }
        groupIds = new ArrayList<>();
        for (GroupEntity friendEntity : groupEntities) {
            Long id = friendEntity.getId();
            groupIds.add(id.intValue());
        }
        mBinding.recyclerView.setHasFixedSize(true);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerView.addItemDecoration(new TopGradual());
        mBinding.ivClose.setOnClickListener(v -> finish());
        mBinding.edit.setOnClickListener(this);
        mBinding.edit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String content = mBinding.edit.getText().toString().trim();
                if (ActionUtil.isLogin()) {
                    ToastUtils.showShort("请先登录!");
                    return true;
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShort(R.string.content_empty);
                    return true;
                }
                mBinding.edit.setText("");
                return true;
            }
            return false;
        });
        mBinding.btnAdd.setOnClickListener(this);
        mBinding.btnFace.setOnClickListener(this);
        mBinding.recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return hideSoftInput() || hideFace() || hideMore();
            }
            return false;
        });
        mKeyBoardHeightUtil = new KeyBoardHeightUtil(this, findViewById(android.R.id.content), this);
        mBinding.container.postDelayed(() -> {
            if (mKeyBoardHeightUtil != null) {
                mKeyBoardHeightUtil.start();
            }
        }, 500);
        mAdapter = new ImRoomAdapter(this);
        mAdapter.setActionListener(this);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.edit.setOnTouchListener((v, event) -> {
            clickInput();
            return false;
        });
        mBinding.tvSend.setOnClickListener(v -> {
            String content = mBinding.edit.getText().toString().trim();
            if (ActionUtil.isLogin()) {
                ToastUtils.showShort("请先登录!");
                return;
            }
            if (TextUtils.isEmpty(content)) {
                ToastUtils.showShort(R.string.content_empty);
                return;
            }
            sendMessage();
            mBinding.edit.setText("");
        });
        mBinding.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before == 1 && count == 0) {
                    MyTextSpan[] spans = mBinding.edit.getText().getSpans(0, mBinding.edit.getText().length(), MyTextSpan.class);
                    Log.e(TAG, "onTextChanged: " + spans.length);
                    for (MyTextSpan myImageSpan : spans) {
                        if (mBinding.edit.getText().getSpanEnd(myImageSpan) == start && !s.toString().endsWith(myImageSpan.getShowText())) {
                            mBinding.edit.getText().delete(mBinding.edit.getText().getSpanStart(myImageSpan), mBinding.edit.getText().getSpanEnd(myImageSpan));
                            break;
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void sendMessage() {
        MultipleSendRequest multipleSendRequest = new MultipleSendRequest();
        if (mType == MultipleSendActivity.TYPE_FRIEND) {
            multipleSendRequest.setFriendIds(friendIds);
        } else if (mType == MultipleSendActivity.TYPE_FRIEND_AND_GROUP) {
            multipleSendRequest.setFriendIds(friendIds);
            multipleSendRequest.setGroupIds(groupIds);
        } else if (mType == MultipleSendActivity.TYPE_GROUP) {
            multipleSendRequest.setGroupIds(groupIds);
        } else {
            return;
        }
        multipleSendRequest.setContent(mBinding.edit.getText().toString());
        mViewModel.multipleSend(multipleSendRequest);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_multiple_send_chat_room;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatScrollToBottom();
        WebSocketManager.getInstance().addServerReceive("MultipleSendChatRoom", this);
    }


    private static final String TAG = "ChatActivity";

    */
/**
     * 隐藏键盘
     *//*

    private boolean hideSoftInput() {
        if (((KeyBoardHeightChangeListener) this).isSoftInputShowed() && imm != null && mBinding.edit != null) {
            imm.hideSoftInputFromWindow(mBinding.edit.getWindowToken(), 0);
            return true;
        }
        return false;
    }

    @Override
    public void onKeyBoardHeightChanged(int visibleHeight, int keyboardHeight) {
        if ((keyboardHeight == 0 && isPopWindowShowed())) {
            return;
        }
        Log.e(TAG, "onKeyBoardHeightChanged: ");
        onKeyBoardChanged(0);
    }

    public boolean isPopWindowShowed() {
        return mChatFaceDialog != null || mMoreDialog != null;
    }

    @Override
    public boolean isSoftInputShowed() {
        if (mKeyBoardHeightUtil != null) {
            return mKeyBoardHeightUtil.isSoftInputShowed();
        }
        return false;
    }

    */
/**
     * 隐藏表情弹窗
     *//*

    private boolean hideFace() {
        if (mChatFaceDialog != null) {
            mChatFaceDialog.dismiss();
            mChatFaceDialog = null;
            return true;
        }
        return false;
    }

    */
/**
     * 隐藏更多弹窗
     *//*

    private boolean hideMore() {
        if (mMoreDialog != null) {
            mMoreDialog.dismiss();
            mMoreDialog = null;
            return true;
        }
        return false;
    }

    */
/**
     * 聊天栏滚到最底部
     *//*

    public void chatScrollToBottom() {
        if (mAdapter != null) {
            mAdapter.scrollToBottom();
        }
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtil.canClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_send:
                if (ActionUtil.isLogin()) {
                    ToastUtils.showShort("请先登录!");
                    return;
                }
                String content = mBinding.edit.getText().toString().trim();
                if (ActionUtil.isLogin()) {
                    ToastUtils.showShort("请先登录!");
                    return;
                }
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showShort(R.string.content_empty);
                    return;
                }
                sendMessage();
                mBinding.edit.setText("");
                break;
            case R.id.btn_face:
                faceClick();
                break;
            case R.id.edit:
                clickInput();
                break;
            case R.id.btn_add:
                clickMore();
                break;
            case R.id.btn_img:
                if (ActionUtil.isLogin()) {
                    ToastUtils.showShort("请先登录!");
                    return;
                }
                choosePicture();
                break;
            case R.id.btn_camera:
                if (ActionUtil.isLogin()) {
                    ToastUtils.showShort("请先登录!");
                    return;
                }
                takePicture();
                break;
        }
    }

    private void choosePicture() {
     */
/*   EasyPhotos.createAlbum(this, false, GlideEngine.getInstance())
                .filter(Type.image())
                .setGif(false)
                .setPuzzleMenu(false)
                .setCleanMenu(false)
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
                        new Thread(() -> zipPic(paths)).start();
                    }
                });*//*

    }

    */
/**
     * 调用摄像头拍照
     *//*

    private void takePicture() {
        hideMore();
        hideFace();
    */
/*    EasyPhotos.createCamera(this)
                .isCrop(false)
                .enableSingleCheckedBack(true)
//                .enableSystemCamera(true)
                .setCapture(Capture.IMAGE)
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
                        new Thread(() -> zipPic(paths)).start();
                    }
                });*//*

    }

    private int picWidth;
    private int picHeight;

    private void zipPic(List<String> list) {
        showDialog("发送中...");
        Luban.with(this)
                .load(list)
                .ignoreBy(100)
                .setFocusAlpha(false)
                .filter(path -> !(TextUtils.isEmpty(path)))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.e(TAG, "sendPicToFriend: " + file.getAbsolutePath() + ",size = " + file.length());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(file.getAbsolutePath(), options); // 此时返回的bitmap为null
                        picWidth = options.outWidth;
                        picHeight = options.outHeight;
                        Log.e(TAG, "onSuccess: picWidth = " + picHeight + ",picWidth = " + picWidth);
                        mViewModel.publishChatPicture(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                    }
                }).launch();
    }

    */
/**
     * 点击表情按钮
     *//*

    private void faceClick() {
        if (mBinding.btnFace.isChecked()) {
            hideSoftInput();
            showFace();
        } else {
            mNeedToBottom = false;
            hideFace();
            showSoftInput();
        }
    }

    */
/**
     * 显示软键盘
     *//*

    private void showSoftInput() {
        if (!isSoftInputShowed() && imm != null && mBinding.edit != null) {
            imm.showSoftInput(mBinding.edit, InputMethodManager.SHOW_FORCED);
            mBinding.edit.requestFocus();
        }
    }

    */
/**
     * 显示表情弹窗
     *//*

    private void showFace() {
        if (mChatFaceDialog != null && mChatFaceDialog.isShowing()) {
            return;
        }
        if (mMoreDialog != null) {
            mNeedToBottom = false;
            hideMore();
        }
        if (mFaceView == null) {
            mFaceView = initFaceView();
        }
        onPopupWindowChanged(mFaceViewHeight);
        mChatFaceDialog = new ChatFaceDialog(mBinding.container.getRootView(), mFaceView, true, this);
        mChatFaceDialog.show();
        mNeedToBottom = true;
    }

    private View defaultEmojiView;

    */
/**
     * 初始化表情控件
     *//*

    private View initFaceView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.view_friend_cicle_input, null);
        v.measure(0, 0);
        mFaceViewHeight = v.getMeasuredHeight();
        v.findViewById(R.id.btn_send).setOnClickListener(this);
        final RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        if (defaultEmojiView == null)
            defaultEmojiView = initDefaultEmojiView(radioGroup);
        FrameLayout frameLayout = v.findViewById(R.id.fl_container);
        frameLayout.removeAllViews();
        frameLayout.addView(defaultEmojiView);
        radioGroup.check(10000);
        return v;
    }

    private View initDefaultEmojiView(RadioGroup radioGroup) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.fragment_default_emoji, null);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(10);
        ImChatFacePagerAdapter adapter = new ImChatFacePagerAdapter(this, this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0, pageCount = adapter.getCount(); i < pageCount; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_chat_indicator, radioGroup, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        return view;
    }

    */
/**
     * 初始化更多控件
     *//*

    private View initMoreView() {
        View v = LayoutInflater.from(this).inflate(R.layout.view_chat_more, null);
        mMoreViewHeight = DpUtil.dp2px(200);
        v.findViewById(R.id.btn_img).setOnClickListener(this);
        v.findViewById(R.id.btn_camera).setOnClickListener(this);
//        v.findViewById(R.id.btn_red_pack).setVisibility(View.GONE);
//        v.findViewById(R.id.btn_ziliao).setVisibility(View.GONE);
        return v;
    }

    */
/**
     * 点击输入框
     *//*

    private void clickInput() {
        mNeedToBottom = false;
        hideFace();
        hideMore();
    }

    */
/**
     * 点击更多按钮
     *//*

    private void clickMore() {
        hideSoftInput();
        showMore();
    }

    */
/**
     * 显示更多弹窗
     *//*

    private void showMore() {
        if (mMoreDialog != null && mMoreDialog.isShowing()) {
            return;
        }
        if (mChatFaceDialog != null) {
            mNeedToBottom = false;
            hideFace();
        }
        if (mMoreView == null) {
            mMoreView = initMoreView();
        }
        onPopupWindowChanged(mMoreViewHeight);
        mMoreDialog = new ChatMoreDialog(mBinding.container.getRootView(), mMoreView, true, this);
        mMoreDialog.show();
        mNeedToBottom = true;
    }

    private boolean mNeedToBottom;//是否需要去底部

    */
/**
     * 点击表情图标按钮
     *//*

    @Override
    public void onFaceClick(String str, int faceImageRes) {
        if (mBinding.edit != null) {
            Editable editable = mBinding.edit.getText();
            if (str.equals("<")) {
                editable.insert(mBinding.edit.getSelectionStart(), TextRender.getFaceImageSpan(str, faceImageRes));
            } else {
                editable.append(str);
            }
        }
    }

    */
/**
     * 点击表情删除按钮
     *//*

    @Override
    public void onFaceDeleteClick() {
        if (mBinding.edit != null) {
            int selection = mBinding.edit.getSelectionStart();
            String text = mBinding.edit.getText().toString();
            if (selection > 0) {
                List<String> faces = FaceUtil.getFaceList();
                for (int i = 0; i < faces.size(); i++) {
                    if (text.endsWith(faces.get(i))) {
                        mBinding.edit.getText().delete(selection - faces.get(i).length(), selection);
                        return;
                    }
                }
                String text2 = text.substring(selection - 1, selection);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[", selection);
                    if (start >= 0) {
                        mBinding.edit.getText().delete(start, selection);
                    } else {
                        mBinding.edit.getText().delete(selection - 1, selection);
                    }
                } else {
                    mBinding.edit.getText().delete(selection - 1, selection);
                }

            }
        }
    }

    @Override
    public void onMoreDialogDismiss() {
        onPopupWindowChanged(0);
        mMoreDialog = null;
    }

    private void onKeyBoardChanged(int keyboardHeight) {
        Log.e(TAG, "onKeyBoardChanged: " + new Exception());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBinding.container.getLayoutParams();
        params.setMargins(0, 0, 0, keyboardHeight);
        mBinding.container.setLayoutParams(params);
        chatScrollToBottom();
    }

    @Override
    public void onImageClick(MyImageView imageView, int x, int y, SendMessageResponse bean) {
        Log.e(TAG, "onImageClick: ");
        hideSoftInput();
        List<SendMessageResponse> messageResponses = mAdapter.getList();
        List<SendMessageResponse> temp = new ArrayList<>();
        List<String> gTzimglist = new ArrayList<>();
        int position = 0;
        if (messageResponses != null && messageResponses.size() > 0) {
            for (int i = 0; i < messageResponses.size(); i++) {
                if (messageResponses.get(i).getType() == MessageResponse.TYPE_IMAGE) {
                    gTzimglist.add(messageResponses.get(i).getImg().getImgurl());
                    temp.add(messageResponses.get(i));
                }
            }
            if (temp.size() > 0) {
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).getId() == bean.getId()) {
                        position = i;
                    }
                }
            }
        }
        Log.e(TAG, "onImageClick: position = " + position);
        Intent intent = new Intent(MultipleSendChatRoomActivity.this, PostImageDetailActivity.class);
        intent.putExtra(Extras.IMAGES, (Serializable) gTzimglist);
        intent.putExtra(Extras.POSITION, position);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAvatarLongClick(SendMessageResponse bean) {
    }

    @Override
    public void onMessageDelete(SendMessageResponse bean) {
    }

    @Override
    public void onMessageCopy(SendMessageResponse bean) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", bean.getContents());
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

    @Override
    public void onMessageCollect(SendMessageResponse bean) {

    }

    @Override
    public void onRedPackClick(SendMessageResponse bean, View view) {
    }

    @Override
    public void onAvatarClick(SendMessageResponse bean) {
    }

    public void onPopupWindowChanged(int height) {
        onKeyBoardChanged(height);
        Log.e(TAG, "onPopupWindowChanged: ");
    }

    */
/**
     * 表情弹窗消失的回调
     *//*

    @Override
    public void onFaceDialogDismiss() {
        if (mNeedToBottom) {
            onPopupWindowChanged(0);
        }
        mChatFaceDialog = null;
        mBinding.btnFace.setChecked(false);
    }

    @Override
    public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
        Log.e(TAG, "onStringAvailable: I got some bytes!");
        bb.recycle();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.publishImg.observe(this, this::handlePublishImg);
    }

    private void handlePublishImg(List<String> strings) {
        MultipleSendRequest multipleSendRequest = new MultipleSendRequest();
        if (mType == MultipleSendActivity.TYPE_FRIEND) {
            multipleSendRequest.setFriendIds(friendIds);
        } else if (mType == MultipleSendActivity.TYPE_FRIEND_AND_GROUP) {
            multipleSendRequest.setFriendIds(friendIds);
            multipleSendRequest.setGroupIds(groupIds);
        } else if (mType == MultipleSendActivity.TYPE_GROUP) {
            multipleSendRequest.setGroupIds(groupIds);
        } else {
            return;
        }
        multipleSendRequest.setImg(strings.get(0));
        mViewModel.multipleSend(multipleSendRequest);
    }

    @Override
    public void onReceiveMessageFromServer(BaseWebSocketResponse baseWebSocketResponse) {
        runOnUiThread(() -> {
            dismissDialog();
            if (baseWebSocketResponse.getErrorcode() != BaseWebSocketResponse.ERRORCODE_SUCCESS) {
                showShortToast(baseWebSocketResponse.getMsg());
                return;
            }
            if (baseWebSocketResponse.getAct().equals(Extras.REQUEST_MULTIPLE_SEND_CHAT_ROOM)) {
                Log.e(TAG, "onReceiveMessageFromServer: " + baseWebSocketResponse.getData().toString());
                handleSendMessage(baseWebSocketResponse.getData());
            } else if (baseWebSocketResponse.getAct().equals(Extras.REQUEST_ACTION_SEND_PIC)) {
//                handleSendPic(baseWebSocketResponse.getData());
            }
        });
    }

   */
/* private void handleSendPic(Object o) {
        if (o == null) {
            mBinding.srlRefresh.setNoMoreData(true);
            return;
        }
        List<SendMessageResponse> userResponseList = JSONObject.parseArray(o.toString(), SendMessageResponse.class);
        SendMessageResponse sendMessageResponse = userResponseList.get(0);
        insertConversation(sendMessageResponse);
        if (!isRelatedCurrentAccount(sendMessageResponse))
            return;
        if (!sendMessageResponse.isFromSelf()) {
            mViewModel.readMessage(sendMessageResponse.getId().intValue());
        }
        runOnUiThread(() -> new Handler().post(() -> mAdapter.insertItem(sendMessageResponse)));
    }*//*


    //处理发送消息后的回调
    private void handleSendMessage(Object o) {
        if (o == null) {
            return;
        }
        List<MultipleSendResponse> userResponseList = JSONObject.parseArray(o.toString(), MultipleSendResponse.class);
        MultipleSendResponse multipleSendResponse = userResponseList.get(0);
        SendMessageResponse sendMessageResponse = new SendMessageResponse();
        if (!TextUtils.isEmpty(multipleSendResponse.getContent())) {
            sendMessageResponse.setPid(TYPE_TEXT);
        } else {
            sendMessageResponse.setPid(TYPE_IMAGE);
        }
        sendMessageResponse.setSendId(MMKVUtil.getUserInfo().getId());
        sendMessageResponse.setAddtime(multipleSendResponse.getAdd_time());
        sendMessageResponse.setImg(multipleSendResponse.getImg());
        sendMessageResponse.setContents(multipleSendResponse.getContent());
        sendMessageResponse.setHeadImg(MMKVUtil.getUserInfo().getImg());
        mAdapter.insertItem(sendMessageResponse);
    }
}*/
