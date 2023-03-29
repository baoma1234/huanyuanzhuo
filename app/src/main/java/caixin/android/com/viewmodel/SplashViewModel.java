package caixin.android.com.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.http.WebSocketManager;
import caixin.android.com.http.basic.config.HttpConfig;
import caixin.android.com.utils.DeviceStateUtil;
import caixin.android.com.utils.MMKVUtil;
import cn.jpush.android.api.JPushInterface;

public class SplashViewModel extends BaseViewModel<UserCenterModel> {

    public SplashViewModel.UIChangeObservable uc = new SplashViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<Boolean> login = new MutableLiveData<Boolean>();
        public MutableLiveData<List<String>> addressURLs = new MutableLiveData<List<String>>();
        public MutableLiveData<String> testUrl = new MutableLiveData<String>();
    }

    public SplashViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    private static final String TAG = "SplashViewModel";
    private Handler handler;

    public void getUrlAddress() {
        mModel.callUrl(this, HttpConfig.ADDRESS_URL, new BaseModel.Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> data, String msg) {
                uc.addressURLs.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                uc.addressURLs.postValue(new ArrayList<>());
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void getTestAddress(String url) {
        mModel.testUrl(this, url, new BaseModel.Callback<String>() {
            @Override
            public void onSuccess(String data, String msg) {
                uc.testUrl.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                uc.testUrl.postValue("");
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void httpLogin() {
        showDialog();
        mModel.httpLogin(this, MMKVUtil.getLoginPhone(), MMKVUtil.getLoginPassword(), JPushInterface.getRegistrationID(caixin.android.com.Application.getInstance()), new BaseModel.Callback<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity data, String mes) {
                Log.e(TAG, "onSuccess: " + mes + "data = " + data.toString());
                MMKVUtil.setUserInfo(data);
                MMKVUtil.setToken(data.getToken());
                caixin.android.com.Application.getInstance().setIsHttpLogin(true);
                login();
            }

            @Override
            public void onFailure(String msg) {
                Log.e(TAG, "onFailure: " + msg);
                dismissDialog();
                caixin.android.com.Application.getInstance().setIsHttpLogin(false);
                uc.login.postValue(false);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void login() {
        showDialog();
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    uc.login.postValue(false);
                    dismissDialog();
                }
            };
        }
        handler.sendEmptyMessageDelayed(1, 15 * 1000);
        mModel.login(MMKVUtil.getUserInfo().getId(), new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                dismissDialog();
                uc.login.postValue(true);
                handler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onFailure(String msg) {
//                MMKVUtil.setLoginPassword("");
//                MMKVUtil.setToken("");
//                MMKVUtil.setUserInfo(null);
                uc.login.postValue(false);
                dismissDialog();
                handler.removeCallbacksAndMessages(null);
            }

            @Override
            public void onDisconnected(String msg) {
                Log.e(TAG, "onDisconnected: ");
                dismissDialog();
                uc.login.postValue(false);
                handler.removeCallbacksAndMessages(null);
                WebSocketManager.getInstance().addConnecteListener(TAG, new WebSocketManager.ConnecteReceive() {
                    @Override
                    public void onConnected(String msg) {
                        Log.e(TAG, "onConnected: ");
                        login();
                        WebSocketManager.getInstance().removeConnectReceive(TAG);
                    }

                    @Override
                    public void onConnectedException(String msg) {
                        dismissDialog();
                        showShortToast(msg);
                        WebSocketManager.getInstance().removeConnectReceive(TAG);
                    }
                });
            }
        });
    }
}
