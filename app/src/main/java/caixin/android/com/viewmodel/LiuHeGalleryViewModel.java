package caixin.android.com.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.LiuHeDataResponse;
import caixin.android.com.entity.LiuHeGalleryIndex;
import caixin.android.com.entity.LiuHeIndexItem;
import caixin.android.com.entity.LiuHeInfoRequest;
import caixin.android.com.entity.LiuHeRequest;
import caixin.android.com.entity.MediaInfo;
import caixin.android.com.entity.TMZSModel2;
import caixin.android.com.http.UserCenterModel;

public class LiuHeGalleryViewModel extends BaseViewModel<UserCenterModel> {

    public LiuHeGalleryViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public LiuHeGalleryViewModel.UIChangeObservable uc = new LiuHeGalleryViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<LiuHeGalleryIndex> mLiuHeIndexLiveData = new MutableLiveData<>();
        public MutableLiveData<List<LiuHeIndexItem>> mLiuHeCollectionsLiveData = new MutableLiveData<>();
        public MutableLiveData<LiuHeDataResponse> mLiuHeDataResponseLiveData = new MutableLiveData<>();
        public MutableLiveData<List<String>> mLiuHeYearResponseLiveData = new MutableLiveData<>();
        public MutableLiveData<List<TMZSModel2>> mTMZSLiveData = new MutableLiveData<>();
        public MutableLiveData<MediaInfo> mMediaInfoLiveData = new MutableLiveData<>();
    }

    public void getAOMENLiuHeIndex() {
        showDialog();
        mModel.httpGetAOMENLiuHeIndex(this, new BaseModel.Callback<LiuHeGalleryIndex>() {
            @Override
            public void onSuccess(LiuHeGalleryIndex data, String mes) {
                uc.mLiuHeIndexLiveData.postValue(data);
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

    public void getXIANGGANGLiuHeIndex() {
        showDialog();
        mModel.httpGetXIANGGANGLiuHeIndex(this, new BaseModel.Callback<LiuHeGalleryIndex>() {
            @Override
            public void onSuccess(LiuHeGalleryIndex data, String mes) {
                uc.mLiuHeIndexLiveData.postValue(data);
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

    public void getLiuHeCollections() {
        mModel.httpGetLiuHeCollections(this, new BaseModel.Callback<List<LiuHeIndexItem>>() {
            @Override
            public void onSuccess(List<LiuHeIndexItem> data, String mes) {
                uc.mLiuHeCollectionsLiveData.postValue(data);
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


    public void getAOMENLiuHeData(LiuHeRequest request) {
        mModel.httpGetAOMENLiuHeData(this, request, new BaseModel.Callback<LiuHeDataResponse>() {
            @Override
            public void onSuccess(LiuHeDataResponse data, String mes) {
                uc.mLiuHeDataResponseLiveData.postValue(data);
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

    public void getXIANGGANGLiuHeData(LiuHeRequest request) {
        mModel.httpGetXIANGGANGLiuHeData(this, request, new BaseModel.Callback<LiuHeDataResponse>() {
            @Override
            public void onSuccess(LiuHeDataResponse data, String mes) {
                uc.mLiuHeDataResponseLiveData.postValue(data);
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


    public void getAOMENLiuHeYear(int typeid) {
        mModel.httpGetAOMENLiuHeYear(this, new LiuHeRequest(typeid), new BaseModel.Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> data, String mes) {
                uc.mLiuHeYearResponseLiveData.postValue(data);
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

    public void getXIANGGANGLiuHeYear(int typeid) {
        mModel.httpGetXIANGGANGLiuHeYear(this, new LiuHeRequest(typeid), new BaseModel.Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> data, String mes) {
                uc.mLiuHeYearResponseLiveData.postValue(data);
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


    public void getAOMENLiuHeArticle(LiuHeInfoRequest request) {
        mModel.httpGetAOMENLiuHeArticle(this, request, new BaseModel.Callback<MediaInfo>() {
            @Override
            public void onSuccess(MediaInfo data, String mes) {
                uc.mMediaInfoLiveData.postValue(data);
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

    public void getXIANGGANGLiuHeArticle(LiuHeInfoRequest request) {
        mModel.httpGetXIANGGANGLiuHeArticle(this, request, new BaseModel.Callback<MediaInfo>() {
            @Override
            public void onSuccess(MediaInfo data, String mes) {
                uc.mMediaInfoLiveData.postValue(data);
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


    public void getHelperTMZS() {
        showDialog();
        mModel.httpGetHelperTMZS(this, new BaseModel.Callback<List<TMZSModel2>>() {
            @Override
            public void onSuccess(List<TMZSModel2> data, String mes) {
                uc.mTMZSLiveData.postValue(data);
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
}
