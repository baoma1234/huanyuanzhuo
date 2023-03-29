package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.AppDownloadUrlEntity;
import caixin.android.com.entity.BlackFriendEntity;
import caixin.android.com.entity.BlackFriendResponse;
import caixin.android.com.entity.CollectEntity;
import caixin.android.com.entity.ImageResponse;
import caixin.android.com.entity.MultipleSendRequest;
import caixin.android.com.entity.ReportRequest;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HomeViewModel extends BaseViewModel<UserCenterModel> {
    public HomeViewModel.UIChangeObservable uc = new HomeViewModel.UIChangeObservable();


    public class UIChangeObservable {
        public MutableLiveData<AppDownloadUrlEntity> getAppDownLoadUrl = new MutableLiveData<>();
        public MutableLiveData<List<String>> publishPics = new MutableLiveData<>();
        public MutableLiveData<Object> report = new MutableLiveData<>();
        public MutableLiveData<List<String>> publishImg = new MutableLiveData<>();
        public MutableLiveData<List<BlackFriendEntity>> getBlackList = new MutableLiveData<>();
        public MutableLiveData<BlackFriendResponse> blackFriend = new MutableLiveData<>();
        public MutableLiveData<List<CollectEntity>> getCollect = new MutableLiveData<>();
        public MutableLiveData<Object> deleteCollect = new MutableLiveData<>();
    }

    public HomeViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void publishChatPicture(File file) {
        showDialog();
 /*       MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("url", "caixin_android");
        builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
        List<MultipartBody.Part> parts = builder.build().parts();*/
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("url", "huanqiutp");
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


    public void getCollect(int page) {
        showDialog();
        mModel.httpGetCollect(this, page, new BaseModel.Callback<List<CollectEntity>>() {
            @Override
            public void onSuccess(List<CollectEntity> data, String mes) {
                uc.getCollect.postValue(data);
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

    public void deleteCollect(int id) {
        showDialog();
        mModel.httpDeleteCollect(this, id, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.deleteCollect.postValue(data);
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

    public void getBlackList(int page) {
        showDialog();
        mModel.httpGetBlackList(this, page, new BaseModel.Callback<List<BlackFriendEntity>>() {
            @Override
            public void onSuccess(List<BlackFriendEntity> data, String mes) {
                uc.getBlackList.postValue(data);
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

    public void multipleSend(MultipleSendRequest multipleSendRequest) {
        multipleSendRequest.setToken(MMKVUtil.getToken());
        mModel.multipleSend(multipleSendRequest, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.report.postValue(data);
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

    public void report(ReportRequest reportRequest) {
        mModel.httpReport(this, reportRequest, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.report.postValue(data);
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
        builder.addFormDataPart("url", "huanqiujb");
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
