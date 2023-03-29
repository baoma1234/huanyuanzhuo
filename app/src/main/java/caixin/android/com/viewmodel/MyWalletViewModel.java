package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.http.UserCenterModel;


public class MyWalletViewModel extends BaseViewModel<UserCenterModel> {

    public MyWalletViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public MyWalletViewModel.UIChangeObservable uc = new MyWalletViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<MyMoneyResponse> getMoney = new MutableLiveData<>();
    }

    public void getMyMoney() {
        mModel.httpGetMoney(this, new BaseModel.Callback<MyMoneyResponse>() {
            @Override
            public void onSuccess(MyMoneyResponse data, String mes) {
                uc.getMoney.postValue(data);
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


    private static final String TAG = "RegisterViewModel";


}
