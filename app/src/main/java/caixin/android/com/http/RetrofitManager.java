package caixin.android.com.http;

import android.util.Log;

import caixin.android.com.constant.Contact;
import caixin.android.com.http.basic.config.HttpConfig;
import caixin.android.com.http.basic.factory.MyGsonConverterFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import caixin.android.com.http.basic.interceptor.BaseInterceptor;
import caixin.android.com.http.basic.interceptor.HeaderInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitManager {
    private static RetrofitManager INSTANCE;
    private static final int READ_TIMEOUT = 300;
    private static final int WRITE_TIMEOUT = 300;
    private static final int CONNECT_TIMEOUT = 300;
    private final Map<String, Object> mServiceMap = new ConcurrentHashMap<>();

    public static RetrofitManager getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitManager();
                }
            }
        }
        return INSTANCE;
    }

    public <T> T getService(Class<T> cls) {
        return getService(cls, HttpConfig.HOST);
    }

    public <T> T getService(Class<T> cls, String host) {
        T value;
        if (mServiceMap.containsKey(host)) {
            Object object = mServiceMap.get(host);
            if (object == null) {
                value = createRetrofit(host).create(cls);
                mServiceMap.put(host, value);
            } else {
                value = (T) object;
            }
        } else {
            value = createRetrofit(host).create(cls);
            mServiceMap.put(host, value);
        }
        return value;
    }

    private static final String TAG = "RetrofitManager";

    private Retrofit createRetrofit(String url) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i(TAG, "log: " + "httpLoggingInterceptor message = " + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new RetryIntercepter(3))
//                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(new FilterInterceptor())
                .addInterceptor(new BaseInterceptor())
                .retryOnConnectionFailure(true);
        builder.addInterceptor(httpLoggingInterceptor);
        OkHttpClient client = builder.build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
