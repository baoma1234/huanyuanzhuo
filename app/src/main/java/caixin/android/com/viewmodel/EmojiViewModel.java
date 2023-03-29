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

public class EmojiViewModel extends BaseViewModel<UserCenterModel> {

    public EmojiViewModel.UIChangeObservable uc = new EmojiViewModel.UIChangeObservable();

    public class UIChangeObservable {

    }

    public EmojiViewModel(@NonNull Application application, UserCenterModel model) {
        super(application, model);
    }


   /* public void sendRedPackToGroup(int togroups, String content, int size, double money, String payPass) {
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
    }*/
}
