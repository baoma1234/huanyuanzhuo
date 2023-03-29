package caixin.android.com.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.CustomerModel;
import caixin.android.com.entity.MOMOExchangeRecord;
import caixin.android.com.entity.MoneyRecodeEntity;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.entity.MyPlatformEntity;
import caixin.android.com.entity.NotificationEntity;
import caixin.android.com.entity.UserInfo;
import caixin.android.com.entity.chatroom.RedPackInformationResponse;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.http.basic.exception.base.BaseException;
import caixin.android.com.utils.MMKVUtil;

public class MomoExchangeViewModel extends BaseViewModel<UserCenterModel> {

    //封装一个界面发生改变的观察者
    public MomoExchangeViewModel.UIChangeObservable uc = new MomoExchangeViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<MyPlatformEntity> mPlatformsLiveData = new MutableLiveData<>();
        public MutableLiveData<Boolean> cashOut = new MutableLiveData<>();
        public MutableLiveData<MyMoneyResponse> getMoney = new MutableLiveData<>();
        public MutableLiveData<MOMOExchangeRecord> moneyRecode = new MutableLiveData<>();
        public MutableLiveData<List<MoneyRecodeEntity>> getMoneyRecodeList = new MutableLiveData<>();
        public MutableLiveData<Boolean> setPayPass = new MutableLiveData<>();
    }

    public MomoExchangeViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void getPlatforms() {
        mModel.getPlatforms(this, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<MyPlatformEntity>() {
            @Override
            public void onSuccess(MyPlatformEntity data, String msg) {
                uc.mPlatformsLiveData.postValue(data);
                showShortToast(msg);
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

    public void exchangeMOMO(String amount, String ptid, String account,String pay_pwd) {
        showDialog();
        mModel.cashOut(this, MMKVUtil.getUserInfo().getId(), amount, ptid, account,pay_pwd, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                getMyMoney();
                uc.cashOut.postValue(true);
                showShortToast(msg);
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                uc.cashOut.postValue(false);
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
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


    public void getMOMOExchangeRecords() {
        showDialog();
        mModel.httpGetMOMOExchangeRecords(this, new BaseModel.Callback<MOMOExchangeRecord>() {
            @Override
            public void onSuccess(MOMOExchangeRecord data, String msg) {
                dismissDialog();
                uc.moneyRecode.postValue(data);
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


    public void getMoneyRecodeList(int page) {
        mModel.getMoneyRecodeList(this, page, new BaseModel.Callback<List<MoneyRecodeEntity>>() {
            @Override
            public void onSuccess(List<MoneyRecodeEntity> data, String msg) {
                uc.getMoneyRecodeList.postValue(data);
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


    public void setPayPass(String pay_pwd) {
        mModel.setPayPass(this, pay_pwd, new BaseModel.Callback<Object>() {
            @Override
            public void onSuccess(Object data, String msg) {
                uc.setPayPass.postValue(true);
                showShortToast(msg);
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
}
