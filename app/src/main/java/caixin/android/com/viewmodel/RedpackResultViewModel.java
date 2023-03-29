package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.chatroom.RedPackInformationResponse;
import caixin.android.com.http.UserCenterModel;

public class RedpackResultViewModel extends BaseViewModel<UserCenterModel> {
    public RedpackResultViewModel.UIChangeObservable uc = new RedpackResultViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<RedPackInformationResponse> getRedBag = new MutableLiveData<RedPackInformationResponse>();
    }

    public RedpackResultViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void getRedbag(int rid) {
        showDialog();
        mModel.getRedBag(this, rid, new BaseModel.Callback<RedPackInformationResponse>() {
            @Override
            public void onSuccess(RedPackInformationResponse data, String msg) {
                dismissDialog();
                uc.getRedBag.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }
}
