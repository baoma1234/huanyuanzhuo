package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.OOSInfoEntity;
import caixin.android.com.entity.PicChannel;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserInfoViewModel extends BaseViewModel<UserCenterModel> {

    //封装一个界面发生改变的观察者
    public UserInfoViewModel.UIChangeObservable uc = new UserInfoViewModel.UIChangeObservable();

    public void publishPicture(File file) {
        showDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("url", "huanqiutx");
        builder.addFormDataPart("file[" + 0 + "]", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        List<MultipartBody.Part> parts = builder.build().parts();
        mModel.httpPublishImgs(this, parts, new BaseModel.Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> data, String msg) {
                uc.publishImg.postValue(data);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }


    public void getOOSInfo() {
        showDialog();
        mModel.httpGetOOSInfo(this, new BaseModel.Callback<OOSInfoEntity>() {
            @Override
            public void onSuccess(OOSInfoEntity data, String mes) {
                uc.getOOSInfo.postValue(data);
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

    public void deleteConversation(int id, int type,int messageId) {
        showDialog();
        mModel.httpDeleteConversation(this, id, type,messageId, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.deleteConversation.postValue(data);
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


    public class UIChangeObservable {
        public MutableLiveData<Object> editNickName = new MutableLiveData<>();
        public MutableLiveData<Object> editSignature = new MutableLiveData<>();
        public MutableLiveData<Object> editHeader = new MutableLiveData<>();
        public MutableLiveData<List<String>> publishImg = new MutableLiveData<>();
        public MutableLiveData<Boolean> setRemind = new MutableLiveData<>();
        public MutableLiveData<PicChannel> getPicChannel = new MutableLiveData<>();
        public MutableLiveData<Object> deletFriend = new MutableLiveData<>();
        public MutableLiveData<Object> deleteConversation = new MutableLiveData<>();
        public MutableLiveData<OOSInfoEntity> getOOSInfo = new MutableLiveData<>();
    }

    public UserInfoViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void setRedmin(int remind) {
        showDialog();
        mModel.httpSetRemind(this, MMKVUtil.getUserInfo().getId(), remind, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                uc.setRemind.postValue(true);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                uc.setRemind.postValue(false);
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void modifyUserHead(String src) {
        mModel.httpModifyHeader(this, src, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.editHeader.postValue(data);
                UserInfoEntity userInfo = MMKVUtil.getUserInfo();
                userInfo.setImg(src);
                MMKVUtil.setUserInfo(userInfo);
                uc.editHeader.postValue(src);
//                getFriends();
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

    public void modifyNickName(int uid, String nickName) {
        showDialog();
        mModel.httpModifyNickName(this, nickName, uid, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.editNickName.postValue(data);
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

    public void modifySignature(String signature) {
        showDialog();
        mModel.httpModifySignature(this, signature, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.editSignature.postValue(data);
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


    public void getPicChannel() {
        showDialog();
        mModel.httpGetPicChannel(this, new BaseModel.Callback<PicChannel>() {
            @Override
            public void onSuccess(PicChannel data, String msg) {
                uc.getPicChannel.postValue(data);
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
