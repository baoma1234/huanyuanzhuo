package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.ActivityEntity;
import caixin.android.com.entity.AddMyNewsRequest;
import caixin.android.com.entity.CollectRequest;
import caixin.android.com.entity.DigResponse;
import caixin.android.com.entity.FriendNewsEntity;
import caixin.android.com.entity.OOSInfoEntity;
import caixin.android.com.http.UserCenterModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FriendCommunityViewModel extends BaseViewModel<UserCenterModel> {

    public FriendCommunityViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }


    public class UIChangeObservable {
        //密码开关观察者
        public MutableLiveData<List<String>> publishPics = new MutableLiveData<>();
        public MutableLiveData<Object> addMyNews = new MutableLiveData<>();
        public MutableLiveData<List<FriendNewsEntity>> getFriendNews = new MutableLiveData<>();
        public MutableLiveData<DigResponse> dig = new MutableLiveData<>();
        public MutableLiveData<Object> deleteCF = new MutableLiveData<>();
        public MutableLiveData<List<ActivityEntity>> getActivityList = new MutableLiveData<>();
        public MutableLiveData<Object> deleteCFComment = new MutableLiveData<>();
        public MutableLiveData<Object> addCollect = new MutableLiveData<>();
        public MutableLiveData<OOSInfoEntity> getOOSInfo = new MutableLiveData<>();
        public MutableLiveData<FriendNewsEntity.CommentsBean> sendFCComment = new MutableLiveData<>();
    }

    public FriendCommunityViewModel.UIChangeObservable uc = new FriendCommunityViewModel.UIChangeObservable();

    public void sendFCComment(int cfid, String content, int replyId) {
        showDialog();
        mModel.httpSendFCComment(this, cfid, content, replyId, new BaseModel.Callback<FriendNewsEntity.CommentsBean>() {
            @Override
            public void onSuccess(FriendNewsEntity.CommentsBean data, String msg) {
                dismissDialog();
                uc.sendFCComment.postValue(data);
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

    public void getActivityList() {
        mModel.httpGetActivityList(this, new BaseModel.Callback<List<ActivityEntity>>() {
            @Override
            public void onSuccess(List<ActivityEntity> data, String msg) {
                dismissDialog();
                uc.getActivityList.postValue(data);
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

    public void deleteFriendCicle(int cfid) {
        showDialog();
        mModel.httpDeleteFC(this, cfid, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.deleteCF.postValue(data);
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

    public void deleteFriendCicleComment(int commentId) {
        showDialog();
        mModel.httpDeleteFCComment(this, commentId, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.deleteCFComment.postValue(data);
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

    public void collectComment(CollectRequest collectRequest) {
        showDialog();
        mModel.httpAddCollect(this, collectRequest, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.addCollect.postValue(data);
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

    //点赞
    public void dig(int id) {
        showDialog();
        mModel.httpDig(this, id, new BaseModel.Callback<DigResponse>() {
            @Override
            public void onSuccess(DigResponse data, String msg) {
                dismissDialog();
                uc.dig.postValue(data);
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

    public void getFriendNews(int page) {
        mModel.httpGetFriendNews(this, page, new BaseModel.Callback<List<FriendNewsEntity>>() {
            @Override
            public void onSuccess(List<FriendNewsEntity> data, String msg) {
                dismissDialog();
                uc.getFriendNews.postValue(data);
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

    public void addMyNews(AddMyNewsRequest addMyNewsRequest) {
        mModel.httpAddMyNews(this, addMyNewsRequest, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.addMyNews.postValue(data);
                showShortToast(msg);
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

    public void publishPictures(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("url", "huanqiutp");
        for (int i = 0; i < files.size(); i++) {
            builder.addFormDataPart("file[" + i + "]", files.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), files.get(i)));
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        mModel.httpPublishImgs(this, parts, new BaseModel.Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> data, String msg) {
                uc.publishPics.postValue(data);
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
