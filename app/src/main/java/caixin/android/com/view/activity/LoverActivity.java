package caixin.android.com.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityLoverBinding;
import com.youjie.datepicker.date.DatePickerDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.utils.LotteryUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.ToastUtils;

public class LoverActivity extends BaseActivity<ActivityLoverBinding, BaseViewModel> {

    private DatePickerDialogFragment datePickerDialogFragment;
    private int yourDate = 0;
    private int loverDate = 0;
    private List<Integer> lotteryList = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_lover;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    private void initView() {
        mBinding.cbMale.setClickable(false);
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        datePickerDialogFragment = new DatePickerDialogFragment();
        if (MMKVUtil.getLoveList() == null || MMKVUtil.getLoveList().size() == 0) {
            mBinding.llOperation.setVisibility(View.VISIBLE);
            mBinding.llLottery.setVisibility(View.GONE);
        } else {
            mBinding.llOperation.setVisibility(View.GONE);
            mBinding.llLottery.setVisibility(View.VISIBLE);
            setData(MMKVUtil.getLoveList());
        }
        mBinding.cbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBinding.cbMale.isChecked()) {
                    mBinding.cbMale.setChecked(true);
                }
                if (mBinding.cbFemale.isChecked()) {
                    mBinding.cbFemale.setChecked(false);
                }
                mBinding.cbMale.setClickable(false);
                mBinding.cbFemale.setClickable(true);
            }
        });
        mBinding.cbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBinding.cbFemale.isChecked()) {
                    mBinding.cbFemale.setChecked(true);
                }
                if (mBinding.cbMale.isChecked()) {
                    mBinding.cbMale.setChecked(false);
                }
                mBinding.cbMale.setClickable(true);
                mBinding.cbFemale.setClickable(false);
            }
        });

        mBinding.tvYourDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogFragment.setOnDateChooseListener((year, month, day) -> {
                    yourDate = 1;
                    mBinding.tvYourDate.setTextColor(getResources().getColor(R.color.main_black));
                    mBinding.tvYourDate.setText(year + "-" + month + "-" + day);
                });
                datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
            }
        });
        mBinding.tvLoverDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogFragment.setOnDateChooseListener((year, month, day) -> {
                    loverDate = 1;
                    mBinding.tvLoverDate.setTextColor(getResources().getColor(R.color.main_black));
                    mBinding.tvLoverDate.setText(year + "-" + month + "-" + day);
                });
                datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
            }
        });
        mBinding.tvMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yourDate == 0) {
                    ToastUtils.show("请选择您的生日");
                } else if (loverDate == 0) {
                    ToastUtils.show("请选择恋人的生日");
                } else {
                    showDialog();
                    Random rand = new Random();
                    for (int i = 0; i < 49; i++) {
                        int lotteryNum = rand.nextInt(49) + 1;
                        if (!lotteryList.contains(lotteryNum)) {
                            lotteryList.add(lotteryNum);
                        }
                        if (lotteryList.size() == 6) {
                            break;
                        }
                    }
                    new Handler().postDelayed(() -> {
                        MMKVUtil.setLoverList(lotteryList);
                        mBinding.llOperation.setVisibility(View.GONE);
                        mBinding.llLottery.setVisibility(View.VISIBLE);
                        setData(lotteryList);
                    }, 1000);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        datePickerDialogFragment = null;
    }

    private void setData(List<Integer> list) {
        dismissDialog();
        //中奖号码
        mBinding.tvOne.setText(String.valueOf(list.get(0)));
        mBinding.tvTwo.setText(String.valueOf(list.get(1)));
        mBinding.tvThree.setText(String.valueOf(list.get(2)));
        mBinding.tvFour.setText(String.valueOf(list.get(3)));
        mBinding.tvFive.setText(String.valueOf(list.get(4)));
        mBinding.tvSix.setText(String.valueOf(list.get(5)));
        //设置波色
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(0)), mBinding.tvOne);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(1)), mBinding.tvTwo);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(2)), mBinding.tvThree);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(3)), mBinding.tvFour);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(4)), mBinding.tvFive);
        LotteryUtil.setColor(LotteryUtil.getColor(list.get(5)), mBinding.tvSix);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public void initViewObservable() {

    }
}