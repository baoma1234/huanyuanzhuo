package caixin.android.com.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.caixin.huanyu.R;

import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.http.RetrofitManager;
import caixin.android.com.http.basic.service.UserCenterService;
import caixin.android.com.utils.DpUtil;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.view.activity.RedPackResultActivity;
import caixin.android.com.widget.AbsDialogFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static caixin.android.com.entity.base.BaseResponse.ERRORCODE_SUCCESS;

/**
 * Created by cxf on 2018/11/21.
 * 抢红包弹窗
 */

public class LiveRedPackRobDialogFragment extends AbsDialogFragment implements View.OnClickListener {

    private View mRobGroup;
    private View mRobGroup1;
    private View mWinGroup;
    private View mResultGroup;
    private ImageView mAvatar;
    private TextView mName;
    private View mText;
    private TextView mMsg;
    private TextView mWinTip;
    private TextView mWinCoin;
    private ActionListener mActionListener;
    private boolean mNeedDelay;
    private int pgid;


    public void setPgid(int pgid) {
        this.pgid = pgid;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_live_red_pack_rob;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.dp2px(300);
        params.height = DpUtil.dp2px(390);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (messageResponse == null) {
            return;
        }

        dialog.setOnDismissListener(dialog -> {
            if (mActionListener != null)
                mActionListener.onDismiss();
        });
        mRobGroup = mRootView.findViewById(R.id.rob_group);
        mRobGroup1 = mRootView.findViewById(R.id.rob_group_1);
        mResultGroup = mRootView.findViewById(R.id.result_group);
        mWinGroup = mRootView.findViewById(R.id.win_group);
        mText = mRootView.findViewById(R.id.text);
        mAvatar = mRootView.findViewById(R.id.avatar);
        mName = mRootView.findViewById(R.id.name);
        mMsg = mRootView.findViewById(R.id.msg);
        mWinTip = mRootView.findViewById(R.id.win_tip);
        mWinCoin = mRootView.findViewById(R.id.win_coin);
        mRootView.findViewById(R.id.btn_rob).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_detail).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_detail_2).setOnClickListener(this);
        mRootView.findViewById(R.id.iv_group_win_close).setOnClickListener(this);
        mRootView.findViewById(R.id.iv_group1_win_close).setOnClickListener(this);
        ImgLoader.GlideLoadCircle(mAvatar, messageResponse.getHeadImg(), R.mipmap.img_user_head);
        mName.setText(messageResponse.getNikeName());
        startRob();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rob:
                robRedPack();
                break;
            case R.id.btn_detail:
            case R.id.btn_detail_2:
                forwardRobDetail();
                break;
            case R.id.iv_group1_win_close:
            case R.id.iv_group_win_close:
                dismiss();
                break;
        }
    }

    /**
     * 查看领取详情
     */
    private void forwardRobDetail() {
        Intent intent = new Intent(getContext(), RedPackResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("pgid", pgid);
        bundle.putString("user", messageResponse.getNikeName());
        intent.putExtras(bundle);
        startActivity(intent);
        mNeedDelay = true;
        dismiss();
    }

    /**
     * 抢红包
     */
    @SuppressLint("CheckResult")
    private void robRedPack() {
        if (messageResponse == null) {
            return;
        }
        RetrofitManager.getInstance().getService(UserCenterService.class)
                .robRedPack( pgid, "grabRedbag", MMKVUtil.getUserInfo().getToken())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseResponse -> {
                    if (baseResponse.getErrorcode() == ERRORCODE_SUCCESS) {
                        onWin(baseResponse.getMsg());
                        if (mActionListener != null) {
                            mActionListener.onRobSuccess();
                        }
                    } else {
                        onNotWin(baseResponse.getMsg());
                    }
                }, throwable -> {
                    Log.e(TAG, "accept: " + throwable.getMessage());
                    onNotWin(throwable.getMessage());
                });
    }

    private void onWin(String winCoin) {
        if (mActionListener != null) {
            mActionListener.hide();
        }
        if (mText != null) {
            mText.clearAnimation();
        }
        if (mRobGroup != null && mRobGroup.getVisibility() == View.VISIBLE) {
            mRobGroup.setVisibility(View.INVISIBLE);
        }
        if (mWinGroup != null && mWinGroup.getVisibility() != View.VISIBLE) {
            mWinGroup.setVisibility(View.VISIBLE);
        }
        if (mWinCoin != null) {
            mWinCoin.setText(winCoin);
        }
        if (mWinTip != null) {
            mWinTip.setText(String.format(getContext().getResources().getString(R.string.red_pack_16), messageResponse.getNikeName(), "随机"));
        }
    }

    /**
     * 未抢到
     */
    private void onNotWin(String msg) {
        if (mText != null) {
            mText.clearAnimation();
        }
        if (mRobGroup1 != null && mRobGroup1.getVisibility() == View.VISIBLE) {
            mRobGroup1.setVisibility(View.INVISIBLE);
        }
        if (mResultGroup != null && mResultGroup.getVisibility() != View.VISIBLE) {
            mResultGroup.setVisibility(View.VISIBLE);
        }
        if (mMsg != null) {
            mMsg.setText(msg);
        }
    }

    private void startRob() {
        if (mRobGroup1 != null && mRobGroup1.getVisibility() != View.VISIBLE) {
            mRobGroup1.setVisibility(View.VISIBLE);
            ScaleAnimation animation = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);
            animation.setRepeatCount(-1);
            animation.setRepeatMode(Animation.REVERSE);
            mText.startAnimation(animation);
        }
    }


    @Override
    public void onDestroy() {
        if (mActionListener != null) {
            mActionListener.show(mNeedDelay);
        }
        mActionListener = null;
        if (mText != null) {
            mText.clearAnimation();
        }
        super.onDestroy();
    }

    private static final String TAG = "LiveRedPackRobDialogFra";

    private SendMessageResponse messageResponse;

    public void setMessageResponse(SendMessageResponse bean) {
        this.messageResponse = bean;
    }

    public interface ActionListener {

        void show(boolean needDelay);

        void hide();

        void onDismiss();

        void onRobSuccess();
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }
}
