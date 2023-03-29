package caixin.android.com.http.basic.interceptor;


import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.NonNull;

import caixin.android.com.constant.Config;
import caixin.android.com.utils.MMKVUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：leavesC
 * 时间：2018/10/27 7:34
 * 描述：
 */
public class HeaderInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        if (!TextUtils.isEmpty(MMKVUtil.getToken())) {
            builder.addHeader(Config.TOKEN, "8a97c3b42b5206e6101058afb3c2328f");
        }
//        Log.e(TAG, "intercept: "+JPushInterface.getRegistrationID(BaseApplication.getInstance()) );
        Log.i(TAG, "intercept: token = " +MMKVUtil.getToken());
        builder/*.addHeader("Accept-Encoding", "gzip")*/
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .addHeader("registerId", JPushInterface.getRegistrationID(BaseApplication.getInstance()))
                .method(originalRequest.method(), originalRequest.body());
        return chain.proceed(builder.build());
    }

    private static final String TAG = "HeaderInterceptor";
}