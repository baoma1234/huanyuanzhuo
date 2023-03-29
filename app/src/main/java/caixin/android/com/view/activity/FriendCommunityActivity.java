package caixin.android.com.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityMyFriendCommunityBinding;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import caixin.android.com.adapter.FriendNewsAdapter;
import caixin.android.com.adapter.ImChatFacePagerAdapter;
import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.ActivityEntity;
import caixin.android.com.entity.CollectRequest;
import caixin.android.com.entity.DigResponse;
import caixin.android.com.entity.FriendNewsEntity;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.utils.AdvertisingUtil;
import caixin.android.com.utils.ClickUtil;
import caixin.android.com.utils.FaceUtil;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.KeyBoardHeightUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.utils.TextRender;
import caixin.android.com.viewmodel.FriendCommunityViewModel;
import caixin.android.com.widget.ChatFaceDialog;
import caixin.android.com.widget.KeyBoardHeightChangeListener;
import caixin.android.com.widget.OnFaceClickListener;
import caixin.android.com.widget.ait.MyTextSpan;

public class FriendCommunityActivity extends BaseActivity<ActivityMyFriendCommunityBinding, FriendCommunityViewModel> implements KeyBoardHeightChangeListener, OnFaceClickListener, ChatFaceDialog.ActionListener, View.OnClickListener {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_my_friend_community;
    }

    @Override
    public FriendCommunityViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(FriendCommunityViewModel.class);
    }

    private InputMethodManager imm;


    private static final String TAG = "MyFriendCommunityActivi";

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtils.immersive(this, this.getResources().getColor(R.color.colorPrimary));
        mBinding.ivClose.setOnClickListener(v -> finish());
        mBinding.ivSendMyNews.setOnClickListener(v -> startActivity(SendMyNewsActivity.class));
        mBinding.srlRefresh.setOnRefreshListener(refreshLayout -> {
            mNextPage = 1;
            refreshLayout.finishRefresh(5000); //这个记得设置，否则一直转圈
            mViewModel.getFriendNews(mNextPage);
            mViewModel.getActivityList();
            isLoadMore = false;
        });
        mBinding.srlRefresh.setRefreshFooter(new ClassicsFooter(this));
        mBinding.srlRefresh.setOnLoadMoreListener(refreshLayout -> {
            mViewModel.getFriendNews(mNextPage);
            isLoadMore = true;
        });
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mKeyBoardHeightUtil = new KeyBoardHeightUtil(FriendCommunityActivity.this, findViewById(android.R.id.content), this);
        mBinding.container.postDelayed(() -> {
            if (mKeyBoardHeightUtil != null) {
                mKeyBoardHeightUtil.start();
            }
        }, 500);

        mBinding.rvNews.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideCommentInput();
                return false;
            }
        });

        mBinding.tvSend.setOnClickListener(this);
        mBinding.btnFace.setOnClickListener(this);
        mBinding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickInput();
            }
        });
        mBinding.edit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendComment();
                return true;
            }
            return false;
        });
        mBinding.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before == 1 && count == 0) {
                    MyTextSpan[] spans = mBinding.edit.getText().getSpans(0, mBinding.edit.getText().length(), MyTextSpan.class);
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
        mBinding.edit.setOnTouchListener((v, event) -> {
            clickInput();
            return false;
        });

        friendNewsAdapter = new FriendNewsAdapter(null);
        friendNewsAdapter.setMyOnclick(new FriendNewsAdapter.MyOnClick() {
            @Override
            public void onDigClick(boolean isCancel, FriendNewsEntity friendNewsEntity, int position) {
                mViewModel.dig(friendNewsEntity.getId());
                mDigPosition = position;
            }

            @Override
            public void onCommentClick(FriendNewsEntity friendNewsEntity, int position) {
                mCommentPosition = position;
                isReplyComment = false;
                if (mBinding.llCommentSend.getVisibility() == View.VISIBLE) {
                    hideCommentInput();
                } else {
                    showCommentInput();
                    commentFriendNewsEntity = friendNewsEntity;
                }
            }

            @Override
            public void onCommentReplay(FriendNewsEntity item, int position, int toUid) {
                mCommentPosition = position;
                isReplyComment = true;
                replyCommentId = toUid;
                if (mBinding.llCommentSend.getVisibility() == View.VISIBLE) {
                    hideCommentInput();
                } else {
                    showCommentInput();
                    commentFriendNewsEntity = item;
                }
            }

            @Override
            public void onCommentDelete(FriendNewsEntity item, int position, FriendNewsEntity.CommentsBean commentsBean) {
                mCommentPosition = position;
                deleteComment = commentsBean;
                mViewModel.deleteFriendCicleComment(commentsBean.getIdX());
            }

            @Override
            public void onCommentCopy(FriendNewsEntity item, int position, FriendNewsEntity.CommentsBean commentsBean) {
                AdvertisingUtil.getInstance().Clipboard(FriendCommunityActivity.this, commentsBean.getContentX());
                showShortToast(getResources().getString(R.string.copy_success));
            }

            @Override
            public void onCollectClick(FriendNewsEntity item) {
                CollectRequest collectRequest = new CollectRequest();
                collectRequest.setImgs(item.getImgs());
                collectRequest.setText(item.getContent());
                collectRequest.setToken(MMKVUtil.getToken());
                collectRequest.setToid(item.getUid());
                mViewModel.collectComment(collectRequest);
            }

            @Override
            public void onDeleteFCClick(FriendNewsEntity item, int position) {
                mDeletePosition = position;
                mViewModel.deleteFriendCicle(item.getId());
            }
        });
        mBinding.rvNews.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvNews.setAdapter(friendNewsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNextPage = 1;
        mBinding.srlRefresh.autoRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mKeyBoardHeightUtil.release();
    }

    private boolean mNeedToBottom;//是否需要去底部

    /**
     * 点击输入框
     */
    private void clickInput() {
        mNeedToBottom = false;
        hideFace();
    }

    protected KeyBoardHeightUtil mKeyBoardHeightUtil;
    private ChatFaceDialog mChatFaceDialog;//表情弹窗

    @Override
    public void onKeyBoardHeightChanged(int visibleHeight, int keyboardHeight) {
        if ((keyboardHeight == 0 && isPopWindowShowed())) {
            return;
        }
        onKeyBoardChanged(0);
    }

    public boolean isPopWindowShowed() {
        return mChatFaceDialog != null;
    }

    /**
     * 隐藏表情弹窗
     */
    private boolean hideFace() {
        if (mChatFaceDialog != null) {
            mChatFaceDialog.dismiss();
            mChatFaceDialog = null;
            return true;
        }
        return false;
    }

    private View mFaceView;//表情控件

    /**
     * 显示表情弹窗
     */
    private void showFace() {
        if (mChatFaceDialog != null && mChatFaceDialog.isShowing()) {
            return;
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
    private int mFaceViewHeight;//表情控件高度

    /**
     * 初始化表情控件
     */
    private View initFaceView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.view_friend_cicle_input, null);
        v.measure(0, 0);
        mFaceViewHeight = v.getMeasuredHeight();
        v.findViewById(R.id.btn_send).setOnClickListener(this);
//        ((RadioButton) v.findViewById(R.id.rbtn_emoji_default)).setChecked(true);
        final RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        if (defaultEmojiView == null)
            defaultEmojiView = initDefaultEmojiView(radioGroup);
        FrameLayout frameLayout = v.findViewById(R.id.fl_container);
        frameLayout.removeAllViews();
        frameLayout.addView(defaultEmojiView);
        radioGroup.check(10000);
        return v;
    }

    /**
     * 点击表情图标按钮
     */
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

    /**
     * 点击表情删除按钮
     */
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

    private void onKeyBoardChanged(int keyboardHeight) {
        Log.e(TAG, "onKeyBoardChanged: " + keyboardHeight);
        if (mBinding.llCommentSend.getVisibility() == View.VISIBLE) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mBinding.llCommentSend.getLayoutParams();
            params.setMargins(0, 0, 0, keyboardHeight);
            mBinding.llCommentSend.setLayoutParams(params);
        }
    }

    public void onPopupWindowChanged(int height) {
        onKeyBoardChanged(height);
    }

    /**
     * 表情弹窗消失的回调
     */
    public void onFaceDialogDismiss() {
        if (mNeedToBottom) {
            onPopupWindowChanged(0);
        }
        mChatFaceDialog = null;
        mBinding.btnFace.setChecked(false);
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getFriendNews.observe(this, this::handleGetFriendNews);
        mViewModel.uc.dig.observe(this, this::handleDig);
        mViewModel.uc.deleteCF.observe(this, this::handleDeleteFC);
        mViewModel.uc.sendFCComment.observe(this, this::handleSendFCComment);
        mViewModel.uc.deleteCFComment.observe(this, this::handleDeleteFCComment);
        mViewModel.uc.addCollect.observe(this, this::handleAddCollect);
        mViewModel.uc.getActivityList.observe(this, this::handleGetActivityList);
    }

    private void handleGetActivityList(List<ActivityEntity> o) {
        mBinding.xbBanner.setAutoPlayAble(o.size() > 1);
        mBinding.xbBanner.setBannerData(o);
        mBinding.xbBanner.loadImage((banner, model, view, position) -> ImgLoader.GlideLoad((ImageView) view, ((ActivityEntity) model).getImg(), R.mipmap.web_default));
        mBinding.xbBanner.setOnItemClickListener((banner, model, view, position) -> {
            try {
                startActivity(new Intent().setAction("android.intent.action.VIEW").setData(Uri.parse(((ActivityEntity) model).getHref())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleAddCollect(Object o) {
        showShortToast(getResources().getString(R.string.collect_success));
    }

    private void handleDeleteFCComment(Object o) {
        friendNewsAdapter.getData().get(mCommentPosition).getComments().remove(deleteComment);
        friendNewsAdapter.notifyItemChanged(mCommentPosition);
    }

    private void handleSendFCComment(FriendNewsEntity.CommentsBean o) {
        friendNewsAdapter.getData().get(mCommentPosition).getComments().add(o);
        friendNewsAdapter.notifyItemChanged(mCommentPosition);
        hideCommentInput();
    }

    private void handleDeleteFC(Object o) {
        friendNewsAdapter.remove(mDeletePosition);
    }

    private void handleDig(DigResponse o) {
        friendNewsAdapter.getData().get(mDigPosition).getThumbs();
        if (o.getAct().equals(DigResponse.ADD_DIG)) {
            friendNewsAdapter.getData().get(mDigPosition).getThumbs().add(new FriendNewsEntity.UserInfoBean(MMKVUtil.getUserInfo().getNikeName(), MMKVUtil.getUserInfo().getImg(), MMKVUtil.getUserInfo().getId()));
            friendNewsAdapter.notifyItemChanged(mDigPosition);
        } else {
            if (friendNewsAdapter.getData().get(mDigPosition).getThumbs() != null && !friendNewsAdapter.getData().get(mDigPosition).getThumbs().isEmpty() && friendNewsAdapter.getData().get(mDigPosition).getThumbs().size() > 0) {
                int position = -1;
                for (int i = 0; i < friendNewsAdapter.getData().get(mDigPosition).getThumbs().size(); i++) {
                    if (friendNewsAdapter.getData().get(mDigPosition).getThumbs().get(i).getId() == MMKVUtil.getUserInfo().getId()) {
                        position = i;
                        break;
                    }
                }
                if (position != -1) {
                    friendNewsAdapter.getData().get(mDigPosition).getThumbs().remove(position);
                }
            }
            friendNewsAdapter.notifyItemChanged(mDigPosition);
        }
    }

    private int mNextPage = 1;
    private boolean isLoadMore = false;
    private FriendNewsAdapter friendNewsAdapter;

    private int mDigPosition;
    private FriendNewsEntity commentFriendNewsEntity;
    private int mDeletePosition;
    private int mCommentPosition;
    private boolean isReplyComment = false;
    private int replyCommentId;
    private FriendNewsEntity.CommentsBean deleteComment;

    private void handleGetFriendNews(List<FriendNewsEntity> o) {
        mBinding.srlRefresh.finishRefresh();
        mBinding.srlRefresh.finishLoadMore();
        if (o == null || o.size() == 0)
            return;
        if (!isLoadMore) {
            mNextPage = 1;
            friendNewsAdapter.setNewData(o);
            mBinding.srlRefresh.setEnableLoadMore(true);
        } else {
            friendNewsAdapter.addData(o);
        }
        mNextPage++;
        if (o.size() < 15) {
            mBinding.srlRefresh.setEnableLoadMore(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtil.canClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_send:
            case R.id.tv_send:
                sendComment();
                break;
            case R.id.btn_face:
                faceClick();
                break;
            case R.id.edit:
                clickInput();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideCommentInput();
    }

    /**
     * 点击表情按钮
     */
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

    /**
     * 隐藏键盘
     */
    private boolean hideSoftInput() {
        imm.hideSoftInputFromWindow(mBinding.edit.getWindowToken(), 0); //强制隐藏键盘
        return false;
    }

    /**
     * 显示软键盘
     */
    private void showSoftInput() {
//        hideSoftInput();
        if (!isSoftInputShowed() && imm != null && mBinding.edit != null) {
            Log.e(TAG, "showSoftInput: !isSoftInputShowed() && imm != null && mBinding.edit != null = true");
            mBinding.edit.requestFocus();
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean isSoftInputShowed() {
        if (mKeyBoardHeightUtil != null) {
            return mKeyBoardHeightUtil.isSoftInputShowed();
        }
        return false;
    }

    public void showCommentInput() {
        Log.e(TAG, "showCommentInput: isSoftInputShowed() = " + isSoftInputShowed());
        mBinding.llCommentSend.setVisibility(View.VISIBLE);
        mBinding.edit.setText("");
        showSoftInput();
    }

    public void hideCommentInput() {
        Log.e(TAG, "hideCommentInput: isSoftInputShowed() = " + isSoftInputShowed());
        hideSoftInput();
        hideFace();
        onKeyBoardChanged(0);
        mBinding.llCommentSend.setVisibility(View.GONE);
    }

    public void sendComment() {
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
        if (isReplyComment) {
            mViewModel.sendFCComment(commentFriendNewsEntity.getId(), mBinding.edit.getText().toString(), replyCommentId);
        } else {
            mViewModel.sendFCComment(commentFriendNewsEntity.getId(), mBinding.edit.getText().toString(), 0);
        }
    }
}
