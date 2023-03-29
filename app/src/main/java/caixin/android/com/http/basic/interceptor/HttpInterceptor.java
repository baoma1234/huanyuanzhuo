package caixin.android.com.http.basic.interceptor;
import android.util.Log;

import androidx.annotation.NonNull;

import caixin.android.com.http.basic.exception.ConnectionException;
import caixin.android.com.http.basic.exception.ForbiddenException;
import caixin.android.com.http.basic.exception.ResultInvalidException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * 作者：leavesC
 * 时间：2018/10/25 21:16
 * 描述：
 *
 *
 */
public class HttpInterceptor implements Interceptor {

    public HttpInterceptor() {
    }

    private static final String TAG = "HttpInterceptor";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            Log.e(TAG, "intercept: " +"---------e:"+e.getMessage()+":"+request.url());
            throw new ConnectionException();
        }
        if (originalResponse.code() != 200) {
            if (originalResponse.code() == 404) {
                throw new ForbiddenException();
            }
            throw new ResultInvalidException();
        }
        assert originalResponse.body() != null;
        BufferedSource source = originalResponse.body().source();
        source.request(Integer.MAX_VALUE);
        String byteString = source.buffer().snapshot().utf8();
        ResponseBody responseBody = ResponseBody.create(null, byteString);
        return originalResponse.newBuilder().body(responseBody).build();
    }

}
