package caixin.android.com.http.basic.service;

import java.util.List;

import caixin.android.com.entity.ActiveModel;
import caixin.android.com.entity.ActivityEntity;
import caixin.android.com.entity.AddMyNewsRequest;
import caixin.android.com.entity.AppDownloadUrlEntity;
import caixin.android.com.entity.AppVersion;
import caixin.android.com.entity.ApplyStatusResponse;
import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.entity.BlackFriendResponse;
import caixin.android.com.entity.CaptchaCheckIt;
import caixin.android.com.entity.CaptchaGetIt;
import caixin.android.com.entity.CollectEntity;
import caixin.android.com.entity.CollectRequest;
import caixin.android.com.entity.ContactResponse;
import caixin.android.com.entity.DeleteEmojiRequest;
import caixin.android.com.entity.DigResponse;
import caixin.android.com.entity.EditEmojiRequest;
import caixin.android.com.entity.EditMangerRequest;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.entity.FriendNewsEntity;
import caixin.android.com.entity.GroupAdEntity;
import caixin.android.com.entity.HelperLRHMRequest;
import caixin.android.com.entity.HomeImageAdModel;
import caixin.android.com.entity.LHCYModel;
import caixin.android.com.entity.LRHMModel;
import caixin.android.com.entity.LikeEmojiEntity;
import caixin.android.com.entity.LiuHeDataResponse;
import caixin.android.com.entity.LiuHeGalleryIndex;
import caixin.android.com.entity.LiuHeIndexItem;
import caixin.android.com.entity.LiuHeInfoRequest;
import caixin.android.com.entity.LiuHeRequest;
import caixin.android.com.entity.MOMOExchangeRecord;
import caixin.android.com.entity.MediaInfo;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.entity.MoneyRecodeEntity;
import caixin.android.com.entity.MultipleSendRequest;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.entity.MyPlatformEntity;
import caixin.android.com.entity.NewFriendApplyEntity;
import caixin.android.com.entity.NoticePopRequest;
import caixin.android.com.entity.NotificationEntity;
import caixin.android.com.entity.OOSInfoEntity;
import caixin.android.com.entity.PicChannel;
import caixin.android.com.entity.PopModel;
import caixin.android.com.entity.ReportRequest;
import caixin.android.com.entity.SendMessageResponse;
import caixin.android.com.entity.SendRedPackMoneyCountLimitResponse;
import caixin.android.com.entity.TMZSModel2;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.entity.ZhuanPanStatusEntity;
import caixin.android.com.entity.base.BaseResponse;
import caixin.android.com.entity.base.VerificationImgBaseResponse;
import caixin.android.com.entity.chatroom.RedPackInformationResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface UserCenterService {

    /**
     * 獲取個人信息
     *
     * @param
     * @return
     */
    @POST("index/users/group")
    Observable<BaseResponse<List<MemberEntity>>> getGroupMember(@Query("group_id") int groupId, @Query("token") String token);

    @POST("/index/money_action/balance")
    Observable<BaseResponse<MyMoneyResponse>> httpGetMoney(@Query("token") String token);


    @POST("/index/money_action/exchangeRecord")
    Observable<BaseResponse<MOMOExchangeRecord>> httpGetMOMOExchangeRecords(@Query("token") String token);


    @POST("/index/users/spay")
    Observable<BaseResponse<Object>> setPayPass(@Query("token") String token, @Query("pay_pwd") String pay_pwd);


    @POST("/index/Money_Action/record")
    Observable<BaseResponse<List<MoneyRecodeEntity>>> getMoneyRecodeList(@Query("token") String token, @Query("page") int page, @Query("size") int size);

    @POST("index/users/joingroup")
    Observable<BaseResponse<List<MemberEntity>>> addMembers(@Query("group_id") int groupId, @Query("group_uids") String group_uids, @Query("token") String token);


    @POST("/api/Group/groupStrators")
    Observable<BaseResponse> addManager(@Body EditMangerRequest editMangerRequest);

    @POST("/api/Group/cancelStrators")
    Observable<BaseResponse> deleteManager(@Body EditMangerRequest editMangerRequest);


    @POST("index/users/kickjoinGroup")
    Observable<BaseResponse<Object>> deleteMembers(@Query("group_id") int groupId, @Query("group_uids") String group_uids, @Query("token") String token);

    @POST("/index/login/userLogin")
    Observable<BaseResponse<UserInfoEntity>> login(@Query("mobile") String mobile, @Query("password") String password, @Query("sbid") String sbid, @Query("token") String token, @Query("is_ios") int is_ios);

    @POST("/index/login/userLogin")
    Observable<BaseResponse<UserInfoEntity>> loginWithCode(@Query("mobile") String mobile, @Query("code") String code, @Query("sbid") String sbid, @Query("token") String token, @Query("is_ios") int is_ios);

    @POST("/index/users/register")
    Observable<BaseResponse<Object>> register(@Query("mobile") String mobile, @Query("code") String code, @Query("nikename") String nikename, @Query("password") String password, @Query("incode") String incode, @Query("token") String token);

    @POST("/index/users/getVerifyCode")
    Observable<BaseResponse<Object>> getVerifyCode(@Query("mobile") String mobile, @Query("type") String type, @Query("token") String token);

    @POST("/index/users/getVerifyLoginCode")
    Observable<BaseResponse<Object>> getVerifyLoginCode(@Query("mobile") String mobile);

    /**
     * 获取滑动验证图片
     */
    @POST("https://captcha.anji-plus.com/captcha-api/captcha/get")
    Observable<VerificationImgBaseResponse<CaptchaGetIt>> getVerificationImg(@Body RequestBody body);


    /**
     * 获取滑动验证图片
     */
    @POST("https://captcha.anji-plus.com/captcha-api/captcha/check")
    Observable<VerificationImgBaseResponse<CaptchaCheckIt>> checkCaptcha(@Body RequestBody body);


    @POST("/index/users/recoverpwd")
    Observable<BaseResponse<Object>> resetPassword(@Query("mobile") String mobile, @Query("code") String code, @Query("password") String password, @Query("token") String token);

    @POST("/index/users/editspay")
    Observable<BaseResponse<Object>> resetPayPassword(@Query("mobile") String mobile, @Query("code") String code, @Query("token") String token, @Query("pay_pwd") String pay_pwd);

    @POST("/index/users/password")
    Observable<BaseResponse<Object>> changePassword(@Query("oldpassword") String oldpassword, @Query("password") String password, @Query("token") String token);

    @POST("/index/users/edit")
    Observable<BaseResponse<Object>> changePayPassword(@Query("pay_pwd") String pay_pwd, @Query("old_pwd") String old_pwd, @Query("token") String token);

    @POST("/index/users/nickname")
    Observable<BaseResponse<Object>> modifyNickName(@Query("nikename") String nikename, @Query("token") String token);

    /*  */

    /**
     * 上传图片
     *//*
    @Multipart
    @POST("upload.php")
    Observable<BaseResponse<ImageResponse>> publishImg(@Part List<MultipartBody.Part> partLis);*/
    @POST("/index/users/avatar")
    Observable<BaseResponse<Object>> modifyHeader(@Query("src") String src, @Query("token") String token);

    @POST("/index/message/getMessage")
    Observable<BaseResponse<List<SendMessageResponse>>> getFriendMessage(@Query("touid") int touid, @Query("size") int size, @Query("token") String token);

    @GET("/api/Sts/aliyun")
    Observable<BaseResponse<OOSInfoEntity>> getOOSInfo(@Query("token") String token);

    @POST("/index/message/getMessage")
    Observable<BaseResponse<List<SendMessageResponse>>> getGroupMessage(@Query("togroup") int togroup, @Query("size") int size, @Query("token") String token);

    @POST("/index/message/sessionUser")
    Observable<BaseResponse<List<SendMessageResponse>>> getConversation(@Query("size") int size, @Query("token") String token);

    @POST("/index/users/lists")
    Observable<BaseResponse<ContactResponse>> getFriends(@Query("type") String type, @Query("token") String token);

    @POST("/index/users/lists")
    Observable<BaseResponse<ContactResponse>> getGroups(@Query("type") String type, @Query("token") String token);

    @POST("/api/user/search")
    Observable<BaseResponse<FriendEntity>> searchFriend(@Query("mobile") String mobile, @Query("token") String token);

    @POST("/index/message/getMessage")
    Observable<BaseResponse<List<SendMessageResponse>>> getMoreFriendsMessage(@Query("id") int id, @Query("touid") int touid, @Query("sort") int sort, @Query("size") int size, @Query("token") String token);

    @POST("/index/message/getMessage")
    Observable<BaseResponse<List<SendMessageResponse>>> getMoreGroupsMessage(@Query("id") int id, @Query("togroup") int togroup, @Query("sort") int sort, @Query("size") int size, @Query("token") String token);

    @POST("/index/users/tag")
    Observable<BaseResponse<Object>> modifyNote(@Query("fuid") int fuid, @Query("tag") String tag, @Query("token") String token);

    @POST("/index/users/removeTag")
    Observable<BaseResponse<Object>> deleteNote(@Query("fuid") int fuid, @Query("token") String token);

    @POST("/index/message/readMessage")
    Observable<BaseResponse<Object>> readMessage(@Query("id") int id, @Query("token") String token);

    @POST("/index/users/topMssage")
    Observable<BaseResponse<List<NotificationEntity>>> getNotification(@Query("token") String token);

    @POST("/index/users/remind")
    Observable<BaseResponse<Object>> setRemind(@Query("status") int status, @Query("token") String token);


    @POST("/index/guanggao/gettop")
    Observable<BaseResponse<GroupAdEntity>> getGroupAd(@Query("group_id") int group_id, @Query("token") String token);

    @POST("/index/login/logout")
    Observable<BaseResponse<Object>> logout(@Query("token") String token);


    /**
     * 查询助手-六合常用查询
     */
    @POST("http://2060900.com/index/helper/commonQuery")
    Observable<BaseResponse<LHCYModel>> getHelperLHCY();

    /**
     * 查询助手-冷热号码查询
     */
    @POST("http://2060900.com/index/helper/cold_hot_number")
    Observable<BaseResponse<LRHMModel>> getHelperLRHM(@Body HelperLRHMRequest request);

    @POST("/index/money_action/balance")
    Observable<BaseResponse<MyMoneyResponse>> getUserMoney(@Query("token") String token);

    @POST("/api/Red_Back/info")
    Observable<BaseResponse<SendRedPackMoneyCountLimitResponse>> getMoneyCountLimit();

    @POST("/index/redbaging/grabRedbag")
    Observable<BaseResponse<Object>> robRedPack(@Query("rid") int rid, @Query("act") String act, @Query("token") String token);

    @POST("/index/redbaging/getRedbag")
    Observable<BaseResponse<RedPackInformationResponse>> getRedbag(@Query("rid") int rid, @Query("token") String token);


    @POST("/index/money_action/balance")
    Observable<BaseResponse<Object>> getMyMoney(@Query("token") String token);

    @POST("/index/money_action/cashOut")
    Observable<BaseResponse<Object>> cashOut(@Query("amount") String amount, @Query("ptid") String ptid, @Query("account") String account, @Query("token") String token, @Query("pay_pwd") String pay_pwd);

    @POST("/index/platform/platformList")
    Observable<BaseResponse<MyPlatformEntity>> getPlatforms(@Query("token") String token);


    @POST("/api/em/zhuanpan")
    Observable<BaseResponse<ZhuanPanStatusEntity>> getZhuanpanState(@Query("token") String token);

    @POST("/api/em/fudong")
    Observable<BaseResponse<List<ZhuanPanStatusEntity>>> getFudongState(@Query("token") String token);


    @POST("/api/user/getSign")
    Observable<BaseResponse<List<String>>> getSignDays(@Query("token") String token);

    @POST("/api/user/sign")
    Observable<BaseResponse<Object>> sign(@Query("token") String token);

    @POST("/api/user/friends")
    Observable<BaseResponse<Object>> addFriend(@Query("mobile") String mobile, @Query("token") String token);

    /**
     * 上传图片
     */
    @Multipart
    @POST("upload2.php")
    Observable<BaseResponse<List<String>>> publishPics(@Part List<MultipartBody.Part> partLis);

    /**
     * 发表朋友圈
     */
    @POST("/api/circle_friends/add")
    Observable<BaseResponse<Object>> addMyNews(@Body AddMyNewsRequest addMyNewsRequest);

    /**
     * 获取朋友圈
     */
    @POST("/api/circle_friends/mine")
    Observable<BaseResponse<List<FriendNewsEntity>>> getFriendNews(@Query("token") String token, @Query("page") int page, @Query("size") int size);

    /**
     * 获取app下载地址
     */
    @POST("/api/activity/download")
    Observable<BaseResponse<AppDownloadUrlEntity>> getAppDownLoadUrl(@Query("token") String token);

    /**
     * 获取app下载地址
     */
    @POST("/api/user/signature")
    Observable<BaseResponse<Object>> modifySignature(@Query("signature") String signature, @Query("token") String token);


    /**
     * 在线举报
     */
    @POST("/api/user/actor")
    Observable<BaseResponse<Object>> report(@Body ReportRequest reportRequest);

    @POST("/api/emoticon/add")
    Observable<BaseResponse<LikeEmojiEntity>> addEmojis(@Query("src") String src, @Query("token") String token);

    /**
     * 六合图库—我的收藏内容
     */
    @POST("index/Lhtkmysc/mytksc")
    Observable<BaseResponse<List<LiuHeIndexItem>>> getLiuHeCollections();

    @POST("http://2060900.com/index/helper/tmzs")
    Observable<BaseResponse<List<TMZSModel2>>> getHelperTMZS();

    @POST("http://2060900.com/index/lhtks/getlhqi")
    Observable<BaseResponse<LiuHeDataResponse>> getAOMENLiuHeData(@Body LiuHeRequest request);

    @POST("http://2060900.com/index/lhtks/getlhinfo")
    Observable<BaseResponse<MediaInfo>> getAOMENLiuHeArticle(@Body LiuHeInfoRequest request);

    @POST("http://2060900.com/index/lhtks/getyear")
    Observable<BaseResponse<List<String>>> getAOMENLiuheYear(@Body LiuHeRequest liuHeRequest);

    @POST("http://2060900.com/index/Lhtks/getTksy")
    Observable<BaseResponse<LiuHeGalleryIndex>> getAOMENLiuHeIndex();

    @POST("http://2060900.com/index/xgtk/getyear")
    Observable<BaseResponse<List<String>>> getXIANGGANGLiuheYear(@Body LiuHeRequest liuHeRequest);

    @POST("http://2060900.com/index/xgtk/getlhqi")
    Observable<BaseResponse<LiuHeDataResponse>> getXIANGGANGLiuHeData(@Body LiuHeRequest request);

    @POST("http://2060900.com/index/xgtk/getlhinfo")
    Observable<BaseResponse<MediaInfo>> getXIANGGANGLiuHeArticle(@Body LiuHeInfoRequest request);

    @POST("http://2060900.com/index/xgtk/getTksy")
    Observable<BaseResponse<LiuHeGalleryIndex>> getXIANGGANGLiuHeIndex();


    /**
     * 在线举报
     */
    @POST("/api/helper/create")
    Observable<BaseResponse<Object>> multipleSend(@Body MultipleSendRequest multipleSendRequest);


    /**
     * 朋友圈点赞
     */
    @POST("/api/circle_friends/thumbs")
    Observable<BaseResponse<DigResponse>> dig(@Query("cfid") int cfid, @Query("token") String token);

    /**
     * 删除朋友圈
     */
    @POST("/api/circle_friends/del")
    Observable<BaseResponse> deleteFC(@Query("cfid") int cfid, @Query("token") String token);

    /**
     * 删除朋友圈评论
     */
    @POST("/api/circle_friends/delComment")
    Observable<BaseResponse> deleteFCComment(@Query("id") int id, @Query("token") String token);

    /**
     * 评论朋友圈
     */
    @POST("/api/circle_friends/comment")
    Observable<BaseResponse<FriendNewsEntity.CommentsBean>> sendFCComment(@Query("cfid") int cfid, @Query("content") String content, @Query("reply") int reply, @Query("token") String token);


    /**
     * 收藏
     */
    @POST("/api/user/userCollection")
    Observable<BaseResponse<Object>> addCollect(@Body CollectRequest collectRequest);


    @POST("/api/emoticon/del")
    Observable<BaseResponse<Object>> deleteEmoji(@Body DeleteEmojiRequest collectRequest);

    @POST("/api/emoticon/edit")
    Observable<BaseResponse<Object>> editEmoji(@Body EditEmojiRequest collectRequest);

    /**
     * 删除会话
     */
    @POST("/api/listen/conversation")
    Observable<BaseResponse<Object>> deleteConversation(@Query("toid") int toid, @Query("type") int type, @Query("tid") int tid, @Query("token") String token);


    /**
     * 删除会话
     */
    @POST("/api/Group/create")
    Observable<BaseResponse<Object>> createGroup(@Query("name") String name, @Query("img") String img, @Query("token") String token);

    @POST("/api/Group/editName")
    Observable<BaseResponse<Object>> editGroupName(@Query("name") String name, @Query("gid") int gid, @Query("token") String token);

    @POST(" /api/Group/editImg")
    Observable<BaseResponse<Object>> editGroupAvatar(@Query("img") String img, @Query("gid") int gid, @Query("token") String token);

    /**
     * 添加黑名单
     */
    @POST("/api/user/black")
    Observable<BaseResponse<BlackFriendResponse>> blackFriend(@Query("uid") int uid, @Query("token") String token);

    /**
     * 获取黑名单列表
     */
    @POST("/api/user/blacklist")
    Observable<BaseResponse<List<BlackFriendEntity>>> getBlackList(@Query("page") int page, @Query("size") int size, @Query("token") String token);

    /**
     * 获取我的收藏
     */
    @POST("/api/user/selfCollection")
    Observable<BaseResponse<List<CollectEntity>>> getCollect(@Query("page") int page, @Query("size") int size, @Query("token") String token);

    /**
     * 删除收藏
     */
    @POST("/api/user/delCollection")
    Observable<BaseResponse> deleteCollect(@Query("id") int id, @Query("token") String token);

    /**
     * 删除好友
     */
    @POST("/api/user/delFriend")
    Observable<BaseResponse> deleteFriend(@Query("uid") int uid, @Query("token") String token);


    /**
     * 获取好友申请列表
     */
    @POST("/api/user/getApply")
    Observable<BaseResponse<List<NewFriendApplyEntity>>> getNewFriendApplyList(@Query("token") String token);

    /**
     * 获取好友申请列表
     */
    @POST("/api/user/operate")
    Observable<BaseResponse> acceptFriend(@Query("id") int id, @Query("status") int status, @Query("token") String token);

    /**
     * 获取好友申请列表
     */
    @POST("/api/group/drop")
    Observable<BaseResponse> leaveGroup(@Query("gid") int gid, @Query("token") String token);

    /**
     * 获取活动列表
     */
    @POST("/api/activity/lists")
    Observable<BaseResponse<List<ActivityEntity>>> getActivityList(@Query("token") String token);

    /**
     * 获取活动列表
     */
    @POST("/index/Activity/getNewActivity")
    Observable<BaseResponse<List<ActiveModel>>> getActivities(@Query("token") String token);

    /**
     * 获取活动列表
     */
    @POST("/api/emoticon/lists")
    Observable<BaseResponse<List<LikeEmojiEntity>>> getLikeEmoji(@Query("token") String token);


    /**
     * 获取聊天历史图片
     */
    @POST("/index/message/getMessageImg")
    Observable<BaseResponse<List<SendMessageResponse.ImgBean>>> getImageHistoryMessage(@Query("token") String token, @Query("size") int size, @Query("page") int page, @Query("touid") int touid, @Query("togroup") int togroup);


    /**
     * 获取聊天历史资料
     */
    @POST("/index/message/getMessageZl")
    Observable<BaseResponse<List<SendMessageResponse.ImgBean>>> getZiliaoHistoryMessage(@Query("token") String token, @Query("size") int size, @Query("page") int page, @Query("touid") int touid, @Query("togroup") int togroup);


    /**
     * 获取活动列表
     */
    @POST("api/Webs/versioninfo")
    Observable<BaseResponse<AppVersion>> getVersion(@Query("type") int type);

    /**
     * 获取活动列表
     */
    @POST("api/user/applyStatus")
    Observable<BaseResponse<ApplyStatusResponse>> getApplyStatus(@Query("token") String token);


    /**
     * 获取活动列表
     */
    @POST("/api/find/index")
    Observable<BaseResponse<List<FindItemModel>>> getFindList();

    //首页弹窗信息
    @POST("api/webs/tcggs")
    Observable<BaseResponse<PopModel>> getPopInfo(@Body NoticePopRequest noticePopRequest);

    /**
     * 获取首页广告弹窗图片
     */
    @GET("api/webs/tanch")
    Observable<BaseResponse<HomeImageAdModel>> getHomeImageAd();

    /**
     * 获取首页广告弹窗图片
     */
    @GET("api/group/speak")
    Observable<BaseResponse> httpSetGroupNoTalk(@Query("type") int type, @Query("gid") int gid, @Query("token") String token);

    /**
     * 获取首页广告弹窗图片
     */
    @GET("api/mode/type")
    Observable<BaseResponse<PicChannel>> httpGetPicChannel(@Query("token") String token);


    @GET
    Observable<List<String>> callUrl(@Url String url, @Query("token") String token);

    @GET
    Observable<ResponseBody> testUrl(@Url String url);
}
