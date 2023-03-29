package caixin.android.com.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import caixin.android.com.base.BaseModel;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.MemberEntity;
import caixin.android.com.entity.MyMoneyResponse;
import caixin.android.com.entity.SendRedPackMoneyCountLimitResponse;
import caixin.android.com.http.UserCenterModel;
import caixin.android.com.utils.MMKVUtil;

public class SendRedPackViewModel extends BaseViewModel<UserCenterModel> {

    public SendRedPackViewModel.UIChangeObservable uc = new SendRedPackViewModel.UIChangeObservable();

    public class UIChangeObservable {
        public MutableLiveData<MyMoneyResponse> getUserMoney = new MutableLiveData<MyMoneyResponse>();
        public MutableLiveData<SendRedPackMoneyCountLimitResponse> getMoneyCountLimit = new MutableLiveData<SendRedPackMoneyCountLimitResponse>();
        public MutableLiveData<List<MemberEntity>> getGroupMembers = new MutableLiveData<>();
        public MutableLiveData<Boolean> sendRedPackToFriend = new MutableLiveData<Boolean>();
        public MutableLiveData<Boolean> sendRedPackToGroup = new MutableLiveData<Boolean>();
    }

    public SendRedPackViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }

    public void getUserMoney() {
        mModel.getUserMoney(this, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<MyMoneyResponse>() {
            @Override
            public void onSuccess(MyMoneyResponse data, String msg) {
                uc.getUserMoney.postValue(data);
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

    private static final String TAG = "SendRedPackViewModel";

    public void getMoneyCountLimit() {
        showDialog();
        mModel.getMoneyCountLimit(this, new BaseModel.Callback<SendRedPackMoneyCountLimitResponse>() {
            @Override
            public void onSuccess(SendRedPackMoneyCountLimitResponse data, String msg) {
                dismissDialog();
                uc.getMoneyCountLimit.postValue(data);
                Log.e(TAG, " getMoneyCountLimit onSuccess: ");
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                showShortToast(msg);
                Log.e(TAG, "getMoneyCountLimit onFailure: " + msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void getGroupMember(int groupId) {
        showDialog();
        mModel.httpGetGroupMember(this, groupId, MMKVUtil.getUserInfo().getId(), new BaseModel.Callback<List<MemberEntity>>() {
            @Override
            public void onSuccess(List<MemberEntity> data, String mes) {
                uc.getGroupMembers.postValue(data);
                dismissDialog();
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

    public void sendRedPackToFriend(int touids, String content, double money, String payPass) {
        showDialog();
        mModel.sendRedPackToFriend(MMKVUtil.getUserInfo().getId(), touids, content, money, payPass, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.sendRedPackToFriend.postValue(true);
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                uc.sendRedPackToFriend.postValue(false);
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

    public void sendRedPackToGroup(int togroups, String content, int size, double money, String payPass) {
        showDialog();
        mModel.sendRedPackToGroup(MMKVUtil.getUserInfo().getId(), togroups, content, size, money, payPass, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.sendRedPackToGroup.postValue(true);
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                uc.sendRedPackToGroup.postValue(false);
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }


    public void sendRedPackExclusive(int touids, String content, double money, int togroup, String payPass) {
        showDialog();
        mModel.sendRedPackExclusive(MMKVUtil.getUserInfo().getId(), touids, content, money, payPass, togroup, new BaseModel.Callback() {
            @Override
            public void onSuccess(Object data, String msg) {
                dismissDialog();
                uc.sendRedPackToFriend.postValue(true);
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
                uc.sendRedPackToFriend.postValue(false);
                showShortToast(msg);
            }

            @Override
            public void onDisconnected(String msg) {

            }
        });
    }

}
