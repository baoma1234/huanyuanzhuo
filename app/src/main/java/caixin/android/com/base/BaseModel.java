package caixin.android.com.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import caixin.android.com.Application;
import caixin.android.com.constant.Contact;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.base.BaseResponse;
import caixin.android.com.entity.base.BaseWebSocketItemRequest;
import caixin.android.com.entity.base.BaseWebSocketRequest;
import caixin.android.com.http.RetrofitManager;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.http.WebSocketManager;
import caixin.android.com.http.basic.config.HttpCode;
import caixin.android.com.http.basic.service.UserCenterService;
import caixin.android.com.utils.MMKVUtil;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.utils.ToastUtils;
import caixin.android.com.view.activity.LoginActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static caixin.android.com.entity.base.BaseResponse.ERRORCODE_SUCCESS;
import static caixin.android.com.entity.base.BaseResponse.ERRORCODE_TOKEN_INVAILABLE;

/**
 * Created by goldze on 2017/6/15.
 */
public class BaseModel implements IModel {

    public BaseModel() {
    }

    @Override
    public void onCleared() {

    }

    protected <T> T getService(Class<T> clz) {
        return RetrofitManager.getInstance().getService(clz);
    }

    protected <T> T getService(Class<T> clz, String host) {
        return RetrofitManager.getInstance().getService(clz, host);
    }

    public void cleanAndGoLogin() {
        Intent intent = new Intent(Application.getInstance(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Extras.INTENT_LOGIN_AGAIN, true);
        intent.putExtras(bundle);
        Application.getInstance().setIsHttpLogin(false);
        Application.getInstance().startActivity(intent);
    }


    protected void execute(Observable observable, final Callback callback, BaseViewModel viewModel) {
        observable.compose(upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())) //线程调度
                .doOnSubscribe(viewModel) //请求与ViewModel周期同步
                .subscribe((Consumer<BaseResponse<Object>>) baseResponse -> {
                    if (baseResponse.getErrorcode() == ERRORCODE_SUCCESS) {
                        if (baseResponse.getType() == 2) {
                            if (baseResponse.getStatus() == 1) {
                                callback.onSuccess(baseResponse.getData(), "这是禁言群，且自己被禁言");
                            } else {
                                callback.onSuccess(baseResponse.getData(), "这是禁言群，但自己不被禁言");
                            }
                        } else {
                            callback.onSuccess(baseResponse.getData(), baseResponse.getMsg());
                        }
                    } else if (baseResponse.getErrorcode() == ERRORCODE_TOKEN_INVAILABLE) {
                        ToastUtils.show(baseResponse.getMsg());
                        cleanAndGoLogin();
                    } else {
                        callback.onFailure(baseResponse.getMsg());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: " + throwable.getMessage());
                        callback.onFailure(throwable.getMessage());
                    }
                });
    }

    private static final String TAG = "BaseModel";

    public interface Callback<T> {
        /**
         * 数据请求成功
         *
         * @param data 请求到的数据
         */
        void onSuccess(T data, String msg);

        /**
         * 使用网络API接口请求方式时，虽然已经请求成功但是由
         * 于{@code msg}的原因无法正常返回数据。
         */
        void onFailure(String msg);

        void onDisconnected(String msg);
    }

    protected void requestWebSocket(String REQUEST_ACTION, BaseWebSocketItemRequest object, @NonNull Callback callback, UserCenterModel.GlobalCallback globalCallback, boolean isNeedAddCallBack) {
        BaseWebSocketRequest<Object> baseWebSocketRequest = new BaseWebSocketRequest();
        baseWebSocketRequest.setAct(REQUEST_ACTION);
        if (object != null)
            object.setToken(MMKVUtil.getUserInfo().getToken());
        ArrayList<Object> dataBeans = new ArrayList<>();
        dataBeans.add(object);
        baseWebSocketRequest.setData(dataBeans);
        String jsonString = JSONObject.toJSONString(baseWebSocketRequest);
        if (isNeedAddCallBack) {
            WebSocketManager.getInstance().addServerReceive(REQUEST_ACTION, baseWebSocketResponse -> {
                if (baseWebSocketResponse.getAct().equals(REQUEST_ACTION)) {
                    WebSocketManager.getInstance().removeServerReceive(REQUEST_ACTION);
                    if (baseWebSocketResponse.getErrorcode() == HttpCode.ERRORCODE_SUCCESS) {
                        if (baseWebSocketResponse.getData() != null && baseWebSocketResponse.getData().size() > 0) {
                            callback.onSuccess(baseWebSocketResponse.getData(), baseWebSocketResponse.getMsg());
                            if (globalCallback != null) {
                                globalCallback.onSuccess(baseWebSocketResponse.getData(), baseWebSocketResponse.getMsg());
                            }
                        } else {
                            callback.onSuccess(null, baseWebSocketResponse.getMsg());
                            if (globalCallback != null) {
                                globalCallback.onSuccess(null, baseWebSocketResponse.getMsg());
                            }
                        }
                    } else if (baseWebSocketResponse.getErrorcode() == HttpCode.ERRORCODE_SYSTEM) {
                        if (baseWebSocketResponse.getData() != null && baseWebSocketResponse.getData().size() > 0) {
                            callback.onSuccess(baseWebSocketResponse.getData(), baseWebSocketResponse.getMsg());
                            if (globalCallback != null) {
                                globalCallback.onSuccess(baseWebSocketResponse.getData(), baseWebSocketResponse.getMsg());
                            }
                        } else {
                            callback.onSuccess(null, baseWebSocketResponse.getMsg());
                            if (globalCallback != null) {
                                globalCallback.onSuccess(null, baseWebSocketResponse.getMsg());
                            }
                        }
                    } else {
                        callback.onFailure(baseWebSocketResponse.getMsg());
                        if (globalCallback != null) {
                            globalCallback.onFailure(baseWebSocketResponse.getMsg());
                        }
                    }
                }
            });
        }
        if (WebSocketManager.getInstance().send(jsonString) == HttpCode.CONNECT_STATE_DISCONNECTED) {
            new Exception().printStackTrace();
            callback.onDisconnected("网络连接异常，正在重连！");
            if (globalCallback != null) {
                globalCallback.onDisconnected("网络连接异常，正在重连！");
            }
            WebSocketManager.getInstance().removeServerReceive(REQUEST_ACTION);
        }
    }


    public void callUrl(BaseViewModel baseViewModel, String url, Callback callback) {
        getService(UserCenterService.class).callUrl(url, MMKVUtil.getToken()).compose(upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())) //线程调度
                .doOnSubscribe(baseViewModel) //请求与ViewModel周期同步
                .subscribe((Consumer<List<String>>) baseResponse -> {
                    callback.onSuccess(baseResponse, "");
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: " + throwable.getMessage());
                        callback.onFailure(throwable.getMessage());
                    }
                });
    }


    public void testUrl(BaseViewModel baseViewModel, String url, Callback callback) {
        getService(UserCenterService.class).testUrl(url).compose(upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())) //线程调度
                .doOnSubscribe(baseViewModel) //请求与ViewModel周期同步
                .subscribe((Consumer<ResponseBody>) baseResponse -> {
                    callback.onSuccess(baseResponse.string(), "");
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: " + throwable.getMessage());
                        callback.onFailure(throwable.getMessage());
                    }
                });
    }
}
