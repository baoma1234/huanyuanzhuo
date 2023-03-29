package caixin.android.com.view.activity;

import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityQueryCodeDetailBinding;

import java.util.List;

import caixin.android.com.adapter.QueryCodeDetailAdapter;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.LRHMModel;

public class QueryCodeDetailActivity extends BaseActivity<ActivityQueryCodeDetailBinding, BaseViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_query_code_detail;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        List<LRHMModel.HotdataBean> hotdataBeanList = (List<LRHMModel.HotdataBean>) getIntent().getSerializableExtra(Extras.LRHMMODEL_ITEM);
        LRHMModel.ColdHotDataBean coldHotDataBean = (LRHMModel.ColdHotDataBean) getIntent().getSerializableExtra(Extras.CURRENT_MODEL);
        mBinding.ivClose.setOnClickListener(v -> finish());
        mBinding.rvContent.setLayoutManager(new LinearLayoutManager(this));
        QueryCodeDetailAdapter mAdapter = new QueryCodeDetailAdapter(hotdataBeanList, coldHotDataBean);
        mBinding.rvContent.setAdapter(mAdapter);
    }

    @Override
    public void initViewObservable() {

    }
}