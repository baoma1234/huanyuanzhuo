package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.http.UserCenterModel;


public class RegisterViewModel extends BaseViewModel<UserCenterModel> {

    public RegisterViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public RegisterViewModel.UIChangeObservable uc = new RegisterViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<Object> register = new MutableLiveData<>();
        public MutableLiveData<Object> getVerifyCode = new MutableLiveData<>();
    }

    public void getVerifyCode(String mobile, String type) {
        showDialog();
        mModel.httpGetVerifyCode(this, mobile, type, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.getVerifyCode.postValue(data);
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


    /* public void verifyCode(VerifyCodeRequest request) {
         model.verifyCode(request)
                 .compose(RxUtils.schedulersTransformer()) //线程调度
                 .doOnSubscribe(RegisterViewModel.this) //请求与ViewModel周期同步
                 .doOnSubscribe(disposable -> showDialog("加载中"))
                 .subscribe((Consumer<BaseResponse<VerifyCodeResponse>>) entity -> {
                     dismissDialog();
                     if (entity.getErrorcode() == 200) {
                         uc.verifyCode.setValue(entity.getData().getUid());
                     } else {
                         showShortToast(entity.getMsg());
                     }
                 }, new Consumer<Throwable>() {
                     @Override
                     public void accept(Throwable throwable) throws Exception {
                         dismissDialog();
                         showShortToast(throwable.getMessage());
                     }
                 });
     }*/
    private static final String TAG = "RegisterViewModel";

    public void register(String phone, String nickName, String password, String code, String inviteCode) {
        showDialog();
        mModel.httpRegister(this, code, phone, password, nickName,inviteCode, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String mes) {
                uc.register.postValue(data);
                dismissDialog();
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {
                showShortToast(msg);
                dismissDialog();
            }
        });
    }
}
