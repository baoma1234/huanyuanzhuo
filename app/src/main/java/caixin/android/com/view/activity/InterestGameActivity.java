package caixin.android.com.view.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityInterestGameBinding;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.MessageDialog;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.adapter.InterestGameAdapter;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.CustomerModel;
import caixin.android.com.entity.InterestGameModel;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.widget.CustomerWindow;

public class InterestGameActivity extends BaseActivity<ActivityInterestGameBinding, BaseViewModel> {

    private InterestGameAdapter interestGameAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_interest_game;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    private void initView() {
        mBinding.ivClose.setOnClickListener(v -> finish());
        RecyclerView rvContent = findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(this));
        interestGameAdapter = new InterestGameAdapter();
        rvContent.setAdapter(interestGameAdapter);
        interestGameAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    startActivity(ShakeActivity.class);
                    break;
                case 1:
                    startActivity(WaveAnimalActivity.class);
                    break;
                case 2:
                    startActivity(LoverActivity.class);
                    break;
                case 3:
                    startActivity(AnimalActivity.class);
                    break;
                case 4:
                    showMenuDialog();

                    break;
                case 5:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(Extras.TYPE, ActionUtil.ACTION_QUERY_LIUHE);
                    startActivity(QueryCommonActivity.class, bundle2);
                    break;
                case 6:
                    startActivity(ShengXiaoLingMaActivity.class);
                    break;
            }
        });
    }

    private CustomerWindow mPopupWindow;
    private List<CustomerModel> mCustomerModels;

    public void showMenuDialog() {
        if (mCustomerModels == null) {
            mCustomerModels = new ArrayList<>();
            mCustomerModels.add(new CustomerModel("追号助手（香港）"));
            mCustomerModels.add(new CustomerModel("追号助手（澳门）"));
        }
        if (mPopupWindow == null) {
            mPopupWindow = new CustomerWindow(this, mCustomerModels);
            mPopupWindow.setTitle("请选择");
            mPopupWindow.setOnItemClickListener(model -> {
                if (model.getName().equals("追号助手（香港）")) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt(Extras.TYPE, ActionUtil.ACTION_QUERY_NUMBER_XIANGGANG);
                    startActivity(QueryCommonActivity.class, bundle1);
                } else {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt(Extras.TYPE, ActionUtil.ACTION_QUERY_NUMBER_AOMEN);
                    startActivity(QueryCommonActivity.class, bundle2);
                }
            });
        }
        mPopupWindow.show();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        initData();
    }

    private void initData() {
        List<InterestGameModel> dataList = new ArrayList<>();
        InterestGameModel interestGameModel2 = new InterestGameModel();
        interestGameModel2.setLogo(R.mipmap.ic_shake);
        interestGameModel2.setName("幸運搖一搖");
        interestGameModel2.setTips("每期搖一搖，好運在這裡");

        InterestGameModel interestGameModel3 = new InterestGameModel();
        interestGameModel3.setLogo(R.mipmap.ic_turntable);
        interestGameModel3.setName("生肖大轉盤");
        interestGameModel3.setTips("轉出屬於你的幸運生肖");

        InterestGameModel interestGameModel4 = new InterestGameModel();
        interestGameModel4.setLogo(R.mipmap.ic_lover);
        interestGameModel4.setName("戀人特碼");
        interestGameModel4.setTips("快來看看專屬於你和Ta的戀人特碼");

        InterestGameModel interestGameModel5 = new InterestGameModel();
        interestGameModel5.setLogo(R.mipmap.ic_animal);
        interestGameModel5.setName("生肖卡牌");
        interestGameModel5.setTips("隱藏在卡牌中的生肖，帶來屬於你的財運");

        InterestGameModel interestGameModel7 = new InterestGameModel();
        interestGameModel7.setLogo(R.mipmap.ic_hot_cold_ma_query);
        interestGameModel7.setName("冷熱號碼查詢");
        interestGameModel7.setTips("冷熱號碼，精準對比，助您篩選");

        InterestGameModel interestGameModel8 = new InterestGameModel();
        interestGameModel8.setLogo(R.mipmap.ic_normal_query);
        interestGameModel8.setName("六合常識查詢");
        interestGameModel8.setTips("小白玩家不要急，六合常識在這裡");

        InterestGameModel interestGameModel9 = new InterestGameModel();
        interestGameModel9.setLogo(R.mipmap.ic_animal_table);
        interestGameModel9.setName("生肖灵码表");
        interestGameModel9.setTips("生肖靈碼不好記，一表在手，天下你有");

        dataList.add(interestGameModel2);
        dataList.add(interestGameModel3);
        dataList.add(interestGameModel4);
        dataList.add(interestGameModel5);
        dataList.add(interestGameModel7);
        dataList.add(interestGameModel8);
        dataList.add(interestGameModel9);

        interestGameAdapter.setNewData(dataList);
    }

    @Override
    public void initViewObservable() {

    }
}