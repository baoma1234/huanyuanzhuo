package caixin.android.com.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.AppDownloadUrlEntity;
import caixin.android.com.entity.ContactResponse;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.entity.NewFriendApplyEntity;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.http.WebSocketManager;
import caixin.android.com.utils.MMKVUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ContactViewModel extends BaseViewModel<UserCenterModel> {

    public ContactViewModel.UIChangeObservable uc = new ContactViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<AppDownloadUrlEntity> getAppDownLoadUrl = new MutableLiveData<>();
        public MutableLiveData<ContactResponse> getFriendsLiveData = new MutableLiveData<>();
        public MutableLiveData<ContactResponse> getGroupsLiveData = new MutableLiveData<>();
        public MutableLiveData<FriendEntity> searchFriend = new MutableLiveData<>();
        public MutableLiveData editGroupName = new MutableLiveData<>();
        public MutableLiveData editGroupAvatar = new MutableLiveData<>();
        public MutableLiveData<Object> addFriend = new MutableLiveData<>();
        public MutableLiveData<Object> acceptFriend = new MutableLiveData<>();
        public MutableLiveData<List<NewFriendApplyEntity>> getNewFriendApplyList = new MutableLiveData<>();
        public MutableLiveData<List<MemberEntity>> getGroupMember = new MutableLiveData<>();
        public MutableLiveData<Object> leaveGroup = new MutableLiveData<>();
        public MutableLiveData<Object> deleteConversation = new MutableLiveData<>();
        public MutableLiveData<Boolean> setGroupNoTalk = new MutableLiveData<>();
        public MutableLiveData<List<String>> publishImg = new MutableLiveData<>();
        public MutableLiveData<Object> createGroup = new MutableLiveData<>();
    }

    public ContactViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }


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

    public void getFriends() {
        mModel.httpGetFriends(this, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<ContactResponse>() {
            @Override
            public void onSuccess(ContactResponse data, String mes) {
                uc.getFriendsLiveData.postValue(data);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                WebSocketManager.getInstance().addConnecteListener(TAG, new WebSocketManager.ConnecteReceive() {
                    @Override
                    public void onConnected(String msg) {
                        getFriends();
                        WebSocketManager.getInstance().removeConnectReceive(TAG);
                    }

                    @Override
                    public void onConnectedException(String msg) {
                        dismissDialog();
                        showShortToast(msg);
                        WebSocketManager.getInstance().removeConnectReceive(TAG);
                    }
                });
                dismissDialog();
                showShortToast(msg);
            }
        });
    }

    public void getGroups() {
        showDialog();
        mModel.httpGetGroups(this, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<ContactResponse>() {
            @Override
            public void onSuccess(ContactResponse data, String mes) {
                dismissDialog();
                uc.getGroupsLiveData.postValue(data);
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

    public void setGroupNoTalk(int type, int groupId) {
        Log.e(TAG, "setGroupNoTalk: ");
        showDialog();
        mModel.httpSetGroupNoTalk(this, type, groupId, new BaseModel.Callback<ContactResponse>() {
            @Override
            public void onSuccess(ContactResponse data, String mes) {
                uc.setGroupNoTalk.postValue(true);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
                dismissDialog();
                uc.setGroupNoTalk.postValue(false);
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        });
    }

    public void getGroupMember(int groupId) {
        mModel.httpGetGroupMember(this, groupId, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<List<MemberEntity>>() {
            @Override
            public void onSuccess(List<MemberEntity> data, String mes) {
                uc.getGroupMember.postValue(data);
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

    public void leaveGroup(int groupId) {
        showDialog();
        mModel.httpLeaveGroup(this, groupId, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.leaveGroup.postValue(data);
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

    public void getNewFriendApplyList() {
        showDialog();
        mModel.httpGetNewFriendApplyList(this, new BaseModel.Callback<List<NewFriendApplyEntity>>() {
            @Override
            public void onSuccess(List<NewFriendApplyEntity> data, String mes) {
                dismissDialog();
                uc.getNewFriendApplyList.postValue(data);
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

    public void getAppDownLoadUrl() {
        showDialog();
        mModel.httpGetAppDownLoadUrl(this, new BaseModel.Callback<AppDownloadUrlEntity>() {
            @Override
            public void onSuccess(AppDownloadUrlEntity data, String mes) {
                uc.getAppDownLoadUrl.postValue(data);
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

    public void addFriend(String mobile) {
        showDialog();
        mModel.httpAddFriend(this, mobile, new BaseModel.Callback<FriendEntity>() {
            @Override
            public void onSuccess(FriendEntity data, String mes) {
                dismissDialog();
                uc.addFriend.postValue(data);
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

    public void acceptFriend(int id) {
        showDialog();
        mModel.httpAcceptFriend(this, id, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                dismissDialog();
                uc.acceptFriend.postValue(data);
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

    public void deleteConversation(int id, int type, int messageId) {
        showDialog();
        mModel.httpDeleteConversation(this, id, type, messageId, new BaseModel.Callback() {
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

    public void createGroup(String name, String img) {
        mModel.httpCreateGroup(this, name, img, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.createGroup.postValue(data);
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

    public void editGroupName(String name, int gid) {
        showDialog();
        mModel.httpEditGroupName(this, name,gid, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                dismissDialog();
                uc.editGroupName.postValue(data);
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

    public void editGroupAvatar(String src, int gid) {
        showDialog();
        mModel.httpEditGroupAvatar(this, src,gid, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                dismissDialog();
                uc.editGroupAvatar.postValue(data);
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

    private static final String TAG = "ContactViewModel";
}
