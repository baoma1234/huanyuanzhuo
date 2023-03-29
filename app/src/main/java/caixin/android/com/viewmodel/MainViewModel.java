package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.entity.AppVersion;
import caixin.android.com.entity.ApplyStatusResponse;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.entity.HomeImageAdModel;
import caixin.android.com.entity.NoticePopRequest;
import caixin.android.com.entity.PopModel;
import caixin.android.com.entity.UserInfo;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.http.basic.exception.base.BaseException;

public class MainViewModel extends BaseViewModel<UserCenterModel> {

    public MainViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public UIChangeObservable uc = new UIChangeObservable();


    public class UIChangeObservable {
        public MutableLiveData<AppVersion> getVersion = new MutableLiveData<>();
        public MutableLiveData<PopModel> mPopLiveData = new MutableLiveData<>();
        public MutableLiveData<HomeImageAdModel> mImageAdLiveData = new MutableLiveData<>();
        public MutableLiveData<List<FindItemModel>> getHomeMenuList = new MutableLiveData<>();
        public MutableLiveData<ApplyStatusResponse> getApplyStatus = new MutableLiveData<>();
    }


    public void getVersion() {
        mModel.getVersion(this, new BaseModel.Callback<AppVersion>() {
            @Override
            public void onSuccess(AppVersion data, String msg) {
                uc.getVersion.postValue(data);
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

    public void getApplyStatus() {
        mModel.getApplyStatus(this, new BaseModel.Callback<ApplyStatusResponse>() {

            @Override
            public void onSuccess(ApplyStatusResponse data, String msg) {
                uc.getApplyStatus.postValue(data);
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

    public void getPopInfo(NoticePopRequest request) {
        mModel.httpGetPopInfo(this, request, new BaseModel.Callback<PopModel>() {
            @Override
            public void onSuccess(PopModel data, String msg) {
                uc.mPopLiveData.postValue(data);
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

    public void getHomeImageAd() {
        mModel.httpGetHomeImageAd(this, new BaseModel.Callback<HomeImageAdModel>() {
            @Override
            public void onSuccess(HomeImageAdModel data, String msg) {
                uc.mImageAdLiveData.postValue(data);
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


    public void getHomeMenuList() {
        mModel.httpGetFindList(this, new BaseModel.Callback<List<FindItemModel>>() {
            @Override
            public void onSuccess(List<FindItemModel> data, String mes) {
                uc.getHomeMenuList.postValue(data);
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

}
