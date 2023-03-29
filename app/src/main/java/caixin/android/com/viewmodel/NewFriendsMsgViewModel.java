package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.entity.NotificationEntity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

import java.util.List;

public class NewFriendsMsgViewModel extends BaseViewModel<UserCenterModel> {
    //封装一个界面发生改变的观察者
    public NewFriendsMsgViewModel.UIChangeObservable uc = new NewFriendsMsgViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<List<NotificationEntity>> init = new MutableLiveData<>();
    }

    public NewFriendsMsgViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void init() {
        showDialog();
        mModel.httpGetNotification(this,MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<List<NotificationEntity>>() {
            @Override
            public void onSuccess(List<NotificationEntity> data, String mes) {
                uc.init.postValue(data);
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
