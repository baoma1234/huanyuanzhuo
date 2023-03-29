package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.utils.MMKVUtil;

import java.util.List;

public class DeleteMemberViewModel extends BaseViewModel<UserCenterModel> {

    public DeleteMemberViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public DeleteMemberViewModel.UIChangeObservable uc = new DeleteMemberViewModel.UIChangeObservable();

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

    public void deleteMembers(int groupId, String group_uids) {
        showDialog();
        mModel.deleteMembers(this, groupId, MMKVUtil.getUserInfo().getId(), group_uids, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.delete.postValue(data);
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

    public class UIChangeObservable {
        public MutableLiveData<List<MemberEntity>> init = new MutableLiveData<>();
        public MutableLiveData<Object> delete = new MutableLiveData<>();
    }
}
