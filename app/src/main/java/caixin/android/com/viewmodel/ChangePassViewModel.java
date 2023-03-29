package caixin.android.com.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

public class ChangePassViewModel extends BaseViewModel<UserCenterModel> {

    public ChangePassViewModel.UIChangeObservable uc = new ChangePassViewModel.UIChangeObservable();

    public ChangePassViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public class UIChangeObservable {
        public MutableLiveData<Boolean> changePassword = new MutableLiveData<>();
    }

    public void changePassword(String oldPasswordString, String password) {
        mModel.httpChangePassword(this, oldPasswordString, password, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.changePassword.postValue(true);
                MMKVUtil.setLoginPassword("");
                showShortToast(mes);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {
                showShortToast(msg);
                dismissDialog();
            }
        });
    }

    private static final String TAG = "ChangePassViewModel";

    public void changePayPassword(String oldPasswordString, String password) {
        Log.e(TAG, "changePayPassword: ");
        mModel.httpChangePayPassword(this, oldPasswordString, password, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.changePassword.postValue(true);
                showShortToast(mes);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
                Log.e(TAG, "onFailure: " + msg);
            }

            @Override
            public void onDisconnected(String msg) {
                showShortToast(msg);
                dismissDialog();
                Log.e(TAG,
                        "onFailure: " + msg);
            }
        });
    }
}
