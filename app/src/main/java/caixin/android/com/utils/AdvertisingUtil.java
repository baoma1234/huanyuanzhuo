package caixin.android.com.utils;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.net.URLDecoder;

/**
 * auther：tom
 * time: 2019/8/8
 * description:
 */
public class AdvertisingUtil {


    static AdvertisingUtil logpre;

    public static synchronized AdvertisingUtil getInstance() {
        if (logpre == null) {
            logpre = new AdvertisingUtil();
        }
        return logpre;
    }


    /**
     * 跳到外部网页
     */
    public void SkipAdWebView(Context context, String url) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //做一些处理
            getVersion8Up(context,url);
        } else{
            //在版本低于此的时候，做一些处理
            getVersion8Down(context,url);
        }
//        Intent minent=new Intent(context, WebPageAdActivity.class);

    }

    /**
     * 8.0以上的
     * @param context
     * @param url
     */
    private  void getVersion8Up(Context context, String url){

         if (!TextUtils.isEmpty(url)){
            url=url.toLowerCase();
         }

        Uri content_url;
        if (TextUtils.isEmpty(url)||!url.contains("http")) {
            ToastUtils.show("网址错误！");
            return;
        } else {
            url= URLDecoder.decode(url);
            content_url = Uri.parse(url);
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(content_url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//        BaseApplication.mContext.startActivity(intent);
      }

    /**
     * 8.0以下的
     * @param context
     * @param url
     */
    private  void getVersion8Down(Context context, String url){

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
//            BaseApplication.mContext.startActivity(browserIntent);
        } catch (ActivityNotFoundException a) {
            a.getMessage();
        }

    }







    /**
     * 复制到剪切板
     * https://www.jianshu.com/p/1e84d33154bd
     */
    public void Clipboard(Context context, String text) {
         //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
         // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", ""+text);
         // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

    }
}
