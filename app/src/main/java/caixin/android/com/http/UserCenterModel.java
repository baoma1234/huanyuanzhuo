package caixin.android.com.http;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import caixin.android.com.Application;
import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.AddMyNewsRequest;
import caixin.android.com.entity.ApplyGroupRequest;
import caixin.android.com.entity.CollectRequest;
import caixin.android.com.entity.ConversationRequest;
import caixin.android.com.entity.DeleteEmojiRequest;
import caixin.android.com.entity.DeleteMessageRequest;
import caixin.android.com.entity.DeleteNoteRequest;
import caixin.android.com.entity.EditEmojiRequest;
import caixin.android.com.entity.EditMangerRequest;
import caixin.android.com.entity.GetHistoryMessageRequest;
import caixin.android.com.entity.GetMoreMessageRequest;
import caixin.android.com.entity.HelperLRHMRequest;
import caixin.android.com.entity.LiuHeInfoRequest;
import caixin.android.com.entity.LiuHeRequest;
import caixin.android.com.entity.MOMOExchangeRecord;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.entity.ModifyNoteRequest;
import caixin.android.com.entity.MoneyRecodeEntity;
import caixin.android.com.entity.MultipleSendRequest;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.entity.MyPlatformEntity;
import caixin.android.com.entity.NoticePopRequest;
import caixin.android.com.entity.NotificationRequest;
import caixin.android.com.entity.ReadMessageRequest;
import caixin.android.com.entity.RegisterRequest;
import caixin.android.com.entity.ReportRequest;
import caixin.android.com.entity.SendMessageRequest;
import caixin.android.com.entity.SendPicRequest;
import caixin.android.com.entity.SendRedPackMoneyCountLimitResponse;
import caixin.android.com.entity.SendRedPackRequest;
import caixin.android.com.entity.SendVideoRequest;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.entity.UserRequest;
import caixin.android.com.entity.ZhuanPanStatusEntity;
import caixin.android.com.entity.base.BaseWebSocketItemRequest;
import caixin.android.com.entity.base.VerificationImgBaseResponse;
import caixin.android.com.entity.chatroom.RedPackInformationResponse;
import caixin.android.com.http.basic.config.HttpConfig;
import caixin.android.com.http.basic.service.UserCenterService;
import caixin.android.com.utils.AESUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.widget.verificationcode.Point;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserCenterModel extends BaseModel {
    private volatile static UserCenterModel INSTANCE = null;

    public static UserCenterModel getInstance() {
        if (INSTANCE == null) {
            synchronized (UserCenterModel.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserCenterModel();
                }
            }
        }
        return INSTANCE;
    }

    public void httpGetGroupMember(BaseViewModel baseViewModel, int groupId, int uid, Callback<List<MemberEntity>> callback) {
        execute(getService(UserCenterService.class).getGroupMember(groupId, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpLeaveGroup(BaseViewModel baseViewModel, int groupId, Callback callback) {
        execute(getService(UserCenterService.class).leaveGroup(groupId, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetMoney(BaseViewModel baseViewModel, Callback<MyMoneyResponse> callback) {
        execute(getService(UserCenterService.class).httpGetMoney(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetFindList(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getFindList(), callback, baseViewModel);
    }

    public void httpGetMOMOExchangeRecords(BaseViewModel baseViewModel, Callback<MOMOExchangeRecord> callback) {
        execute(getService(UserCenterService.class).httpGetMOMOExchangeRecords(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void getMoneyRecodeList(BaseViewModel baseViewModel, int page, Callback<List<MoneyRecodeEntity>> callback) {
        execute(getService(UserCenterService.class).getMoneyRecodeList(MMKVUtil.getUserInfo().getToken(), page, 20), callback, baseViewModel);
    }

    public void setPayPass(BaseViewModel baseViewModel, String pay_pwd, Callback<Object> callback) {
        execute(getService(UserCenterService.class).setPayPass(MMKVUtil.getUserInfo().getToken(), pay_pwd), callback, baseViewModel);
    }

    public void httpLogin(BaseViewModel baseViewModel, String mobile, String password, String sbid, Callback<UserInfoEntity> callback) {
        execute(getService(UserCenterService.class).login(mobile, password, sbid, MMKVUtil.getUserInfo().getToken(), 3), callback, baseViewModel);
    }

    public void httpLoginWithCode(BaseViewModel baseViewModel, String mobile, String code, String sbid, Callback<UserInfoEntity> callback) {
        execute(getService(UserCenterService.class).loginWithCode(mobile, code, sbid, MMKVUtil.getUserInfo().getToken(), 3), callback, baseViewModel);
    }

    public void getLoginVerifyCode(BaseViewModel baseViewModel, String mobile, Callback callback) {
        execute(getService(UserCenterService.class).getVerifyLoginCode(mobile), callback, baseViewModel);
    }

    public void httpRegister(BaseViewModel baseViewModel, String code, String mobile, String password, String nikeName, String inviteCode, Callback callback) {
        execute(getService(UserCenterService.class).register(mobile, code, nikeName, password, inviteCode, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetVerifyCode(BaseViewModel baseViewModel, String mobile, String type, Callback callback) {
        execute(getService(UserCenterService.class).getVerifyCode(mobile, type, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void getVersion(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getVersion(1), callback, baseViewModel);
    }

    public void getApplyStatus(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getApplyStatus(MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpResetPassword(BaseViewModel baseViewModel, String mobile, String code, String password, Callback callback) {
        execute(getService(UserCenterService.class).resetPassword(mobile, code, password, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpResetPayPassword(BaseViewModel baseViewModel, String mobile, String code, String pay_pwd, Callback callback) {
        execute(getService(UserCenterService.class).resetPayPassword(mobile, code, MMKVUtil.getUserInfo().getToken(), pay_pwd), callback, baseViewModel);
    }

    public void httpChangePassword(BaseViewModel baseViewModel, String oldPassword, String newPassword, int uid, Callback callback) {
        execute(getService(UserCenterService.class).changePassword(oldPassword, newPassword, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpChangePayPassword(BaseViewModel baseViewModel, String oldPassword, String newPassword, int uid, Callback callback) {
        execute(getService(UserCenterService.class).changePayPassword(newPassword, oldPassword, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetSignDays(BaseViewModel baseViewModel, final Callback callback) {
        execute(getService(UserCenterService.class).getSignDays(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpSign(BaseViewModel baseViewModel, final Callback callback) {
        execute(getService(UserCenterService.class).sign(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpModifyNickName(BaseViewModel baseViewModel, String nickName, int uid, Callback callback) {
        execute(getService(UserCenterService.class).modifyNickName(nickName, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpModifySignature(BaseViewModel baseViewModel, String signature, Callback callback) {
        execute(getService(UserCenterService.class).modifySignature(signature, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

  /*  public void httpPublishImg(BaseViewModel baseViewModel, List<MultipartBody.Part> partLis, Callback callback) {
        execute(getService(UserCenterService.class, HttpConfig.IMG_URL).publishPics(partLis), callback, baseViewModel);
    }*/

    public void httpGetBlackList(BaseViewModel baseViewModel, int page, Callback callback) {
        execute(getService(UserCenterService.class).getBlackList(page, 20, MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpGetCollect(BaseViewModel baseViewModel, int page, Callback callback) {
        execute(getService(UserCenterService.class).getCollect(page, 20, MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpDeleteCollect(BaseViewModel baseViewModel, int id, Callback callback) {
        execute(getService(UserCenterService.class).deleteCollect(id, MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpPublishImgs(BaseViewModel baseViewModel, List<MultipartBody.Part> partLis, Callback callback) {
        execute(getService(UserCenterService.class, HttpConfig.IMG_URL).publishPics(partLis), callback, baseViewModel);
    }

    public void httpAddMyNews(BaseViewModel baseViewModel, AddMyNewsRequest addMyNewsRequest, Callback callback) {
        execute(getService(UserCenterService.class).addMyNews(addMyNewsRequest), callback, baseViewModel);
    }

    public void httpGetFriendNews(BaseViewModel baseViewModel, int page, Callback callback) {
        execute(getService(UserCenterService.class).getFriendNews(MMKVUtil.getToken(), page, 15), callback, baseViewModel);
    }

    public void httpDig(BaseViewModel baseViewModel, int id, Callback callback) {
        execute(getService(UserCenterService.class).dig(id, MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpDeleteFC(BaseViewModel baseViewModel, int id, Callback callback) {
        execute(getService(UserCenterService.class).deleteFC(id, MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpDeleteFCComment(BaseViewModel baseViewModel, int commentId, Callback callback) {
        execute(getService(UserCenterService.class).deleteFCComment(commentId, MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpGetPopInfo(BaseViewModel baseViewModel, NoticePopRequest request, Callback callback) {
        execute(getService(UserCenterService.class).getPopInfo(request), callback, baseViewModel);
    }

    public void httpGetHomeImageAd(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getHomeImageAd(), callback, baseViewModel);
    }

    public void httpAddCollect(BaseViewModel baseViewModel, CollectRequest collectRequest, Callback callback) {
        execute(getService(UserCenterService.class).addCollect(collectRequest), callback, baseViewModel);
    }

    public void httpDeleteEmoji(BaseViewModel baseViewModel, DeleteEmojiRequest deleteMessageRequest, Callback callback) {
        execute(getService(UserCenterService.class).deleteEmoji(deleteMessageRequest), callback, baseViewModel);
    }

    public void httpEditEmoji(BaseViewModel baseViewModel, EditEmojiRequest deleteMessageRequest, Callback callback) {
        execute(getService(UserCenterService.class).editEmoji(deleteMessageRequest), callback, baseViewModel);
    }

    public void httpGetLikeEmoji(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getLikeEmoji(MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpModifyHeader(BaseViewModel baseViewModel, String src, int uid, Callback callback) {
        execute(getService(UserCenterService.class).modifyHeader(src, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpSendFCComment(BaseViewModel baseViewModel, int cfid, String content, int replayId, Callback callback) {
        execute(getService(UserCenterService.class).sendFCComment(cfid, content, replayId, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetActivityList(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getActivityList(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetFriendHistoryMessage(BaseViewModel baseViewModel, int uid, int toUid, int size, Callback callback) {
        execute(getService(UserCenterService.class).getFriendMessage(toUid, size, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetOOSInfo(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getOOSInfo(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }


    public void httpGetImageHistoryMessage(BaseViewModel baseViewModel, int touid, int togroup, int page, int size, Callback callback) {
        execute(getService(UserCenterService.class).getImageHistoryMessage(MMKVUtil.getToken(), size, page, touid, togroup), callback, baseViewModel);
    }

    public void httpGetZiliaoHistoryMessage(BaseViewModel baseViewModel, int touid, int togroup, int page, int size, Callback callback) {
        execute(getService(UserCenterService.class).getZiliaoHistoryMessage(MMKVUtil.getToken(), size, page, touid, togroup), callback, baseViewModel);
    }

    public void httpGetGroupHistoryMessage(BaseViewModel baseViewModel, int uid, int groupId, int size, Callback callback) {
        execute(getService(UserCenterService.class).getGroupMessage(groupId, size, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetConversation(BaseViewModel baseViewModel, int uid, int size, Callback callback) {
        execute(getService(UserCenterService.class).getConversation(size, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpDeleteConversation(BaseViewModel baseViewModel, int id, int type, int messageId, Callback callback) {
        execute(getService(UserCenterService.class).deleteConversation(id, type, messageId, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpCreateGroup(BaseViewModel baseViewModel, String name, String src, Callback callback) {
        execute(getService(UserCenterService.class).createGroup(name, src, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpEditGroupName(BaseViewModel baseViewModel, String name, int gid, Callback callback) {
        execute(getService(UserCenterService.class).editGroupName(name, gid, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpEditGroupAvatar(BaseViewModel baseViewModel, String src, int gid, Callback callback) {
        execute(getService(UserCenterService.class).editGroupAvatar(src, gid, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetFriends(BaseViewModel baseViewModel, int uid, Callback callback) {
        execute(getService(UserCenterService.class).getFriends("", MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetGroups(BaseViewModel baseViewModel, int uid, Callback callback) {
        execute(getService(UserCenterService.class).getGroups("groups", MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpSetGroupNoTalk(BaseViewModel baseViewModel, int type, int groupId, Callback callback) {
        execute(getService(UserCenterService.class).httpSetGroupNoTalk(type, groupId, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetNewFriendApplyList(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getNewFriendApplyList(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpSearchFriend(BaseViewModel baseViewModel, String mobile, Callback callback) {
        execute(getService(UserCenterService.class).searchFriend(mobile, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpAddFriend(BaseViewModel baseViewModel, String mobile, Callback callback) {
        execute(getService(UserCenterService.class).addFriend(mobile, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpAcceptFriend(BaseViewModel baseViewModel, int id, Callback callback) {
        execute(getService(UserCenterService.class).acceptFriend(id, 1, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }


    public void httpGetMoreFriendMessage(BaseViewModel baseViewModel, int uid, int touid, int size, int messageId, Callback callback) {
        execute(getService(UserCenterService.class).getMoreFriendsMessage(messageId, touid, 1, size, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetMoreGroupMessage(BaseViewModel baseViewModel, int uid, int togroup, int size, int messageId, Callback callback) {
        execute(getService(UserCenterService.class).getMoreGroupsMessage(messageId, togroup, 1, size, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void addMembers(BaseViewModel baseViewModel, int groupId, int uid, String group_ids, Callback<Object> callback) {
        execute(getService(UserCenterService.class).addMembers(groupId, group_ids, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }


    public void addManager(BaseViewModel baseViewModel, EditMangerRequest editMangerRequest, Callback<Object> callback) {
        execute(getService(UserCenterService.class).addManager(editMangerRequest), callback, baseViewModel);
    }

    public void deleteManager(BaseViewModel baseViewModel, EditMangerRequest editMangerRequest, Callback<Object> callback) {
        execute(getService(UserCenterService.class).deleteManager(editMangerRequest), callback, baseViewModel);
    }

    public void deleteMembers(BaseViewModel baseViewModel, int groupId, int uid, String group_ids, Callback<Object> callback) {
        execute(getService(UserCenterService.class).deleteMembers(groupId, group_ids, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpDeleteNote(BaseViewModel baseViewModel, int uid, int touid, Callback callback) {
        execute(getService(UserCenterService.class).deleteNote(touid, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpDeleteFriend(BaseViewModel baseViewModel, int uid, Callback callback) {
        execute(getService(UserCenterService.class).deleteFriend(uid, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpModifyNote(BaseViewModel baseViewModel, int uid, int touid, String note, Callback callback) {
        if (TextUtils.isEmpty(note)) {
            httpDeleteNote(baseViewModel, uid, touid, callback);
        } else {
            execute(getService(UserCenterService.class).modifyNote(touid, note, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
        }
    }

    public void httpGetAOMENLiuHeIndex(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getAOMENLiuHeIndex(), callback, baseViewModel);
    }

    public void httpGetXIANGGANGLiuHeIndex(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getXIANGGANGLiuHeIndex(), callback, baseViewModel);
    }

    public void httpReadMessage(BaseViewModel baseViewModel, int uid, int id, Callback callback) {
        execute(getService(UserCenterService.class).readMessage(id, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpBlackFriend(BaseViewModel baseViewModel, int uid, Callback callback) {
        execute(getService(UserCenterService.class).blackFriend(uid, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetNotification(BaseViewModel baseViewModel, int uid, Callback callback) {
        execute(getService(UserCenterService.class).getNotification(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpSetRemind(BaseViewModel baseViewModel, int uid, int remind, Callback callback) {
        execute(getService(UserCenterService.class).setRemind(remind, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetActivities(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getActivities(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpGetGroupAd(BaseViewModel baseViewModel, int groupId, Callback callback) {
        execute(getService(UserCenterService.class).getGroupAd(groupId, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void httpAddEmoji(BaseViewModel baseViewModel, String addEmojiRequest, Callback callback) {
        execute(getService(UserCenterService.class).addEmojis(addEmojiRequest, MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpLogout(BaseViewModel baseViewModel, String token, Callback callback) {
        execute(getService(UserCenterService.class).logout(token), callback, baseViewModel);
    }

    public void getHelperLHCY(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getHelperLHCY(), callback, baseViewModel);
    }

    public void getHelperLRHM(BaseViewModel baseViewModel, HelperLRHMRequest helperLRHMRequest, Callback callback) {
        execute(getService(UserCenterService.class).getHelperLRHM(helperLRHMRequest), callback, baseViewModel);
    }

    public void httpGetAppDownLoadUrl(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getAppDownLoadUrl(MMKVUtil.getToken()), callback, baseViewModel);
    }

    public void httpReport(BaseViewModel baseViewModel, ReportRequest reportRequest, Callback callback) {
        execute(getService(UserCenterService.class).report(reportRequest), callback, baseViewModel);
    }

    public void httpGetLiuHeCollections(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getLiuHeCollections(), callback, baseViewModel);
    }


    public void httpGetAOMENLiuHeData(BaseViewModel baseViewModel, LiuHeRequest request, Callback callback) {
        execute(getService(UserCenterService.class).getAOMENLiuHeData(request), callback, baseViewModel);
    }

    public void httpGetXIANGGANGLiuHeData(BaseViewModel baseViewModel, LiuHeRequest request, Callback callback) {
        execute(getService(UserCenterService.class).getXIANGGANGLiuHeData(request), callback, baseViewModel);
    }

    public void httpGetAOMENLiuHeYear(BaseViewModel baseViewModel, LiuHeRequest request, Callback callback) {
        execute(getService(UserCenterService.class).getAOMENLiuheYear(request), callback, baseViewModel);
    }

    public void httpGetXIANGGANGLiuHeYear(BaseViewModel baseViewModel, LiuHeRequest request, Callback callback) {
        execute(getService(UserCenterService.class).getXIANGGANGLiuheYear(request), callback, baseViewModel);
    }

    public void httpGetAOMENLiuHeArticle(BaseViewModel baseViewModel, LiuHeInfoRequest request, Callback callback) {
        execute(getService(UserCenterService.class).getAOMENLiuHeArticle(request), callback, baseViewModel);
    }

    public void httpGetXIANGGANGLiuHeArticle(BaseViewModel baseViewModel, LiuHeInfoRequest request, Callback callback) {
        execute(getService(UserCenterService.class).getXIANGGANGLiuHeArticle(request), callback, baseViewModel);
    }

    public void httpGetHelperTMZS(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).getHelperTMZS(), callback, baseViewModel);
    }

    public void httpMultipleSend(BaseViewModel baseViewModel, MultipleSendRequest multipleSendRequest, Callback callback) {
        execute(getService(UserCenterService.class).multipleSend(multipleSendRequest), callback, baseViewModel);
    }

    public void multipleSend(MultipleSendRequest multipleSendRequest, Callback callback) {
        requestWebSocket(Extras.REQUEST_MULTIPLE_SEND_CHAT_ROOM, multipleSendRequest, callback, null, false);
    }

    public void getUserMoney(BaseViewModel baseViewModel, int uid, Callback<MyMoneyResponse> callback) {
        execute(getService(UserCenterService.class).getUserMoney(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void getMoneyCountLimit(BaseViewModel baseViewModel, Callback<SendRedPackMoneyCountLimitResponse> callback) {
        execute(getService(UserCenterService.class).getMoneyCountLimit(), callback, baseViewModel);
    }

    public void getRedBag(BaseViewModel baseViewModel, int rid, Callback<RedPackInformationResponse> callback) {
        execute(getService(UserCenterService.class).getRedbag(rid, MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void cashOut(BaseViewModel baseViewModel, int uid, String amount, String ptid, String account, String pay_pwd, Callback<Object> callback) {
        execute(getService(UserCenterService.class).cashOut(amount, ptid, account, MMKVUtil.getUserInfo().getToken(), pay_pwd), callback, baseViewModel);
    }

    public void getPlatforms(BaseViewModel baseViewModel, int uid, Callback<MyPlatformEntity> callback) {
        execute(getService(UserCenterService.class).getPlatforms(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void getZhuanpanState(BaseViewModel baseViewModel,  Callback<ZhuanPanStatusEntity> callback) {
        execute(getService(UserCenterService.class).getZhuanpanState(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }
    public void getFudongState(BaseViewModel baseViewModel,  Callback<List<ZhuanPanStatusEntity>> callback) {
        execute(getService(UserCenterService.class).getFudongState(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }

    public void getUserInfo(int uid, final Callback callback) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUid(uid);
        requestWebSocket(Extras.REQUEST_ACTION_GET_USERINFO, userRequest, callback, null, true);
    }

    public void ping() {
        if (Application.getInstance().isIsHttpLogin())
            requestWebSocket(Extras.REQUEST_PING, new BaseWebSocketItemRequest(), new Callback() {
                @Override
                public void onSuccess(Object data, String mes) {

                }

                @Override
                public void onFailure(String msg) {

                }

                @Override
                public void onDisconnected(String msg) {

                }
            }, null, true);
    }

    public void login(int uid, Callback callback) {
        Log.e(TAG, "login: ");
        GetMoreMessageRequest getMoreMessageRequest = new GetMoreMessageRequest();
        getMoreMessageRequest.setId(uid);
        requestWebSocket(Extras.REQUEST_ACTION_LOGIN, getMoreMessageRequest, callback, null, true);
    }

    public void login(int uid) {
        Log.e(TAG, "login: ");
        GetMoreMessageRequest getMoreMessageRequest = new GetMoreMessageRequest();
        getMoreMessageRequest.setId(uid);
        requestWebSocket(Extras.REQUEST_ACTION_LOGIN, getMoreMessageRequest, new Callback() {
            @Override
            public void onSuccess(Object data, String msg) {

            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onDisconnected(String msg) {

            }
        }, null, true);
    }

    public void register(String code, String mobile, String password, String nikeName, Callback callback) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setCode(code);
        registerRequest.setMobile(mobile);
        registerRequest.setPassword(password);
        registerRequest.setNikename(nikeName);
        requestWebSocket(Extras.REQUEST_ACTION_REGISTER, registerRequest, callback, null, true);
    }

    public void applyGroup(int uid, int groupId, Callback callback) {
        ApplyGroupRequest applyGroupRequest = new ApplyGroupRequest();
        applyGroupRequest.setGroup_id(groupId);
        applyGroupRequest.setUid(uid);
        requestWebSocket(Extras.REQUEST_ACTION_APPLY_GROUP, applyGroupRequest, callback, null, true);
    }

    public void sendMessageToFriend(int uid, int toUid, String content, Callback callback) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setTouids(toUid + "");
        sendMessageRequest.setContents(content);
        requestWebSocket(Extras.REQUEST_ACTION_SEND_MESSAGE, sendMessageRequest, callback, null, false);
    }

    public void sendMessageToFriend(int uid, int toUid, int replyId, String content, Callback callback) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setTouids(toUid + "");
        sendMessageRequest.setContents(content);
        sendMessageRequest.setReply(replyId);
        requestWebSocket(Extras.REQUEST_ACTION_SEND_MESSAGE, sendMessageRequest, callback, null, false);
    }

    public void sendRedPackToFriend(int uid, int touids, String content, double money, String payPass, Callback callback) {
        SendRedPackRequest sendMessageRequest = new SendRedPackRequest();
        sendMessageRequest.setUid(uid);
        sendMessageRequest.setTouids(touids);
        sendMessageRequest.setContents(content);
        sendMessageRequest.setMoney(money);
        sendMessageRequest.setSize(1);
        sendMessageRequest.setPay_pwd(payPass);
        requestWebSocket(Extras.REQUEST_ACTION_SEND_RED_PACK, sendMessageRequest, callback, null, true);
    }

    public void sendRedPackExclusive(int uid, int suid, String content, double money, String payPass, int togroup, Callback callback) {
        SendRedPackRequest sendMessageRequest = new SendRedPackRequest();
        sendMessageRequest.setUid(uid);
        sendMessageRequest.setSuid(suid);
        sendMessageRequest.setContents(content);
        sendMessageRequest.setTogroups(togroup);
        sendMessageRequest.setMoney(money);
        sendMessageRequest.setSize(1);
        sendMessageRequest.setPay_pwd(payPass);
        requestWebSocket(Extras.REQUEST_ACTION_SEND_RED_PACK, sendMessageRequest, callback, null, true);
    }

    public void sendRedPackToGroup(int uid, int togroups, String content, int size, double money, String payPass, Callback callback) {
        SendRedPackRequest sendMessageRequest = new SendRedPackRequest();
        sendMessageRequest.setUid(uid);
        sendMessageRequest.setTogroups(togroups);
        sendMessageRequest.setContents(content);
        sendMessageRequest.setSize(size);
        sendMessageRequest.setMoney(money);
        sendMessageRequest.setPay_pwd(payPass);
        requestWebSocket(Extras.REQUEST_ACTION_SEND_RED_PACK, sendMessageRequest, callback, null, true);
    }

    public void sendMessageToGroup(int uid, int groupId, String content, Callback callback, int assignType, List<Integer> assignTos) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setTogroups(groupId);
        sendMessageRequest.setContents(content);
        sendMessageRequest.setAssignType(assignType);
        String assgintoString = "";
        for (int i = 0; i < assignTos.size(); i++) {
            if (i != assignTos.size() - 1) {
                assgintoString += assignTos.get(i) + ",";
            } else {
                assgintoString += assignTos.get(i);
            }
        }
        sendMessageRequest.setAssignTo(assgintoString);
        requestWebSocket(Extras.REQUEST_ACTION_SEND_MESSAGE, sendMessageRequest, callback, null, false);
    }

    public void sendPicToFriend(int uid, int toUid, String src, String thumb, int width, int height, boolean isZiliao, Callback callback) {
        SendPicRequest sendMessageRequest = new SendPicRequest();
        sendMessageRequest.setUid(uid);
        sendMessageRequest.setTouids(toUid);
        sendMessageRequest.setSrc(src);
        sendMessageRequest.setThumb(thumb);
        sendMessageRequest.setWidth(width);
        sendMessageRequest.setHeight(height);
        if (isZiliao) {
            sendMessageRequest.setIs_zl(1);
        } else {
            sendMessageRequest.setIs_zl(0);
        }
        requestWebSocket(Extras.REQUEST_ACTION_SEND_PIC, sendMessageRequest, callback, null, false);
    }

    public void sendVideoToFriend(int uid, int toUid, String src, String thumb, int width, int height, boolean isZiliao, Callback callback) {
        SendVideoRequest sendMessageRequest = new SendVideoRequest();
        sendMessageRequest.setUid(uid);
        sendMessageRequest.setTouids(toUid);
        sendMessageRequest.setSrc(src);
        sendMessageRequest.setThumb(thumb);
        sendMessageRequest.setWidth(width);
        sendMessageRequest.setHeight(height);
        sendMessageRequest.setPid(4);
        if (isZiliao) {
            sendMessageRequest.setIs_zl(1);
        } else {
            sendMessageRequest.setIs_zl(0);
        }
        requestWebSocket(Extras.REQUEST_ACTION_SEND_PIC, sendMessageRequest, callback, null, false);
    }

    public void sendPicToGroup(int uid, int groupId, String src, String thumb, int width, int height, boolean isZiliao, Callback callback) {
        SendPicRequest sendMessageRequest = new SendPicRequest();
        sendMessageRequest.setWidth(width);
        sendMessageRequest.setHeight(height);
        sendMessageRequest.setUid(uid);
        sendMessageRequest.setTogroups(groupId);
        sendMessageRequest.setSrc(src);
        sendMessageRequest.setThumb(thumb);
        if (isZiliao) {
            sendMessageRequest.setIs_zl(1);
        } else {
            sendMessageRequest.setIs_zl(0);
        }
        requestWebSocket(Extras.REQUEST_ACTION_SEND_PIC, sendMessageRequest, callback, null, false);
    }

    private static final String TAG = "UserCenterModel";

    public void getFriendHistoryMessage(int uid, int toUid, int size, Callback callback) {
        GetHistoryMessageRequest getHistoryMessageRequest = new GetHistoryMessageRequest();
        getHistoryMessageRequest.setSize(size);
        getHistoryMessageRequest.setTouid(toUid);
        getHistoryMessageRequest.setUid(uid);
//        requestWebSocket(Extras.REQUEST_ACTION_GET_MESSAGE, getHistoryMessageRequest, callback, null, true);
    }

    public void getGroupHistoryMessage(int uid, int groupId, int size, Callback callback) {
        GetHistoryMessageRequest getHistoryMessageRequest = new GetHistoryMessageRequest();
        getHistoryMessageRequest.setSize(size);
        getHistoryMessageRequest.setTogroup(groupId);
        getHistoryMessageRequest.setUid(uid);
//        requestWebSocket(Extras.REQUEST_ACTION_GET_MESSAGE, getHistoryMessageRequest, callback, null, true);
    }

    public void getConversation(int uid, Callback callback) {
        ConversationRequest conversationRequest = new ConversationRequest();
        conversationRequest.setSize(200);
        conversationRequest.setUid(uid);
//        requestWebSocket(Extras.REQUEST_ACTION_CONVERSATION, conversationRequest, callback, null, true);
    }

    public void getMoreFriendMessage(int uid, int touid, int size, int messageId, Callback callback) {
        GetMoreMessageRequest getMoreMessageRequest = new GetMoreMessageRequest();
        getMoreMessageRequest.setId(messageId);
        getMoreMessageRequest.setSize(size);
        getMoreMessageRequest.setSort(1);
        getMoreMessageRequest.setTouid(touid);
        getMoreMessageRequest.setUid(uid);
        requestWebSocket(Extras.REQUEST_ACTION_GET_MESSAGE, getMoreMessageRequest, callback, null, true);
    }

    public void getMoreGroupMessage(int uid, int togroup, int size, int messageId, Callback callback) {
        GetMoreMessageRequest getMoreMessageRequest = new GetMoreMessageRequest();
        getMoreMessageRequest.setId(messageId);
        getMoreMessageRequest.setSize(size);
        getMoreMessageRequest.setSort(1);
        getMoreMessageRequest.setTogroup(togroup);
        getMoreMessageRequest.setUid(uid);
        requestWebSocket(Extras.REQUEST_ACTION_GET_MESSAGE, getMoreMessageRequest, callback, null, true);
    }

    public void getNotification(int uid, Callback callback) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setUid(uid);
//        requestWebSocket(Extras.REQUEST_ACTION_NOTIFICATION, notificationRequest, callback, null, true);
    }

    public void deleteMessage(int uid, int id, Callback callback) {
        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setId(id);
        deleteMessageRequest.setUid(uid);
        requestWebSocket(Extras.REQUEST_ACTION_DELETE_MESSAGE, deleteMessageRequest, callback, null, true);
    }

    public void deleteNote(int uid, int touid, Callback callback) {
        DeleteNoteRequest deleteMessageRequest = new DeleteNoteRequest();
        deleteMessageRequest.setFuid(touid);
        deleteMessageRequest.setUid(uid);
//        requestWebSocket(Extras.REQUEST_ACTION_DELETE_NOTE, deleteMessageRequest, callback, null, true);
    }

    public void modifyNote(int uid, int touid, String note, Callback callback) {
        ModifyNoteRequest modifyNoteRequest = new ModifyNoteRequest();
        modifyNoteRequest.setFuid(touid);
        modifyNoteRequest.setUid(uid);
        modifyNoteRequest.setTag(note);
//        requestWebSocket(Extras.REQUEST_ACTION_MODIFY_NOTE, modifyNoteRequest, callback, null, true);
    }

    public void logout(int uid, Callback callback) {
        ModifyNoteRequest modifyNoteRequest = new ModifyNoteRequest();
        modifyNoteRequest.setUid(uid);
//        requestWebSocket(Extras.REQUEST_ACTION_LOGTOU, modifyNoteRequest, callback, null, true);
    }

    public void readMessage(int uid, int id, Callback callback) {
        ReadMessageRequest readMessageRequest = new ReadMessageRequest();
        readMessageRequest.setId(id);
        readMessageRequest.setUid(uid);
//        requestWebSocket(Extras.REQUEST_ACTION_READ_MESSAGE, readMessageRequest, callback, null, true);
    }


    public interface GlobalCallback<T> {

        void onSuccess(T data, String mes);

        void onFailure(String msg);

        void onDisconnected(String msg);
    }

    public void httpGetPicChannel(BaseViewModel baseViewModel, Callback callback) {
        execute(getService(UserCenterService.class).httpGetPicChannel(MMKVUtil.getUserInfo().getToken()), callback, baseViewModel);
    }


    public void getVerificationImg(BaseViewModel baseViewModel, Callback callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("captchaType", "blockPuzzle");
        JSONObject jsonObject = new JSONObject(params);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        getService(UserCenterService.class, HttpConfig.VERIFY_URL).getVerificationImg(body)
                .compose(upstream -> upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())) //线程调度
                .doOnSubscribe(baseViewModel) //请求与ViewModel周期同步
                .subscribe((Consumer<VerificationImgBaseResponse<Object>>) baseResponse -> {
                    callback.onSuccess(baseResponse.getRepData(), baseResponse.getRepCode());
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: " + throwable.getMessage());
                        callback.onFailure(throwable.getMessage());
                    }
                });
    }


    public void checkCaptcha(BaseViewModel baseViewModel, double sliderXMoved, String token, String key, Callback callback) {
        Point point = new Point();
        point.setY(5.0);
        point.setX(sliderXMoved);
        String pointStr = new Gson().toJson(point);
        Map<String, Object> params = new HashMap<>();
        params.put("captchaType", "blockPuzzle");
        params.put("token", token);
        params.put("pointJson", AESUtil.encode(pointStr, key));
        JSONObject jsonObject = new JSONObject(params);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        getService(UserCenterService.class, HttpConfig.VERIFY_URL).checkCaptcha(body)
                .compose(upstream -> upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())) //线程调度
                .doOnSubscribe(baseViewModel) //请求与ViewModel周期同步
                .subscribe((Consumer<VerificationImgBaseResponse<Object>>) baseResponse -> {
                    callback.onSuccess(baseResponse, baseResponse.getRepCode());
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: " + throwable.getMessage());
                        callback.onFailure(throwable.getMessage());
                    }
                });
    }
}
