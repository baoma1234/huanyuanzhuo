package caixin.android.com.utils;

import android.widget.TextView;

import com.caixin.huanyu.R;

import java.util.concurrent.atomic.AtomicReference;

public class LotteryUtil {
    //根据号码处理为2位数
    public static String getNum(String num) {
        if (num.length() == 1) {
            return "0" + num;
        } else {
            return num;
        }
    }

    //根据号码返回号码的颜色
    public static String getColor(int num) {
        int[] redNum = {1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40, 45, 46};
        int[] greenNum = {5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49};
        int[] blueNum = {3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47, 48};
        AtomicReference<String> color = new AtomicReference<>("1");
        for (int i1 : redNum) {
            if (i1 == num) {
                color.set("1");
                break;
            }
        }
        for (int i2 : greenNum) {
            if (i2 == num) {
                color.set("2");
                break;
            }
        }
        for (int i3 : blueNum) {
            if (i3 == num) {
                color.set("3");
                break;
            }
        }
        return color.get();
    }

    //设置波色
    public static void setColor(String color, TextView textView) {
        switch (color) {
            case "1":
                textView.setBackgroundResource(R.mipmap.red);
                break;
            case "2":
                textView.setBackgroundResource(R.mipmap.green);
                break;
            case "3":
                textView.setBackgroundResource(R.mipmap.blue);
                break;
        }
    }

    //获取波色
    public static String getWaveColor(int waveColor) {
        AtomicReference<String> color = new AtomicReference<>("红波");
        switch (waveColor) {
            case 0:
                color.set("红波");
                break;
            case 1:
                color.set("绿波");
                break;
            case 2:
                color.set("蓝波");
                break;
        }
        return color.get();
    }

    //设置生肖
    public static String getSx(String sx) {
        AtomicReference<String> mSx = new AtomicReference<>("");
        switch (sx) {
            case "1":
                mSx.set("鼠");
                break;
            case "2":
                mSx.set("牛");
                break;
            case "3":
                mSx.set("虎");
                break;
            case "4":
                mSx.set("兔");
                break;
            case "5":
                mSx.set("龙");
                break;
            case "6":
                mSx.set("蛇");
                break;
            case "7":
                mSx.set("马");
                break;
            case "8":
                mSx.set("羊");
                break;
            case "9":
                mSx.set("猴");
                break;
            case "10":
                mSx.set("鸡");
                break;
            case "11":
                mSx.set("狗");
                break;
            case "12":
                mSx.set("猪");
                break;
        }
        return mSx.get();
    }

    //返回int类型的生肖
    public static int getIntSx(String sx) {
        AtomicReference<Integer> mSx = new AtomicReference<>(1);
        switch (sx) {
            case "鼠":
                mSx.set(1);
                break;
            case "牛":
                mSx.set(2);
                break;
            case "虎":
                mSx.set(3);
                break;
            case "兔":
                mSx.set(4);
                break;
            case "龙":
                mSx.set(5);
                break;
            case "蛇":
                mSx.set(6);
                break;
            case "马":
                mSx.set(7);
                break;
            case "羊":
                mSx.set(8);
                break;
            case "猴":
                mSx.set(9);
                break;
            case "鸡":
                mSx.set(10);
                break;
            case "狗":
                mSx.set(11);
                break;
            case "猪":
                mSx.set(12);
                break;
        }
        return mSx.get();
    }

    //设置五行
    public static String getWx(String wx) {
        AtomicReference<String> mWx = new AtomicReference<>("");
        switch (wx) {
            case "1":
                mWx.set("/金");
                break;
            case "2":
                mWx.set("/木");
                break;
            case "3":
                mWx.set("/水");
                break;
            case "4":
                mWx.set("/火");
                break;
            case "5":
                mWx.set("/土");
                break;
        }
        return mWx.get();
    }
}