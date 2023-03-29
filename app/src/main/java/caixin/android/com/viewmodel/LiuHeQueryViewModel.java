package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.AppVersion;
import caixin.android.com.entity.ApplyStatusResponse;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.entity.HelperLRHMRequest;
import caixin.android.com.entity.HomeImageAdModel;
import caixin.android.com.entity.LHCYModel;
import caixin.android.com.entity.LRHMModel;
import caixin.android.com.entity.NoticePopRequest;
import caixin.android.com.entity.PopModel;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.http.basic.exception.base.BaseException;
import caixin.android.com.utils.ToastUtils;

public class LiuHeQueryViewModel extends BaseViewModel<UserCenterModel> {

    public LiuHeQueryViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public UIChangeObservable uc = new UIChangeObservable();


    public class UIChangeObservable {
        public MutableLiveData<HomeImageAdModel> mImageAdLiveData = new MutableLiveData<>();
        public MutableLiveData<LHCYModel> mHelperLHCYLiveData = new MutableLiveData<>();
        public MutableLiveData<LRHMModel> mHelperLRHMLiveData = new MutableLiveData<>();
    }

    public void getHelperLHCY() {
        showDialog();
        mModel.getHelperLHCY(this, new BaseModel.Callback<LHCYModel>() {
            @Override
            public void onSuccess(LHCYModel data, String msg) {
                uc.mHelperLHCYLiveData.postValue(data);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.show(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void getHelperLRHM(HelperLRHMRequest request) {
        showDialog();
        mModel.getHelperLRHM(this, request, new BaseModel.Callback<LRHMModel>() {
            @Override
            public void onSuccess(LRHMModel data, String msg) {
                uc.mHelperLRHMLiveData.postValue(data);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                ToastUtils.show(msg);
                dismissDialog();
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }
}
