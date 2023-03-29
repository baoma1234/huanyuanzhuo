package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

public class SignViewModel extends BaseViewModel<UserCenterModel> {
    public SignViewModel.UIChangeObservable uc = new SignViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<List<String>> getSignDays = new MutableLiveData<>();
        public MutableLiveData<Object> sign = new MutableLiveData<>();
    }

    public SignViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void getSignDays() {
        mModel.httpGetSignDays(this, new BaseModel.Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> data, String mes) {
                uc.getSignDays.postValue(data);
                dismissDialog();
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


    public void sign() {
        showDialog();
        mModel.httpSign(this, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.sign.postValue(data);
                showShortToast(mes);
                dismissDialog();
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
}
