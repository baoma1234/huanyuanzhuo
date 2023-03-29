package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivitySignBinding;

import java.util.HashMap;
import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.utils.DateUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.SignViewModel;
import caixin.android.com.widget.signcalendar.ZWCalendarView;

public class SignActivity extends BaseActivity<ActivitySignBinding, SignViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sign;
    }

    @Override
    public SignViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SignViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        
        mBinding.titleBar.ivClose.setOnClickListener(v -> finish());
        mBinding.titleBar.title.setText(getResources().getString(R.string.daily_sign));
        mViewModel.getSignDays();
        mBinding.tvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.sign();
            }
        });

        findViewById(R.id.calendar_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.calendarView.showPreviousMonth();
            }
        });

        findViewById(R.id.calendar_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.calendarView.showNextMonth();
            }
        });

        mBinding.calendarView.setSelectListener(new ZWCalendarView.SelectListener() {
            @Override
            public void change(int year, int month) {
                mBinding.tvCalendarShow.setText(String.format("%s 年 %s 月", year, month));
            }

            @Override
            public void select(int year, int month, int day, int week) {

            }
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.getSignDays.observe(this, this::handleGetSignDays);
        mViewModel.uc.sign.observe(this, this::handleSign);
    }

    private static final String TAG = "SignActivity";

    private void handleSign(Object o) {
        String today = DateUtil.LongToString(System.currentTimeMillis());
        Log.e(TAG, "handleSign: " + today);
        signs.put(today, true);
        mBinding.calendarView.setSignRecords(signs);
    }

    HashMap<String, Boolean> signs = new HashMap<>();

    private void handleGetSignDays(List<String> o) {
        if (o != null && !o.isEmpty()) {
            signs.clear();
            for (int i = 0; i < o.size(); i++) {
                signs.put(o.get(i), true);
            }
            mBinding.calendarView.setSignRecords(signs);
        }
    }
}
