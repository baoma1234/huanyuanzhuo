package caixin.android.com.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import caixin.android.com.constant.Extras;
import caixin.android.com.view.activity.LoginActivity;

/**
 * Created by jogger on 2019/6/4
 * 描述：
 */
public class ActionUtil {
    public static final int ACTION_LIUHE_GALLERY = 1001;//六合图库
    public static final int ACTION_CHAT_ROOM_SEND_MESSAGE = 5002;  //发送聊天消息
    public static final int ACTION_QUERY_NUMBER_XIANGGANG = 1;
    public static final int ACTION_QUERY_NUMBER_AOMEN = 2;
    public static final int ACTION_QUERY_LIUHE = 3;

    public static boolean isNeedLogin(Activity activity) {
        String token = MMKVUtil.getToken();
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.putExtra(Extras.LOGIN_VALI, true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            return true;
        }
        return false;
    }

    /**
     * 用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        String token = MMKVUtil.getToken();
        return TextUtils.isEmpty(token);
    }
}

