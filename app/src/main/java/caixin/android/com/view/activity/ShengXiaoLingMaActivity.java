package caixin.android.com.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityShengXiaoLingMaBinding;

import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.utils.MMKVUtil;

public class ShengXiaoLingMaActivity extends BaseActivity<ActivityShengXiaoLingMaBinding, BaseViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sheng_xiao_ling_ma;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.ivClose.setOnClickListener(v -> finish());
    }

    @Override
    public void initViewObservable() {

    }
}