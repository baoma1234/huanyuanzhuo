package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.entity.EditMangerRequest;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

import java.util.List;

public class AddMemberViewModel extends BaseViewModel<UserCenterModel> {

    public AddMemberViewModel.UIChangeObservable uc = new AddMemberViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<List<MemberEntity>> init = new MutableLiveData<>();
        public MutableLiveData<Boolean> addMembers = new MutableLiveData<>();
        public MutableLiveData<Boolean> addManager = new MutableLiveData<>();
        public MutableLiveData<Boolean> deleteManager = new MutableLiveData<>();
    }

    public AddMemberViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void init(int groupId) {
        showDialog();
        mModel.httpGetGroupMember(this, groupId, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<List<MemberEntity>>() {
            @Override
            public void onSuccess(List<MemberEntity> data, String mes) {
                uc.init.postValue(data);
                dismissDialog();
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

    public void addMembers(int groupId, String group_ids) {
        showDialog();
        mModel.addMembers(this, groupId, MMKVUtil.getUserInfo().getId(), group_ids, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.addMembers.postValue(true);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                uc.addMembers.postValue(false);
                dismissDialog();
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void addManager(EditMangerRequest editMangerRequest) {
        showDialog();
        mModel.addManager(this, editMangerRequest, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.addManager.postValue(true);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                uc.addManager.postValue(false);
                dismissDialog();
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }  public void deleteManager(EditMangerRequest editMangerRequest) {
        showDialog();
        mModel.deleteManager(this, editMangerRequest, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.deleteManager.postValue(true);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                uc.deleteManager.postValue(false);
                dismissDialog();
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }
}
