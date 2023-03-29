package caixin.android.com.http.basic.interceptor;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

import static com.alibaba.fastjson.util.IOUtils.UTF8;

/**
 * Created by goldze on 2017/5/10.
 */
public class BaseInterceptor implements Interceptor {
    private Map<String, String> headers;

    public BaseInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }
        Headers headers = response.headers();
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
            GzipSource gzippedResponseBody = null;
            try {
                gzippedResponseBody = new GzipSource(buffer.clone());
                buffer = new Buffer();
                buffer.writeAll(gzippedResponseBody);
            } finally {
                if (gzippedResponseBody != null) {
                    gzippedResponseBody.close();
                }
            }
        }
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String json = buffer.clone().readString(charset);
        String newJson = dealWithJson(json);
        // 创建一个新的response 对象并返回
        MediaType type = response.body().contentType();
        ResponseBody newRepsoneBody = ResponseBody.create(type, newJson);
        Response newResponse = response.newBuilder().body(newRepsoneBody).build();
        return newResponse;
    }

    private static final String TAG = "BaseInterceptor";

    public String dealWithJson(String oldJson) {
        String newJson = oldJson.replaceAll(":\"\"", ":null");
        newJson = newJson.replaceAll(":\\[\\]", ":null");
        return newJson;
    }
}