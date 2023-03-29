package caixin.android.com.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.CaptchaGetIt;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.entity.base.VerificationImgBaseResponse;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.http.WebSocketManager;
import caixin.android.com.utils.MMKVUtil;
import cn.jpush.android.api.JPushInterface;

public class LoginViewModel extends BaseViewModel<UserCenterModel> {

    public LoginViewModel.UIChangeObservable uc = new LoginViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<Object> login = new MutableLiveData<>();
        public MutableLiveData<Object> getLoginVerifyCode = new MutableLiveData<>();
        public MutableLiveData<CaptchaGetIt> getVerificationImg = new MutableLiveData<>();
        public MutableLiveData<VerificationImgBaseResponse> checkItMutableLiveData = new MutableLiveData<>();
    }


    public LoginViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void httpLogin(String mobile, String password) {
        showDialog();
        Log.e(TAG, "httpLogin: "+ JPushInterface.getRegistrationID(caixin.android.com.Application.getInstance()) );
        mModel.httpLogin(this, mobile, password, JPushInterface.getRegistrationID(caixin.android.com.Application.getInstance()), new BaseModel.Callback<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity data, String mes) {
                Log.e(TAG, "onSuccess: " + mes + "data = " + data.toString());
                MMKVUtil.setUserInfo(data);
                MMKVUtil.setToken(data.getToken());
                MMKVUtil.setLoginPhone(mobile);
                if (MMKVUtil.isRememberPass()) {
                    MMKVUtil.setLoginPassword(password);
                } else {
                    MMKVUtil.setLoginPassword("");
                }
                caixin.android.com.Application.getInstance().setIsHttpLogin(true);
                login();
            }

            @Override
            public void onFailure(String msg) {
                Log.e(TAG, "onFailure: " + msg);
                showShortToast(msg);
                dismissDialog();

                int loginTimes = MMKVUtil.getLoginTimes();
                loginTimes++;
                MMKVUtil.setLoginTimes(loginTimes);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void httpLoginWithCode(String mobile, String code) {
        showDialog();
        mModel.httpLoginWithCode(this, mobile, code, JPushInterface.getRegistrationID(caixin.android.com.Application.getInstance()), new BaseModel.Callback<UserInfoEntity>() {
            @Override
            public void onSuccess(UserInfoEntity data, String mes) {
                Log.e(TAG, "onSuccess: " + mes + "data = " + data.toString());
                MMKVUtil.setUserInfo(data);
                MMKVUtil.setToken(data.getToken());
                MMKVUtil.setLoginPhone(mobile);
                caixin.android.com.Application.getInstance().setIsHttpLogin(true);
                login();
            }

            @Override
            public void onFailure(String msg) {
                Log.e(TAG, "onFailure: " + msg);
                showShortToast(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void getLoginVerifyCode(String mobile) {
        showDialog();
        mModel.getLoginVerifyCode(this, mobile, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.getLoginVerifyCode.postValue(data);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                Log.e(TAG, "onFailure: " + msg);
                showShortToast(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void login() {
        mModel.login(MMKVUtil.getUserInfo().getId(), new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                dismissDialog();
                uc.login.postValue(data);
                MMKVUtil.setLoginTimes(0);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
                int loginTimes = MMKVUtil.getLoginTimes();
                loginTimes++;
                MMKVUtil.setLoginTimes(loginTimes);
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);

                int loginTimes = MMKVUtil.getLoginTimes();
                loginTimes++;
                MMKVUtil.setLoginTimes(loginTimes);
                WebSocketManager.getInstance().addConnecteListener(TAG, new WebSocketManager.ConnecteReceive() {
                    @Override
                    public void onConnected(String msg) {
                        login();
                        WebSocketManager.getInstance().removeConnectReceive(TAG);
                    }

                    @Override
                    public void onConnectedException(String msg) {
                        dismissDialog();
                        showShortToast("连接失败：" + msg);
                        WebSocketManager.getInstance().removeConnectReceive(TAG);
                    }
                });
            }
        });
    }

    private static final String TAG = "LoginViewModel";


    public void getVerificationImg() {
        showDialog();
        mModel.getVerificationImg(this, new BaseModel.Callback<CaptchaGetIt>() {
            @Override
            public void onSuccess(CaptchaGetIt data, String mes) {
                dismissDialog();
                uc.getVerificationImg.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void checkCaptcha(double sliderXMoved, String token, String key) {
        showDialog();
        mModel.checkCaptcha(this, sliderXMoved, token, key, new BaseModel.Callback<VerificationImgBaseResponse>() {
            @Override
            public void onSuccess(VerificationImgBaseResponse data, String mes) {
                dismissDialog();
                uc.checkItMutableLiveData.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }
}
