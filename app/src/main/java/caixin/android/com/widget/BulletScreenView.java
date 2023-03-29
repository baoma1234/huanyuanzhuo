package caixin.android.com.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.caixin.huanyu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 弹幕控件
 *
 * @author wkk
 */
public class BulletScreenView extends FrameLayout {

    private int lv = 0;//滚动弹幕共有几行可用
    private int maxLv = 0;//最多可以有几行
    private int height;//每一行的高度
    private Paint paint = new Paint();
    @SuppressLint("UseSparseArrays")
    private Map<Integer, Temporary> map = new HashMap<>();//每一行最后的动画
    private List<Temporary> list = new ArrayList<>();//存有当前屏幕上的所有动画
    @SuppressLint("UseSparseArrays")
    private Map<Integer, CountDown> tbMap = new HashMap<>();//key 行数
    private List<CountDown> countDownList = new ArrayList<>();//缓存所有倒计时
    private int textSize = 14;
    private boolean stop = false;//暫停功能


    public BulletScreenView(Context context) {
        this(context, null);
    }

    public BulletScreenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //设置文字大小
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, getContext().getResources().getDisplayMetrics()));
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = (int) (paint.measureText("我") + 10);//测量一行的高度
        lv = getHeight() / height;//最多可以存在多少行
        maxLv = lv;
        lv = maxLv / 2;//限制滚动弹幕位置
    }


    //添加一条滚动弹幕
    public void add(String string) {
        if (stop) {
            return;
        }
        //创建控件
        final TextView textView = new TextView(getContext());
        textView.setText(string);
        textView.setTextSize(textSize);
        textView.setTextColor(Color.BLACK);
        textView.setPadding(5,0,5,0);
        textView.setGravity(Gravity.CENTER);
        textView.setBackground(getResources().getDrawable(R.drawable.shape_tv_chat_bg));
        addView(textView);

        //找到合适插入到行数
        float minPosition = Integer.MAX_VALUE;//最小的位置
        int minLv = 0;//最小位置的行数
        for (int i = 0; i < lv; i++) {
            Temporary temporary = map.get(i);//获取到该行最后一个动画
            if (temporary == null) {
                minLv = i;
                break;
            }
            float p = (float) map.get(i).animation.getAnimatedValue() + map.get(i).viewLength;//获取位置
            if (minPosition > p) {
                minPosition = p;
                minLv = i;
            }
        }


        //设置行数
        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.topMargin = height * minLv;
        layoutParams.width = (int) paint.measureText(string)+50;
        textView.setLayoutParams(layoutParams);

        //设置动画
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "translationX", getWidth(),
                -paint.measureText(string));
        objectAnimator.setDuration(7000);//设置动画时间
        objectAnimator.setInterpolator(new LinearInterpolator());//设置差值器

        //将弹幕相关数据缓存起来
        final Temporary temporary = new Temporary(objectAnimator);
        temporary.time = 0;
        temporary.viewLength = paint.measureText(string);
        list.add(temporary);
        map.put(minLv, temporary);

        //动画结束监听
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!stop) {
                    removeView(textView);//移除控件
                    list.remove(temporary);//移除缓存
                }
            }
        });
        objectAnimator.start();//开启动画
    }


    //添加一条弹幕
    public void add(String str, Type type) {
        if (stop) {
            return;
        }
        if (type == Type.ROLL) {
            add(str);
            return;
        }
        int minLv = 0;
        View view = null;
        switch (type) {
            case TOP: {
                final TextView textView = new TextView(getContext());
                textView.setText(str);
                textView.setTextSize(textSize);
                textView.setTextColor(Color.GREEN);

                //确定位置
                long minTime = Integer.MAX_VALUE;
                for (int i = 0; i < lv; i++) {
                    CountDown countDown = tbMap.get(i);
                    if (countDown == null) {
                        minLv = i;
                        break;
                    }
                    if (countDown.over) {
                        minLv = i;
                        break;
                    }
                    //剩余时间最小的
                    long st = countDown.getSurplusTime();
                    if (minTime > st) {
                        minTime = st;
                        minLv = i;
                    }
                }

                LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
                layoutParams.topMargin = height * minLv;
                textView.setLayoutParams(layoutParams);
                addView(textView);
                view = textView;
            }
            break;
            case BOTTOM: {
                final TextView textView = new TextView(getContext());
                textView.setText(str);
                textView.setTextSize(textSize);
                textView.setTextColor(Color.RED);

                long minTime = Integer.MAX_VALUE;
                for (int i = maxLv - 1; i >= 0; i--) {
                    CountDown countDown = tbMap.get(i);
                    if (countDown == null) {
                        minLv = i;
                        break;
                    }
                    if (countDown.over) {
                        minLv = i;
                        break;
                    }
                    //剩余时间最小的
                    long st = countDown.getSurplusTime();
                    if (minTime > st) {
                        minTime = st;
                        minLv = i;
                    }
                }

                LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
                layoutParams.bottomMargin = height * (maxLv - minLv);
                textView.setLayoutParams(layoutParams);
                addView(textView);
                view = textView;
            }
            break;
        }

        CountDown countDown = new CountDown(view);
        tbMap.put(minLv, countDown);
        countDownList.add(countDown);
    }

    //停止动画
    public void stop() {
        if (stop) {
            return;
        }
        stop = true;
        for (int i = 0; i < list.size(); i++) {
            Temporary temporary = list.get(i);
            temporary.time = temporary.animation.getCurrentPlayTime();
            temporary.animation.cancel();//会调用结束接口
        }
        for (CountDown countDown : countDownList) {
            countDown.stop();
        }
    }

    //重新开始
    public void restart() {
        if (!stop) {
            return;
        }
        stop = false;
        for (Temporary temporary : list) {
            temporary.animation.start();
            temporary.animation.setCurrentPlayTime(temporary.time);
        }
        for (CountDown countDown : countDownList) {
            countDown.restart();
        }
    }

    //清除全部
    public void clear() {
        map.clear();
        tbMap.clear();
        list.clear();
        countDownList.clear();
        removeAllViews();
    }

    private static class Temporary {//方便缓存动画
        long time;
        float viewLength;
        ObjectAnimator animation;

        Temporary(ObjectAnimator animation) {
            this.animation = animation;
        }
    }

    public enum Type {//弹幕类型
        TOP,//顶部弹幕
        BOTTOM,//底部弹幕
        ROLL//滚动弹幕
    }

    private class CountDown {//为了方便暂停,所以写了这个类用于顶部和底部的弹幕暂停恢复
        long startTime;
        private long surplusTime = 0;//暂停过后的剩余时间
        long sustain = 1000 * 3;//持续时间
        boolean over = false;//任务是否执行完成
        Runnable runnable;

        CountDown(final View view) {
            startTime = System.currentTimeMillis();
            runnable = new Runnable() {
                @Override
                public void run() {
                    countDownList.remove(CountDown.this);
                    removeView(view);
                    over = true;
                }
            };
            postDelayed(runnable, 3000);//直接开始
        }

        //暂停当前倒计时任务
        void stop() {
            if (over) {
                return;
            }
            surplusTime = sustain - (System.currentTimeMillis() - startTime);//剩余时间=需要显示的时间 - (当前时间 - 开始时间)
            sustain = surplusTime;
            removeCallbacks(runnable);//暂停移除任务
        }

        //恢复倒计时任务
        void restart() {
            if (over) {
                return;
            }
            startTime = System.currentTimeMillis();//重置开始时间
            postDelayed(runnable, surplusTime);
        }

        //获取剩余时间
        long getSurplusTime() {
            surplusTime = sustain - (System.currentTimeMillis() - startTime);
            sustain = surplusTime;
            return surplusTime;
        }
    }

}
