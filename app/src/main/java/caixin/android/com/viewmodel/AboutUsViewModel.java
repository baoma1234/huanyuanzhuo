package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import caixin.android.com.http.UserCenterModel;
import caixin.android.com.base.BaseViewModel;

public class AboutUsViewModel extends BaseViewModel<UserCenterModel> {
    public AboutUsViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }
}
