package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.BlackFriendResponse;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

public class FriendInfoViewModel extends BaseViewModel<UserCenterModel> {

    public FriendInfoViewModel.UIChangeObservable uc = new FriendInfoViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<Object> modifyNote = new MutableLiveData<>();
        public MutableLiveData<Object> deletFriend = new MutableLiveData<>();
        public MutableLiveData<BlackFriendResponse> blackFriend = new MutableLiveData<>();
    }

    public FriendInfoViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void modifyNote(int toUid, String note) {
        showDialog();
        mModel.httpModifyNote(this, MMKVUtil.getUserInfo().getId(), toUid, note, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                dismissDialog();
                uc.modifyNote.postValue(note);
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


    public void deleteFriend(int uid) {
        showDialog();
        mModel.httpDeleteFriend(this, uid, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                dismissDialog();
                uc.deletFriend.postValue(data);
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


    public void blackFriend(int uid) {
        showDialog();
        mModel.httpBlackFriend(this, uid, new BaseModel.Callback<BlackFriendResponse>() {
            @Override
            public void onSuccess(BlackFriendResponse data, String mes) {
                dismissDialog();
                uc.blackFriend.postValue(data);
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
