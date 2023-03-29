package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.CaptchaGetIt;
import caixin.android.com.entity.base.VerificationImgBaseResponse;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

public class ForgetPasswordViewModel extends BaseViewModel<UserCenterModel> {

    public ForgetPasswordViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public ForgetPasswordViewModel.UIChangeObservable uc = new ForgetPasswordViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<Object> resetPassword = new MutableLiveData<>();
        public MutableLiveData<Object> resetPayPassword = new MutableLiveData<>();
        public MutableLiveData<Object> getVerifyCode = new MutableLiveData<>();
        public MutableLiveData<CaptchaGetIt> getVerificationImg = new MutableLiveData<>();
        public MutableLiveData<VerificationImgBaseResponse> checkItMutableLiveData = new MutableLiveData<>();
    }

    public void resetPassword(String mobile, String code, String password) {
        showDialog();
        mModel.httpResetPassword(this, mobile, code,password, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                MMKVUtil.setLoginPassword("");
                MMKVUtil.setToken("");
                MMKVUtil.setUserInfo(null);
                uc.resetPassword.postValue(mes);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        });
    }


    public void resetPayPassword(String mobile, String code, String password) {
        showDialog();
        mModel.httpResetPayPassword(this, mobile, code, password, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.resetPayPassword.postValue(mes);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        });
    }

    public void getVerifyCode(String mobile, String type) {
        showDialog();
        mModel.httpGetVerifyCode(this, mobile, type, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                dismissDialog();
                showShortToast(mes);
                uc.getVerifyCode.postValue(null);
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                showShortToast("获取验证码失败:" + msg);
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        });
    }

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
