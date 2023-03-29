package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.PicChannel;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SearchUserViewModel extends BaseViewModel<UserCenterModel> {

    //封装一个界面发生改变的观察者
    public SearchUserViewModel.UIChangeObservable uc = new SearchUserViewModel.UIChangeObservable();

    public void searchFriend(String mobile) {
        showDialog();
        mModel.httpSearchFriend(this, mobile, new BaseModel.Callback<FriendEntity>() {
            @Override
            public void onSuccess(FriendEntity data, String mes) {
                dismissDialog();
                uc.searchFriend.postValue(data);
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


    public class UIChangeObservable {
        public MutableLiveData<FriendEntity> searchFriend = new MutableLiveData<>();
    }

    public SearchUserViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }
}
