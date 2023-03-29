package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.ActiveModel;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;


public class ActivityViewModel extends BaseViewModel<UserCenterModel> {
    public ActivityViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public ActivityViewModel.UIChangeObservable uc = new ActivityViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<List<ActiveModel>> getActivities = new MutableLiveData<>();
    }

    public void getActivities() {
        showDialog();
        mModel.httpGetActivities(this, new BaseModel.Callback<List<ActiveModel>>() {
            @Override
            public void onSuccess(List<ActiveModel> data, String mes) {
                uc.getActivities.postValue(data);
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
}
