package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.ContactResponse;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

public class GroupViewModel extends BaseViewModel<UserCenterModel> {
    public GroupViewModel.UIChangeObservable uc = new GroupViewModel.UIChangeObservable();

    public class UIChangeObservable {

        public MutableLiveData<ContactResponse> init = new MutableLiveData<>();
        public MutableLiveData<Object> applyGroup = new MutableLiveData<>();
    }

    public void init() {
        showDialog();
        mModel.httpGetGroups(this,MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<ContactResponse>() {
            @Override
            public void onSuccess(ContactResponse data, String mes) {
                dismissDialog();
                uc.init.postValue(data);
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


    public GroupViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void applyGroup(int groupId) {
        showDialog();
        mModel.applyGroup(MMKVUtil.getUserInfo().getId(), groupId, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                showShortToast(mes);
                uc.applyGroup.postValue(groupId);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        });
    }


}
