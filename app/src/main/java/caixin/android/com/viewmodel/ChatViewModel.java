package caixin.android.com.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.caixin.huanyu.R;

import java.io.File;
import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.CollectRequest;
import caixin.android.com.entity.DeleteEmojiRequest;
import caixin.android.com.entity.EditEmojiRequest;
import caixin.android.com.entity.GroupAdEntity;
import caixin.android.com.entity.LikeEmojiEntity;
import caixin.android.com.entity.OOSInfoEntity;
import caixin.android.com.entity.SendMessageRequest;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ChatViewModel extends BaseViewModel<UserCenterModel> {


    public ChatViewModel.UIChangeObservable uc = new ChatViewModel.UIChangeObservable();

    public void publishChatPicture(File file, boolean isZiliao) {
        showDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (isZiliao) {
            builder.addFormDataPart("url", "huanqiuzl");
        } else {
            builder.addFormDataPart("url", "huanqiutp");
        }
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

    public void publishEmojiPictures(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("url", "huanqiubq");
        builder.addFormDataPart("file[" + 0 + "]", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        List<MultipartBody.Part> parts = builder.build().parts();
        mModel.httpPublishImgs(this, parts, new BaseModel.Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> data, String msg) {
                uc.publishEmojiPics.postValue(data);
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

    public void getGroupAd(int groupId) {
   /*     UserCenterModel.getInstance().login(MMKVUtil.getUserInfo().getId(), new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
//                MMKVUtil.setLoginState(true);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onDisconnected(String msg) {

            }
        });*/
        mModel.httpGetGroupAd(this, groupId, new BaseModel.Callback<GroupAdEntity>() {
            @Override
            public void onSuccess(GroupAdEntity data, String msg) {
                uc.getGroupAd.postValue(data);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void addEmoji(String strings) {
        mModel.httpAddEmoji(this, strings, new BaseModel.Callback<LikeEmojiEntity>() {
            @Override
            public void onSuccess(LikeEmojiEntity data, String msg) {
                dismissDialog();
                uc.AddEmojis.postValue(data);
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
        public MutableLiveData<Object> sendMessage = new MutableLiveData<>();
        public MutableLiveData<Object> sendPIC = new MutableLiveData<>();
        public MutableLiveData<List<SendMessageResponse>> getHistoryFriendMessage = new MutableLiveData<>();
        public MutableLiveData<List<SendMessageResponse>> getMoreFriendMessage = new MutableLiveData<>();
        public MutableLiveData<List<SendMessageResponse.ImgBean>> getImgHistory = new MutableLiveData<>();
        public MutableLiveData<Object> deleteMessage = new MutableLiveData<>();
        public MutableLiveData<List<String>> publishImg = new MutableLiveData<>();
        public MutableLiveData<List<String>> publishEmojiPics = new MutableLiveData<>();
        public MutableLiveData<LikeEmojiEntity> AddEmojis = new MutableLiveData<>();
        public MutableLiveData<GroupAdEntity> getGroupAd = new MutableLiveData<>();
        public MutableLiveData<OOSInfoEntity> getOOSInfo = new MutableLiveData<>();
        public MutableLiveData<Object> addCollect = new MutableLiveData<>();
        public MutableLiveData<Object> deleteEmoji = new MutableLiveData<>();
        public MutableLiveData<Boolean> editEmoji = new MutableLiveData<>();
        public MutableLiveData<Boolean> isNoTalkGroup = new MutableLiveData<>();
        public MutableLiveData<List<LikeEmojiEntity>> getLikeEmoji = new MutableLiveData<>();
        public MutableLiveData<Object> deleteNote = new MutableLiveData<>();
        public MutableLiveData<Object> modifyNote = new MutableLiveData<>();
    }


    public ChatViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void collect(CollectRequest collectRequest) {
        showDialog();
        mModel.httpAddCollect(this, collectRequest, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.addCollect.postValue(data);
                showShortToast(caixin.android.com.Application.getInstance().getResources().getString(R.string.collect_success));
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


    public void deleteEmoji(DeleteEmojiRequest deleteEmojiRequest) {
        showDialog();
        mModel.httpDeleteEmoji(this, deleteEmojiRequest, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.deleteEmoji.postValue(data);
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

    public void editEmoji(EditEmojiRequest deleteEmojiRequest) {
        showDialog();
        mModel.httpEditEmoji(this, deleteEmojiRequest, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.editEmoji.postValue(true);
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                showShortToast(msg);
                uc.editEmoji.postValue(false);
            }

            @Override
            public void onDisconnected(String msg) {
                uc.editEmoji.postValue(false);
                dismissDialog();
            }
        });
    }

    public void getLikeEmoji() {
        mModel.httpGetLikeEmoji(this, new BaseModel.Callback<List<LikeEmojiEntity>>() {
            @Override
            public void onSuccess(List<LikeEmojiEntity> data, String msg) {
                uc.getLikeEmoji.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    /**
     * 发送图片消息
     */
    public void sendPicToFriend(String url, String thumb, int toUid, int width, int height, boolean isZiliao) {
        if (TextUtils.isEmpty(url) ) {
            return;
        }
        mModel.sendPicToFriend(MMKVUtil.getUserInfo().getId(), toUid, url, thumb, width, height, isZiliao, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.sendPIC.postValue(data);
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

    public void sendVideoToFriend(String url, String thumb, int toUid, int width, int height, boolean isZiliao) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(thumb)) {
            return;
        }
        mModel.sendVideoToFriend(MMKVUtil.getUserInfo().getId(), toUid, url, thumb, width, height, isZiliao, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.sendPIC.postValue(data);
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

    /**
     * 发送图片消息
     */
    public void sendPicToGroup(String url, String thumb, int groupId, int width, int height, boolean isZiliao) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        mModel.sendPicToGroup(MMKVUtil.getUserInfo().getId(), groupId, url, thumb, width, height, isZiliao, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.sendPIC.postValue(data);
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

    public void sendMessageToFriend(String content, int toUid) {
        mModel.sendMessageToFriend(MMKVUtil.getUserInfo().getId(), toUid, content, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.sendMessage.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        });
    }

    public void sendMessageToFriend(String content, int toUid, int replyId) {
        mModel.sendMessageToFriend(MMKVUtil.getUserInfo().getId(), toUid, replyId,content, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.sendMessage.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        });
    }

    public void sendMessageToGroup(String content, int toGroupId, List<Integer> assignTos) {
        int type;
        List<Integer> userIds = assignTos;
        if (userIds != null && userIds.size() > 0) {
            type = SendMessageRequest.TYPE_ASSIGN_ALONE;
        } else {
            type = SendMessageRequest.TYPE_ASSIGN_NONE;
        }

        mModel.sendMessageToGroup(MMKVUtil.getUserInfo().getId(), toGroupId, content, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.sendMessage.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        }, type, assignTos);
    }

    public void getFriendHistoryMessage(int touid, int size) {
        showDialog();
        mModel.httpGetFriendHistoryMessage(this, MMKVUtil.getUserInfo().getId(), touid, size, new BaseModel.Callback<List<SendMessageResponse>>() {
            @Override
            public void onSuccess(List<SendMessageResponse> data, String mes) {
                uc.getHistoryFriendMessage.postValue(data);
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

    public void getImageHistoryMessage(int touid, int togroup, int page, int size) {
        mModel.httpGetImageHistoryMessage(this, touid, togroup, page, size, new BaseModel.Callback<List<SendMessageResponse.ImgBean>>() {
            @Override
            public void onSuccess(List<SendMessageResponse.ImgBean> data, String mes) {
                uc.getImgHistory.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                showShortToast(msg);
            }
        });
    }

    public void getZiliaoHistoryMessage(int touid, int togroup, int page, int size) {
        mModel.httpGetZiliaoHistoryMessage(this, touid, togroup, page, size, new BaseModel.Callback<List<SendMessageResponse.ImgBean>>() {
            @Override
            public void onSuccess(List<SendMessageResponse.ImgBean> data, String mes) {
                uc.getImgHistory.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                showShortToast(msg);
            }
        });
    }

    private static final String TAG = "ChatViewModel";

    public void getGroupHistoryMessage(int groupid, int size) {
        showDialog();
        mModel.httpGetGroupHistoryMessage(this, MMKVUtil.getUserInfo().getId(), groupid, size, new BaseModel.Callback<List<SendMessageResponse>>() {
            @Override
            public void onSuccess(List<SendMessageResponse> data, String mes) {
                Log.e(TAG, "onSuccess: " + mes);
                if (!TextUtils.isEmpty(mes) && mes.equals("这是禁言群，且自己被禁言")) {
                    uc.isNoTalkGroup.postValue(true);
                } else if (!TextUtils.isEmpty(mes) && mes.equals("这是禁言群，但自己不被禁言")) {
                    uc.isNoTalkGroup.postValue(false);
                }
                uc.getHistoryFriendMessage.postValue(data);
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

    /**
     * 获取更多消息
     */
    public void getMoreFriendMessage(int touid, int size, int messageId) {
        mModel.httpGetMoreFriendMessage(this, MMKVUtil.getUserInfo().getId()
                , touid, size, messageId, new BaseModel.Callback<List<SendMessageResponse>>() {
                    @Override
                    public void onSuccess(List<SendMessageResponse> data, String mes) {
                        uc.getMoreFriendMessage.postValue(data);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showShortToast(msg);
                    }

                    @Override
                    public void onDisconnected(String msg) {
                        dismissDialog();
                        showShortToast(msg);
                    }
                });
    }

    /**
     * 获取更多消息
     */
    public void getMoreGroupMessage(int toGroup, int size, int messageId) {
        mModel.httpGetMoreGroupMessage(this, MMKVUtil.getUserInfo().getId()
                , toGroup, size, messageId, new BaseModel.Callback<List<SendMessageResponse>>() {
                    @Override
                    public void onSuccess(List<SendMessageResponse> data, String mes) {
                        uc.getMoreFriendMessage.postValue(data);
                    }

                    @Override
                    public void onFailure(String msg) {
                        showShortToast(msg);
                    }

                    @Override
                    public void onDisconnected(String msg) {
                        dismissDialog();
                        showShortToast(msg);
                    }
                });
    }

    public void deleteMessage(int id) {
        mModel.deleteMessage(MMKVUtil.getUserInfo().getId(), id, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.deleteMessage.postValue(data);
            }

            @Override
            public void onFailure(String msg) {
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                dismissDialog();
                showShortToast(msg);
            }
        });
    }

    public void readMessage(int id) {
        mModel.readMessage(MMKVUtil.getUserInfo().getId(), id, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }
}
