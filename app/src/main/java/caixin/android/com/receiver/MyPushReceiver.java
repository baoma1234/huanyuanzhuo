package caixin.android.com.receiver;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.service.JPushMessageReceiver;

public class MyPushReceiver extends JPushMessageReceiver {
    private static final String TAG = "MyPushReceiver";
    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        Log.e(TAG, "onRegister: "+s );
//        MMKVUtil.setJPushID(s);
    }
}
