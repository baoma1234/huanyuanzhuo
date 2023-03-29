package caixin.android.com.view.activity;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;


import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityShakeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.utils.LotteryUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.ShakeListener;

public class ShakeActivity extends BaseActivity<ActivityShakeBinding, BaseViewModel> {

    private SoundPool soundPool;  //定义音效
    private int soundId;
    private Vibrator vibrator;//定义振动器
    private ShakeListener mShakeListener = null;//定义传感器事件
    private Handler mHandler;
    private List<Integer> lotteryList = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_shake;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }


    //延迟消息
    private void delayMessage() {
        mHandler = new Handler(message -> {
            vibrator.cancel();
            Random rand = new Random();
            for (int i = 0; i < 49; i++) {
                int lotteryNum = rand.nextInt(49) + 1;
                if (!lotteryList.contains(lotteryNum)) {
                    lotteryList.add(lotteryNum);
                }
                if (lotteryList.size() == 6) {
                    break;
                }
            }
            MMKVUtil.setShakeList(lotteryList);
            mBinding.llShake.setClickable(false);
            setText(0);
            mBinding.llLottery.setVisibility(View.VISIBLE);
            mBinding.tvLottery.setVisibility(View.GONE);
            setData(lotteryList);
            return true;
        });
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    private void setData(List<Integer> list) {
        //中奖号码
        mBinding.tvOne.setText(String.valueOf(list.get(0)));
        mBinding.tvTwo.setText(String.valueOf(list.get(1)));
        mBinding.tvThree.setText(String.valueOf(list.get(2)));
        mBinding.tvFour.setText(String.valueOf(list.get(3)));
        mBinding.tvFive.setText(String.valueOf(list.get(4)));
        mBinding.tvSix.setText(String.valueOf(list.get(5)));
        //设置波色
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(0)), mBinding.tvOne);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(1)), mBinding.tvTwo);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(2)), mBinding.tvThree);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(3)), mBinding.tvFour);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(4)), mBinding.tvFive);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(5)), mBinding.tvSix);
    }

    //定义震动
    public void startVibrato() {
        vibrator.vibrate(new long[]{500, 200, 500, 200}, -1);  //-1是不循环的意思
    }

    //当界面停止时停止监听器
    @Override
    protected void onStop() {
        super.onStop();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

    //当界面销毁时，停止监听器
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
        if (mHandler != null) {
            mHandler.removeMessages(1);
        }
    }

    //启动动画
    public void startAnim() {
        //摇一摇图片向上并返回的动画
        AnimationSet animUp = new AnimationSet(true);
        //创建向上动画
        TranslateAnimation translateAnimation0 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -0.5f);
        translateAnimation0.setDuration(1000);                //设置持续时间为1秒

        //创建返回动画
        TranslateAnimation translateAnimation1 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, +0.5f);
        translateAnimation1.setDuration(1000);                //设置持续时间为1秒
        translateAnimation1.setStartOffset(1000);                //向上动画结束后执行返回动画
        animUp.addAnimation(translateAnimation0);
        animUp.addAnimation(translateAnimation1);
        mBinding.shakeUp.startAnimation(animUp);                        //启动向上并返回动画

        //摇一摇图片向下并返回的动画
        AnimationSet animDown = new AnimationSet(true);
        //创建向下动画
        TranslateAnimation translateAnimation2 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, +0.5f);
        translateAnimation2.setDuration(1000);                //设置持续时间为1秒

        //创建返回动画
        TranslateAnimation translateAnimation3 = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -0.5f);
        translateAnimation3.setDuration(1000);                //设置持续时间为1秒
        translateAnimation3.setStartOffset(1000);                //向下动画结束后执行返回动画
        animDown.addAnimation(translateAnimation2);
        animDown.addAnimation(translateAnimation3);
        mBinding.shakeDown.startAnimation(animDown);                        //启动向下并返回动画
    }

    private void setText(int size) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String s1 = "您本期还可以摇";
        ssb.append(s1);
        String times = String.valueOf(size);
        ssb.append(times);
        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.main_red)), s1.length(), times.length() + s1.length(), 0);
        ssb.append("次");
        mBinding.tvTips.setText(ssb);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.ivClose.setOnClickListener(v -> finish());
        mBinding.llShake.setOnClickListener(v -> {
            startAnim();            //启动动画
            mShakeListener.stop(); //停止监听
            soundPool.play(soundId, 1, 1, 0, 0, 1);
            startVibrato();//开始震动
            delayMessage();//延时消息
        });

        //获取震动器服务
        vibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)                   //设置音效使用场景
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)        //设置音效类型
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)            //设置音效池的属性
                .setMaxStreams(10).build();                     //设置最多可以容纳10个音频流
        soundId = soundPool.load(this, R.raw.shake, 1);
        //实现了摇一摇监听类
        if (MMKVUtil.getShakeList() == null || MMKVUtil.getShakeList().size() == 0) {
            setText(1);
            mShakeListener = new ShakeListener(this);
            mShakeListener.setOnShakeListener(() -> {
                startAnim();            //启动动画
                mShakeListener.stop(); //停止监听
                soundPool.play(soundId, 1, 1, 0, 0, 1);
                startVibrato();     //开始震动
                mBinding.llShake.setClickable(false);
                delayMessage();     //延时消息
            });
        } else {
            mBinding.llShake.setClickable(false);
            setText(0);
            mBinding.llLottery.setVisibility(View.VISIBLE);
            mBinding.tvLottery.setVisibility(View.GONE);
            setData(MMKVUtil.getShakeList());
        }
    }

    @Override
    public void initViewObservable() {

    }


}