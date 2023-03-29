package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

import java.util.List;

public class GroupMembersViewModel extends BaseViewModel<UserCenterModel> {

    public GroupMembersViewModel.UIChangeObservable uc = new GroupMembersViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<List<MemberEntity>> init = new MutableLiveData<>();
    }
    public GroupMembersViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }


    public void init(int groupId){
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
}
