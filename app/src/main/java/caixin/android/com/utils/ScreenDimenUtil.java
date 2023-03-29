package caixin.android.com.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import caixin.android.com.Application;

import java.lang.reflect.Field;

/**
 * Created by cxf on 2017/10/30.
 * 获取屏幕尺寸
 */

public class ScreenDimenUtil {

    private Resources mResources;
    private int mStatusBarHeight;//状态栏高度
    private int mContentHeight;
    private int mScreenWdith;
    private int mScreenHeight;


    private static ScreenDimenUtil sInstance;

    private ScreenDimenUtil() {
        mResources = Application.getInstance().getResources();
        DisplayMetrics dm = mResources.getDisplayMetrics();
        mScreenWdith = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        //网上找的办法，使用反射在DecoderView未绘制出来之前计算状态栏的高度
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            mStatusBarHeight = mResources.getDimensionPixelSize(x);
            mContentHeight = mScreenHeight - mStatusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ScreenDimenUtil getInstance() {
        if (sInstance == null) {
            synchronized (ScreenDimenUtil.class) {
                if (sInstance == null) {
                    sInstance = new ScreenDimenUtil();
                }
            }
        }
        return sInstance;
    }

    public static int dp2px(float dpValue) {
        return (int) (dpValue * Application.getInstance().getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int px2dip(Context context, Float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5);
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    public int getScreenWidth() {
        return mScreenWdith;
    }

    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public int getScreenHeight() {
        return mScreenHeight;
    }

    /**
     * 获取ContentView的高度
     *
     * @return
     */
    public int getContentHeight() {
        return mContentHeight;
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        return mStatusBarHeight;
    }

}
