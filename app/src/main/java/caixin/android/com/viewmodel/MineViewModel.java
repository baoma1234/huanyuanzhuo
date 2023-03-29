package caixin.android.com.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;


public class MineViewModel extends BaseViewModel<UserCenterModel> {
    public MineViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    //封装一个界面发生改变的观察者
    public MineViewModel.UIChangeObservable uc = new MineViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<Object> logout = new MutableLiveData<>();
        public MutableLiveData<List<FindItemModel>> getFindList = new MutableLiveData<>();
    }

    private static final String TAG = "MineViewModel";

    public void logout() {
        showDialog();
        mModel.httpLogout(this, MMKVUtil.getToken(), new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.logout.postValue(data);
                dismissDialog();
                MMKVUtil.setLoginPassword("");
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

    public void getFindList() {
        showDialog();
        mModel.httpGetFindList(this, new BaseModel.Callback<List<FindItemModel>>() {
            @Override
            public void onSuccess(List<FindItemModel> data, String mes) {
                uc.getFindList.postValue(data);
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


}
