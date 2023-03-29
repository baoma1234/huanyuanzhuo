package caixin.android.com.view.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;


import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityChooseCodeHelperBinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.TMZSModel2;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.utils.GsonUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.LiuHeGalleryViewModel;

public class ChooseCodeHelperActivity extends BaseActivity<ActivityChooseCodeHelperBinding, LiuHeGalleryViewModel> {
    private List<Integer> mDan = new ArrayList<>();
    private List<Integer> mDa = new ArrayList<>();
    private List<Integer> mXiao = new ArrayList<>();
    private List<Integer> mDadan = new ArrayList<>();
    private List<Integer> mShuang = new ArrayList<>();
    private List<Integer> mDashuang = new ArrayList<>();
    private List<Integer> mXiaodan = new ArrayList<>();
    private List<Integer> mXiaoshuang = new ArrayList<>();
    private List<Integer> mHedan = new ArrayList<>();
    private List<Integer> mHeshuang = new ArrayList<>();
    private List<Integer> mHeda = new ArrayList<>();
    private List<Integer> mHexiao = new ArrayList<>();
    private List<Integer> mJin = new ArrayList<>();
    private List<Integer> mMu = new ArrayList<>();
    private List<Integer> mShui = new ArrayList<>();
    private List<Integer> mHuo = new ArrayList<>();
    private List<Integer> mTu = new ArrayList<>();
    private List<Integer> mLanbo = new ArrayList<>();
    private List<Integer> mHongbo = new ArrayList<>();
    private List<Integer> mLvbo = new ArrayList<>();
    private List<Integer> mJiaqian = new ArrayList<>();
    private List<Integer> mYeshou = new ArrayList<>();
    private List<Integer> mHongdan = new ArrayList<>();
    private List<Integer> mHongshuang = new ArrayList<>();
    private List<Integer> mLandan = new ArrayList<>();
    private List<Integer> mLanshuang = new ArrayList<>();
    private List<Integer> mLvdan = new ArrayList<>();
    private List<Integer> mLvshuang = new ArrayList<>();
    private List<Integer> mShu = new ArrayList<>();
    private List<Integer> mNiu = new ArrayList<>();
    private List<Integer> mHu = new ArrayList<>();
    private List<Integer> mTuzi = new ArrayList<>();

    private List<Integer> mLong = new ArrayList<>();
    private List<Integer> mShe = new ArrayList<>();
    private List<Integer> mMa = new ArrayList<>();
    private List<Integer> mYang = new ArrayList<>();
    private List<Integer> mHou = new ArrayList<>();
    private List<Integer> mJi = new ArrayList<>();
    private List<Integer> mGou = new ArrayList<>();
    private List<Integer> mZhu = new ArrayList<>();
    private List<Integer> mWei0 = new ArrayList<>();
    private List<Integer> mWei1 = new ArrayList<>();
    private List<Integer> mWei2 = new ArrayList<>();
    private List<Integer> mWei3 = new ArrayList<>();
    private List<Integer> mWei4 = new ArrayList<>();
    private List<Integer> mWei5 = new ArrayList<>();
    private List<Integer> mWei6 = new ArrayList<>();
    private List<Integer> mWei7 = new ArrayList<>();
    private List<Integer> mWei8 = new ArrayList<>();
    private List<Integer> mWei9 = new ArrayList<>();
    private List<Integer> mDawei = new ArrayList<>();
    private List<Integer> mXiaowei = new ArrayList<>();
    private List<Integer> mTou0 = new ArrayList<>();
    private List<Integer> mTou1 = new ArrayList<>();
    private List<Integer> mTou2 = new ArrayList<>();
    private List<Integer> mTou3 = new ArrayList<>();
    private List<Integer> mTou4 = new ArrayList<>();
    private List<Integer> mMen1 = new ArrayList<>();
    private List<Integer> mMen2 = new ArrayList<>();
    private List<Integer> mMen3 = new ArrayList<>();
    private List<Integer> mMen4 = new ArrayList<>();
    private List<Integer> mMen5 = new ArrayList<>();
    private List<Integer> mDuan1 = new ArrayList<>();
    private List<Integer> mDuan2 = new ArrayList<>();
    private List<Integer> mDuan3 = new ArrayList<>();
    private List<Integer> mDuan4 = new ArrayList<>();
    private List<Integer> mDuan5 = new ArrayList<>();
    private List<Integer> mDuan6 = new ArrayList<>();
    private List<Integer> mDuan7 = new ArrayList<>();
    private List<Integer> mHe1 = new ArrayList<>();
    private List<Integer> mHe2 = new ArrayList<>();
    private List<Integer> mHe3 = new ArrayList<>();
    private List<Integer> mHe4 = new ArrayList<>();
    private List<Integer> mHe5 = new ArrayList<>();
    private List<Integer> mHe6 = new ArrayList<>();
    private List<Integer> mHe7 = new ArrayList<>();
    private List<Integer> mHe8 = new ArrayList<>();
    private List<Integer> mHe9 = new ArrayList<>();
    private List<Integer> mHe10 = new ArrayList<>();
    private List<Integer> mHe11 = new ArrayList<>();
    private List<Integer> mHe12 = new ArrayList<>();
    private List<Integer> mHe13 = new ArrayList<>();
    private List<Integer> mToudan0 = new ArrayList<>();
    private List<Integer> mToudan1 = new ArrayList<>();
    private List<Integer> mToudan2 = new ArrayList<>();
    private List<Integer> mToudan3 = new ArrayList<>();
    private List<Integer> mToudan4 = new ArrayList<>();
    private List<Integer> mToushuang0 = new ArrayList<>();
    private List<Integer> mToushuang1 = new ArrayList<>();
    private List<Integer> mToushuang2 = new ArrayList<>();
    private List<Integer> mToushuang3 = new ArrayList<>();
    private List<Integer> mToushuang4 = new ArrayList<>();


    private List<Integer> mAll = new ArrayList<>();


    private void initData() {
        for (int i = 1; i <= 49; i++) {
            mAll.add(i);
            if (i % 2 != 0) {
                mDan.add(i);
                if (i < 10 || i > 20 && i < 30 || i > 40) {
                    mHedan.add(i);//合单
                } else {
                    mHeshuang.add(i);//合双
                }

            } else {
                mShuang.add(i);
                if (!(i < 10 || i > 20 && i < 30 || i > 40)) {
                    mHedan.add(i);//合单
                } else {
                    mHeshuang.add(i);
                }
            }
            if (i >= 25) {
                if (i % 2 != 0) {
                    mDadan.add(i);
                } else {
                    mDashuang.add(i);
                }
                mDa.add(i);
            } else {
                mXiao.add(i);
                if (i % 2 != 0) {
                    mXiaodan.add(i);
                } else {
                    mXiaoshuang.add(i);
                }
            }
            if (i < 10 && i > 6 || i > 15 && i < 20 || i > 24 && i < 30 || i > 33 && i < 40 || i > 42) {
                mHeda.add(i);
            } else {
                mHexiao.add(i);
            }
            if (i == 1 || i == 2 || i == 7 || i == 8 || i == 12 || i == 13 || i == 18 || i == 19
                    || i == 23 || i == 24 || i == 29 || i == 30 || i == 34 || i == 35 || i == 40 || i == 45 || i == 46) {
                mHongbo.add(i);
                if (i % 2 != 0) {
                    mHongdan.add(i);
                } else {
                    mHongshuang.add(i);
                }
            } else if (i == 3 || i == 4 || i == 9 || i == 10 || i == 14 || i == 15 || i == 20 || i == 25 || i == 26 ||
                    i == 31 || i == 36 || i == 37 || i == 41 || i == 42 || i == 47 || i == 48) {
                if (i % 2 != 0) {
                    mLandan.add(i);
                } else {
                    mLanshuang.add(i);
                }
                mLanbo.add(i);
            } else {
                if (i % 2 != 0) {
                    mLvdan.add(i);
                } else {
                    mLvshuang.add(i);
                }
                mLvbo.add(i);
            }
            if (i % 10 == 0) {
                mWei0.add(i);
                mXiaowei.add(i);
            }
            if (i % 10 == 1) {
                mWei1.add(i);
                mXiaowei.add(i);
            }
            if (i % 10 == 2) {
                mWei2.add(i);
                mXiaowei.add(i);
            }
            if (i % 10 == 3) {
                mWei3.add(i);
                mXiaowei.add(i);
            }
            if (i % 10 == 4) {
                mWei4.add(i);
                mXiaowei.add(i);
            }
            if (i % 10 == 5) {
                mWei5.add(i);
                mDawei.add(i);
            }
            if (i % 10 == 6) {
                mWei6.add(i);
                mDawei.add(i);
            }
            if (i % 10 == 7) {
                mWei7.add(i);
                mDawei.add(i);
            }
            if (i % 10 == 8) {
                mWei8.add(i);
                mDawei.add(i);
            }
            if (i % 10 == 9) {
                mWei9.add(i);
                mDawei.add(i);
            }
            if (i <= 9) {
                mMen1.add(i);
                mMen2.add(i + 9);
                mMen3.add(i + 18);
                mMen4.add(i + 27);
                mMen5.add(i + 37);
                if (i == 9) {
                    mMen4.add(37);
                    mMen5.add(47);
                    mMen5.add(48);
                    mMen5.add(49);
                }

            }
            if (i <= 7) {
                mDuan1.add(i);
                mDuan2.add(7 + i);
                mDuan3.add(7 * 2 + i);
                mDuan4.add(7 * 3 + i);
                mDuan5.add(7 * 4 + i);
                mDuan6.add(7 * 5 + i);
                mDuan7.add(7 * 6 + i);
            }
            if (i < 10) {
                mTou0.add(i);
                if (i % 2 == 0) {
                    mToushuang0.add(i);
                } else {
                    mToudan0.add(i);
                }
            }
            if (i < 20 && i >= 10) {
                mTou1.add(i);
                if (i % 2 == 0) {
                    mToushuang1.add(i);
                } else {
                    mToudan1.add(i);
                }
            }
            if (i < 30 && i >= 20) {
                mTou2.add(i);
                if (i % 2 == 0) {
                    mToushuang2.add(i);
                } else {
                    mToudan2.add(i);
                }
            }
            if (i < 40 && i >= 30) {
                mTou3.add(i);
                if (i % 2 == 0) {
                    mToushuang3.add(i);
                } else {
                    mToudan3.add(i);
                }
            }
            if (i >= 40) {
                mTou4.add(i);
                if (i % 2 == 0) {
                    mToushuang4.add(i);
                } else {
                    mToudan4.add(i);
                }
            }
        }

        mHe1.add(1);
        mHe1.add(10);
        mHe2.add(2);
        mHe2.add(11);
        mHe2.add(20);
        mHe3.add(3);
        mHe3.add(12);
        mHe3.add(21);
        mHe3.add(30);
        for (int i = 4; i <= 40; i += 9) {
            mHe4.add(i);
            mHe5.add(i + 1);
            mHe6.add(i + 2);
            mHe7.add(i + 3);
            mHe8.add(i + 4);
            mHe9.add(i + 5);
            mHe10.add(i + 6);
        }
        mHe10.remove(0);
        mHe11.add(29);
        mHe11.add(38);
        mHe11.add(47);
        mHe12.add(39);
        mHe12.add(48);
        mHe13.add(49);
    }


    private void initClick() {
        mBinding.tvCopy.setOnClickListener(v -> {
            ClipboardManager clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clip.setText(GsonUtil.toJson(mAll)); // 复制
            showShortToast("复制成功");
        });
        mBinding.tvClear.setOnClickListener(v -> {
            mBinding.codeLayout.tvDan.setSelected(false);
            mBinding.codeLayout.tvShuang.setSelected(false);

            mBinding.codeLayout.tvDa.setSelected(false);
            mBinding.codeLayout.tvXiao.setSelected(false);

            mBinding.codeLayout.tvDadan.setSelected(false);
            mBinding.codeLayout.tvDashuang.setSelected(false);
            mBinding.codeLayout.tvXiaodan.setSelected(false);
            mBinding.codeLayout.tvXiaoshuang.setSelected(false);

            mBinding.codeLayout.tvHedan.setSelected(false);
            mBinding.codeLayout.tvHeshuang.setSelected(false);
            mBinding.codeLayout.tvHeda.setSelected(false);
            mBinding.codeLayout.tvHexiao.setSelected(false);

            mBinding.codeLayout.tvJin.setSelected(false);
            mBinding.codeLayout.tvMu.setSelected(false);
            mBinding.codeLayout.tvShui.setSelected(false);
            mBinding.codeLayout.tvHuo.setSelected(false);
            mBinding.codeLayout.tvTu.setSelected(false);

            mBinding.codeLayout.tvGou.setSelected(false);
            mBinding.codeLayout.tvJi.setSelected(false);
            mBinding.codeLayout.tvHou.setSelected(false);
            mBinding.codeLayout.tvYang.setSelected(false);
            mBinding.codeLayout.tvMa.setSelected(false);
            mBinding.codeLayout.tvShe.setSelected(false);
            mBinding.codeLayout.tvNiu.setSelected(false);
            mBinding.codeLayout.tvLong.setSelected(false);
            mBinding.codeLayout.tvTuzi.setSelected(false);
            mBinding.codeLayout.tvHu.setSelected(false);
            mBinding.codeLayout.tvShu.setSelected(false);
            mBinding.codeLayout.tvZhu.setSelected(false);

            mBinding.codeLayout.tvHongbo.setSelected(false);
            mBinding.codeLayout.tvLanbo.setSelected(false);
            mBinding.codeLayout.tvLvbo.setSelected(false);

            mBinding.codeLayout.tvJiaqian.setSelected(false);
            mBinding.codeLayout.tvYeshou.setSelected(false);

            mBinding.codeLayout.tvHongdan.setSelected(false);
            mBinding.codeLayout.tvHongshuang.setSelected(false);
            mBinding.codeLayout.tvLandan.setSelected(false);
            mBinding.codeLayout.tvLanshuang.setSelected(false);
            mBinding.codeLayout.tvLvdan.setSelected(false);
            mBinding.codeLayout.tvLvshuang.setSelected(false);

            mBinding.codeLayout.tvWei0.setSelected(false);
            mBinding.codeLayout.tvWei1.setSelected(false);
            mBinding.codeLayout.tvWei2.setSelected(false);
            mBinding.codeLayout.tvWei3.setSelected(false);
            mBinding.codeLayout.tvWei4.setSelected(false);
            mBinding.codeLayout.tvWei5.setSelected(false);
            mBinding.codeLayout.tvWei6.setSelected(false);
            mBinding.codeLayout.tvWei7.setSelected(false);
            mBinding.codeLayout.tvWei8.setSelected(false);
            mBinding.codeLayout.tvWei9.setSelected(false);
            mBinding.codeLayout.tvDawei.setSelected(false);
            mBinding.codeLayout.tvXiaowei.setSelected(false);

            mBinding.codeLayout.tvTou0.setSelected(false);
            mBinding.codeLayout.tvTou1.setSelected(false);
            mBinding.codeLayout.tvTou2.setSelected(false);
            mBinding.codeLayout.tvTou3.setSelected(false);
            mBinding.codeLayout.tvTou4.setSelected(false);

            mBinding.codeLayout.tvMen1.setSelected(false);
            mBinding.codeLayout.tvMen2.setSelected(false);
            mBinding.codeLayout.tvMen3.setSelected(false);
            mBinding.codeLayout.tvMen4.setSelected(false);
            mBinding.codeLayout.tvMen5.setSelected(false);

            mBinding.codeLayout.tvDuan1.setSelected(false);
            mBinding.codeLayout.tvDuan2.setSelected(false);
            mBinding.codeLayout.tvDuan3.setSelected(false);
            mBinding.codeLayout.tvDuan4.setSelected(false);
            mBinding.codeLayout.tvDuan5.setSelected(false);
            mBinding.codeLayout.tvDuan6.setSelected(false);
            mBinding.codeLayout.tvDuan7.setSelected(false);

            mBinding.codeLayout.tvHe1.setSelected(false);
            mBinding.codeLayout.tvHe2.setSelected(false);
            mBinding.codeLayout.tvHe3.setSelected(false);
            mBinding.codeLayout.tvHe4.setSelected(false);
            mBinding.codeLayout.tvHe5.setSelected(false);
            mBinding.codeLayout.tvHe6.setSelected(false);
            mBinding.codeLayout.tvHe7.setSelected(false);
            mBinding.codeLayout.tvHe8.setSelected(false);
            mBinding.codeLayout.tvHe9.setSelected(false);
            mBinding.codeLayout.tvHe10.setSelected(false);
            mBinding.codeLayout.tvHe11.setSelected(false);
            mBinding.codeLayout.tvHe12.setSelected(false);
            mBinding.codeLayout.tvHe13.setSelected(false);

            mBinding.codeLayout.tvToudan0.setSelected(false);
            mBinding.codeLayout.tvToudan1.setSelected(false);
            mBinding.codeLayout.tvToudan2.setSelected(false);
            mBinding.codeLayout.tvToudan3.setSelected(false);
            mBinding.codeLayout.tvToudan4.setSelected(false);
            mBinding.codeLayout.tvToushuang0.setSelected(false);
            mBinding.codeLayout.tvToushuang1.setSelected(false);
            mBinding.codeLayout.tvToushuang2.setSelected(false);
            mBinding.codeLayout.tvToushuang3.setSelected(false);
            mBinding.codeLayout.tvToushuang4.setSelected(false);
            matchCode();
        });

        mBinding.codeLayout.tvDan.setOnClickListener(v -> {
            if (mBinding.codeLayout.tvDan.isSelected()) {
                mBinding.codeLayout.tvDan.setSelected(false);
            } else {
                mBinding.codeLayout.tvDan.setSelected(true);
                mBinding.codeLayout.tvShuang.setSelected(false);
            }
            matchCode();
        });
        mBinding.codeLayout.tvShuang.setOnClickListener(v -> {
            if (mBinding.codeLayout.tvShuang.isSelected()) {
                mBinding.codeLayout.tvShuang.setSelected(false);
            } else {
                mBinding.codeLayout.tvShuang.setSelected(true);
                mBinding.codeLayout.tvDan.setSelected(false);
            }
            matchCode();
        });
        mBinding.codeLayout.tvDa.setOnClickListener(v -> {
            if (mBinding.codeLayout.tvDa.isSelected()) {
                mBinding.codeLayout.tvDa.setSelected(false);
            } else {
                mBinding.codeLayout.tvDa.setSelected(true);
                mBinding.codeLayout.tvXiao.setSelected(false);
            }
            matchCode();
        });
        mBinding.codeLayout.tvXiao.setOnClickListener(v -> {
            if (mBinding.codeLayout.tvXiao.isSelected()) {
                mBinding.codeLayout.tvXiao.setSelected(false);
            } else {
                mBinding.codeLayout.tvXiao.setSelected(true);
                mBinding.codeLayout.tvDa.setSelected(false);
            }
            matchCode();
        });
        mBinding.codeLayout.tvDadan.setOnClickListener(v -> {
            mBinding.codeLayout.tvDadan.setSelected(!mBinding.codeLayout.tvDadan.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvXiaodan.setOnClickListener(v -> {
            mBinding.codeLayout.tvXiaodan.setSelected(!mBinding.codeLayout.tvXiaodan.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDashuang.setOnClickListener(v -> {
            mBinding.codeLayout.tvDashuang.setSelected(!mBinding.codeLayout.tvDashuang.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvXiaoshuang.setOnClickListener(v -> {
            mBinding.codeLayout.tvXiaoshuang.setSelected(!mBinding.codeLayout.tvXiaoshuang.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHedan.setOnClickListener(v -> {
            mBinding.codeLayout.tvHedan.setSelected(!mBinding.codeLayout.tvHedan.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHeshuang.setOnClickListener(v -> {
            mBinding.codeLayout.tvHeshuang.setSelected(!mBinding.codeLayout.tvHeshuang.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHeda.setOnClickListener(v -> {
            mBinding.codeLayout.tvHeda.setSelected(!mBinding.codeLayout.tvHeda.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHexiao.setOnClickListener(v -> {
            mBinding.codeLayout.tvHexiao.setSelected(!mBinding.codeLayout.tvHexiao.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvJin.setOnClickListener(v -> {
            mBinding.codeLayout.tvJin.setSelected(!mBinding.codeLayout.tvJin.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvMu.setOnClickListener(v -> {
            mBinding.codeLayout.tvMu.setSelected(!mBinding.codeLayout.tvMu.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvShui.setOnClickListener(v -> {
            mBinding.codeLayout.tvShui.setSelected(!mBinding.codeLayout.tvShui.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHuo.setOnClickListener(v -> {
            mBinding.codeLayout.tvHuo.setSelected(!mBinding.codeLayout.tvHuo.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvTu.setOnClickListener(v -> {
            mBinding.codeLayout.tvTu.setSelected(!mBinding.codeLayout.tvTu.isSelected());
            matchCode();
        });

        mBinding.codeLayout.tvHongbo.setOnClickListener(v -> {
            mBinding.codeLayout.tvHongbo.setSelected(!mBinding.codeLayout.tvHongbo.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvLanbo.setOnClickListener(v -> {
            mBinding.codeLayout.tvLanbo.setSelected(!mBinding.codeLayout.tvLanbo.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvLvbo.setOnClickListener(v -> {
            mBinding.codeLayout.tvLvbo.setSelected(!mBinding.codeLayout.tvLvbo.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvJiaqian.setOnClickListener(v -> {
            if (mBinding.codeLayout.tvJiaqian.isSelected()) {
                mBinding.codeLayout.tvJiaqian.setSelected(false);
            } else {
                mBinding.codeLayout.tvJiaqian.setSelected(true);
                mBinding.codeLayout.tvYeshou.setSelected(false);
            }
            matchCode();
        });
        mBinding.codeLayout.tvYeshou.setOnClickListener(v -> {
            if (mBinding.codeLayout.tvYeshou.isSelected()) {
                mBinding.codeLayout.tvYeshou.setSelected(false);
            } else {
                mBinding.codeLayout.tvYeshou.setSelected(true);
                mBinding.codeLayout.tvJiaqian.setSelected(false);
            }
            matchCode();
        });
        mBinding.codeLayout.tvHongdan.setOnClickListener(v -> {
            mBinding.codeLayout.tvHongdan.setSelected(!mBinding.codeLayout.tvHongdan.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHongshuang.setOnClickListener(v -> {
            mBinding.codeLayout.tvHongshuang.setSelected(!mBinding.codeLayout.tvHongshuang.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvLandan.setOnClickListener(v -> {
            mBinding.codeLayout.tvLandan.setSelected(!mBinding.codeLayout.tvLandan.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvLanshuang.setOnClickListener(v -> {
            mBinding.codeLayout.tvLanshuang.setSelected(!mBinding.codeLayout.tvLanshuang.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvLvdan.setOnClickListener(v -> {
            mBinding.codeLayout.tvLvdan.setSelected(!mBinding.codeLayout.tvLvdan.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvLvshuang.setOnClickListener(v -> {
            mBinding.codeLayout.tvLvshuang.setSelected(!mBinding.codeLayout.tvLvshuang.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvShu.setOnClickListener(v -> {
            mBinding.codeLayout.tvShu.setSelected(!mBinding.codeLayout.tvShu.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvNiu.setOnClickListener(v -> {
            mBinding.codeLayout.tvNiu.setSelected(!mBinding.codeLayout.tvNiu.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHu.setOnClickListener(v -> {
            mBinding.codeLayout.tvHu.setSelected(!mBinding.codeLayout.tvHu.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvTuzi.setOnClickListener(v -> {
            mBinding.codeLayout.tvTuzi.setSelected(!mBinding.codeLayout.tvTuzi.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvLong.setOnClickListener(v -> {
            mBinding.codeLayout.tvLong.setSelected(!mBinding.codeLayout.tvLong.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvShe.setOnClickListener(v -> {
            mBinding.codeLayout.tvShe.setSelected(!mBinding.codeLayout.tvShe.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvMa.setOnClickListener(v -> {
            mBinding.codeLayout.tvMa.setSelected(!mBinding.codeLayout.tvMa.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvYang.setOnClickListener(v -> {
            mBinding.codeLayout.tvYang.setSelected(!mBinding.codeLayout.tvYang.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHou.setOnClickListener(v -> {
            mBinding.codeLayout.tvHou.setSelected(!mBinding.codeLayout.tvHou.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvJi.setOnClickListener(v -> {
            mBinding.codeLayout.tvJi.setSelected(!mBinding.codeLayout.tvJi.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvGou.setOnClickListener(v -> {
            mBinding.codeLayout.tvGou.setSelected(!mBinding.codeLayout.tvGou.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvZhu.setOnClickListener(v -> {
            mBinding.codeLayout.tvZhu.setSelected(!mBinding.codeLayout.tvZhu.isSelected());
            matchCode();
        });

        mBinding.codeLayout.tvWei0.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei0.setSelected(!mBinding.codeLayout.tvWei0.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei1.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei1.setSelected(!mBinding.codeLayout.tvWei1.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei2.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei2.setSelected(!mBinding.codeLayout.tvWei2.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei3.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei3.setSelected(!mBinding.codeLayout.tvWei3.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei4.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei4.setSelected(!mBinding.codeLayout.tvWei4.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei5.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei5.setSelected(!mBinding.codeLayout.tvWei5.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei6.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei6.setSelected(!mBinding.codeLayout.tvWei6.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei7.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei7.setSelected(!mBinding.codeLayout.tvWei7.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei8.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei8.setSelected(!mBinding.codeLayout.tvWei8.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvWei9.setOnClickListener(v -> {
            mBinding.codeLayout.tvWei9.setSelected(!mBinding.codeLayout.tvWei9.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDawei.setOnClickListener(v -> {
            mBinding.codeLayout.tvDawei.setSelected(!mBinding.codeLayout.tvDawei.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvXiaowei.setOnClickListener(v -> {
            mBinding.codeLayout.tvXiaowei.setSelected(!mBinding.codeLayout.tvXiaowei.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvTou0.setOnClickListener(v -> {
            mBinding.codeLayout.tvTou0.setSelected(!mBinding.codeLayout.tvTou0.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvTou1.setOnClickListener(v -> {
            mBinding.codeLayout.tvTou1.setSelected(!mBinding.codeLayout.tvTou1.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvTou2.setOnClickListener(v -> {
            mBinding.codeLayout.tvTou2.setSelected(!mBinding.codeLayout.tvTou2.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvTou3.setOnClickListener(v -> {
            mBinding.codeLayout.tvTou3.setSelected(!mBinding.codeLayout.tvTou3.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvTou4.setOnClickListener(v -> {
            mBinding.codeLayout.tvTou4.setSelected(!mBinding.codeLayout.tvTou4.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvMen1.setOnClickListener(v -> {
            mBinding.codeLayout.tvMen1.setSelected(!mBinding.codeLayout.tvMen1.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvMen2.setOnClickListener(v -> {
            mBinding.codeLayout.tvMen2.setSelected(!mBinding.codeLayout.tvMen2.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvMen3.setOnClickListener(v -> {
            mBinding.codeLayout.tvMen3.setSelected(!mBinding.codeLayout.tvMen3.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvMen4.setOnClickListener(v -> {
            mBinding.codeLayout.tvMen4.setSelected(!mBinding.codeLayout.tvMen4.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvMen5.setOnClickListener(v -> {
            mBinding.codeLayout.tvMen5.setSelected(!mBinding.codeLayout.tvMen5.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDuan1.setOnClickListener(v -> {
            mBinding.codeLayout.tvDuan1.setSelected(!mBinding.codeLayout.tvDuan1.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDuan2.setOnClickListener(v -> {
            mBinding.codeLayout.tvDuan2.setSelected(!mBinding.codeLayout.tvDuan2.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDuan3.setOnClickListener(v -> {
            mBinding.codeLayout.tvDuan3.setSelected(!mBinding.codeLayout.tvDuan3.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDuan4.setOnClickListener(v -> {
            mBinding.codeLayout.tvDuan4.setSelected(!mBinding.codeLayout.tvDuan4.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDuan5.setOnClickListener(v -> {
            mBinding.codeLayout.tvDuan5.setSelected(!mBinding.codeLayout.tvDuan5.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDuan6.setOnClickListener(v -> {
            mBinding.codeLayout.tvDuan6.setSelected(!mBinding.codeLayout.tvDuan6.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvDuan7.setOnClickListener(v -> {
            mBinding.codeLayout.tvDuan7.setSelected(!mBinding.codeLayout.tvDuan7.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe1.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe1.setSelected(!mBinding.codeLayout.tvHe1.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe2.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe2.setSelected(!mBinding.codeLayout.tvHe2.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe3.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe3.setSelected(!mBinding.codeLayout.tvHe3.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe4.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe4.setSelected(!mBinding.codeLayout.tvHe4.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe5.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe5.setSelected(!mBinding.codeLayout.tvHe5.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe6.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe6.setSelected(!mBinding.codeLayout.tvHe6.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe7.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe7.setSelected(!mBinding.codeLayout.tvHe7.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe8.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe8.setSelected(!mBinding.codeLayout.tvHe8.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe9.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe9.setSelected(!mBinding.codeLayout.tvHe9.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe10.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe10.setSelected(!mBinding.codeLayout.tvHe10.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe11.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe11.setSelected(!mBinding.codeLayout.tvHe11.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe12.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe12.setSelected(!mBinding.codeLayout.tvHe12.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvHe13.setOnClickListener(v -> {
            mBinding.codeLayout.tvHe13.setSelected(!mBinding.codeLayout.tvHe13.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToudan0.setOnClickListener(v -> {
            mBinding.codeLayout.tvToudan0.setSelected(!mBinding.codeLayout.tvToudan0.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToudan1.setOnClickListener(v -> {
            mBinding.codeLayout.tvToudan1.setSelected(!mBinding.codeLayout.tvToudan1.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToudan2.setOnClickListener(v -> {
            mBinding.codeLayout.tvToudan2.setSelected(!mBinding.codeLayout.tvToudan2.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToudan3.setOnClickListener(v -> {
            mBinding.codeLayout.tvToudan3.setSelected(!mBinding.codeLayout.tvToudan3.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToudan4.setOnClickListener(v -> {
            mBinding.codeLayout.tvToudan4.setSelected(!mBinding.codeLayout.tvToudan4.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToushuang0.setOnClickListener(v -> {
            mBinding.codeLayout.tvToushuang0.setSelected(!mBinding.codeLayout.tvToushuang0.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToushuang1.setOnClickListener(v -> {
            mBinding.codeLayout.tvToushuang1.setSelected(!mBinding.codeLayout.tvToushuang1.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToushuang2.setOnClickListener(v -> {
            mBinding.codeLayout.tvToushuang2.setSelected(!mBinding.codeLayout.tvToushuang2.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToushuang3.setOnClickListener(v -> {
            mBinding.codeLayout.tvToushuang3.setSelected(!mBinding.codeLayout.tvToushuang3.isSelected());
            matchCode();
        });
        mBinding.codeLayout.tvToushuang4.setOnClickListener(v -> {
            mBinding.codeLayout.tvToushuang4.setSelected(!mBinding.codeLayout.tvToushuang4.isSelected());
            matchCode();
        });

    }

    private void matchCode() {
        mAll.clear();
        for (int j = 1; j <= 49; j++) {
            mAll.add(j);
        }
        List<Integer> list = new ArrayList<>();
        if (mBinding.codeLayout.tvDan.isSelected()) {
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!mDan.contains(next))
                    iterator.remove();
            }
        }
        if (mBinding.codeLayout.tvShuang.isSelected()) {
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!mShuang.contains(next))
                    iterator.remove();
            }
        }
        if (mBinding.codeLayout.tvDa.isSelected()) {
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!mDa.contains(next))
                    iterator.remove();
            }
        }
        if (mBinding.codeLayout.tvXiao.isSelected()) {
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!mXiao.contains(next))
                    iterator.remove();
            }
        }
        if (mBinding.codeLayout.tvDadan.isSelected() ||
                mBinding.codeLayout.tvDashuang.isSelected() ||
                mBinding.codeLayout.tvXiaodan.isSelected() ||
                mBinding.codeLayout.tvXiaoshuang.isSelected()) {
            if (mBinding.codeLayout.tvDadan.isSelected()) {
                list.addAll(mDadan);
            }
            if (mBinding.codeLayout.tvDashuang.isSelected()) {
                list.addAll(mDashuang);
            }
            if (mBinding.codeLayout.tvXiaodan.isSelected()) {
                list.addAll(mXiaodan);
            }
            if (mBinding.codeLayout.tvXiaoshuang.isSelected()) {
                list.addAll(mXiaoshuang);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvHedan.isSelected() ||
                mBinding.codeLayout.tvHeshuang.isSelected() ||
                mBinding.codeLayout.tvHeda.isSelected() ||
                mBinding.codeLayout.tvHexiao.isSelected()) {
            if (mBinding.codeLayout.tvHedan.isSelected()) {
                list.addAll(mHedan);
            }
            if (mBinding.codeLayout.tvHeshuang.isSelected()) {
                list.addAll(mHeshuang);
            }
            if (mBinding.codeLayout.tvHeda.isSelected()) {
                list.addAll(mHeda);
            }
            if (mBinding.codeLayout.tvHexiao.isSelected()) {
                list.addAll(mHexiao);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvJin.isSelected() ||
                mBinding.codeLayout.tvMu.isSelected() ||
                mBinding.codeLayout.tvShui.isSelected() ||
                mBinding.codeLayout.tvHuo.isSelected() ||
                mBinding.codeLayout.tvTu.isSelected()) {
            if (mBinding.codeLayout.tvJin.isSelected()) {
                list.addAll(mJin);
            }
            if (mBinding.codeLayout.tvMu.isSelected()) {
                list.addAll(mMu);
            }
            if (mBinding.codeLayout.tvShui.isSelected()) {
                list.addAll(mShui);
            }
            if (mBinding.codeLayout.tvHuo.isSelected()) {
                list.addAll(mHuo);
            }
            if (mBinding.codeLayout.tvTu.isSelected()) {
                list.addAll(mTu);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvHongbo.isSelected() ||
                mBinding.codeLayout.tvLanbo.isSelected() ||
                mBinding.codeLayout.tvLvbo.isSelected()) {
            if (mBinding.codeLayout.tvHongbo.isSelected()) {
                list.addAll(mHongbo);
            }
            if (mBinding.codeLayout.tvLanbo.isSelected()) {
                list.addAll(mLanbo);
            }
            if (mBinding.codeLayout.tvLvbo.isSelected()) {
                list.addAll(mLvbo);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        if (mBinding.codeLayout.tvJiaqian.isSelected()) {
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!mJiaqian.contains(next))
                    iterator.remove();
            }
        }
        if (mBinding.codeLayout.tvYeshou.isSelected()) {
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!mYeshou.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvHongdan.isSelected() ||
                mBinding.codeLayout.tvHongshuang.isSelected() ||
                mBinding.codeLayout.tvLandan.isSelected() ||
                mBinding.codeLayout.tvLanshuang.isSelected() ||
                mBinding.codeLayout.tvLvdan.isSelected() ||
                mBinding.codeLayout.tvLvshuang.isSelected()) {
            if (mBinding.codeLayout.tvHongdan.isSelected()) {
                list.addAll(mHongdan);
            }
            if (mBinding.codeLayout.tvHongshuang.isSelected()) {
                list.addAll(mHongshuang);
            }
            if (mBinding.codeLayout.tvLandan.isSelected()) {
                list.addAll(mLandan);
            }
            if (mBinding.codeLayout.tvLanshuang.isSelected()) {
                list.addAll(mLanshuang);
            }
            if (mBinding.codeLayout.tvLvdan.isSelected()) {
                list.addAll(mLvdan);
            }
            if (mBinding.codeLayout.tvLvshuang.isSelected()) {
                list.addAll(mLvshuang);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvShu.isSelected() ||
                mBinding.codeLayout.tvNiu.isSelected() ||
                mBinding.codeLayout.tvHu.isSelected() ||
                mBinding.codeLayout.tvTuzi.isSelected() ||
                mBinding.codeLayout.tvLong.isSelected() ||
                mBinding.codeLayout.tvShe.isSelected() ||
                mBinding.codeLayout.tvMa.isSelected() ||
                mBinding.codeLayout.tvYang.isSelected() ||
                mBinding.codeLayout.tvHou.isSelected() ||
                mBinding.codeLayout.tvJi.isSelected() ||
                mBinding.codeLayout.tvGou.isSelected() ||
                mBinding.codeLayout.tvZhu.isSelected()) {
            if (mBinding.codeLayout.tvShu.isSelected()) {
                list.addAll(mShu);
            }
            if (mBinding.codeLayout.tvNiu.isSelected()) {
                list.addAll(mNiu);
            }
            if (mBinding.codeLayout.tvHu.isSelected()) {
                list.addAll(mHu);
            }
            if (mBinding.codeLayout.tvTuzi.isSelected()) {
                list.addAll(mTuzi);
            }
            if (mBinding.codeLayout.tvLong.isSelected()) {
                list.addAll(mLong);
            }
            if (mBinding.codeLayout.tvShe.isSelected()) {
                list.addAll(mShe);
            }
            if (mBinding.codeLayout.tvMa.isSelected()) {
                list.addAll(mMa);
            }
            if (mBinding.codeLayout.tvYang.isSelected()) {
                list.addAll(mYang);
            }
            if (mBinding.codeLayout.tvHou.isSelected()) {
                list.addAll(mHou);
            }
            if (mBinding.codeLayout.tvJi.isSelected()) {
                list.addAll(mJi);
            }
            if (mBinding.codeLayout.tvGou.isSelected()) {
                list.addAll(mGou);
            }
            if (mBinding.codeLayout.tvZhu.isSelected()) {
                list.addAll(mZhu);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvWei0.isSelected() ||
                mBinding.codeLayout.tvWei1.isSelected() ||
                mBinding.codeLayout.tvWei2.isSelected() ||
                mBinding.codeLayout.tvWei3.isSelected() ||
                mBinding.codeLayout.tvWei4.isSelected() ||
                mBinding.codeLayout.tvWei5.isSelected() ||
                mBinding.codeLayout.tvWei6.isSelected() ||
                mBinding.codeLayout.tvWei7.isSelected() ||
                mBinding.codeLayout.tvWei8.isSelected() ||
                mBinding.codeLayout.tvWei9.isSelected() ||
                mBinding.codeLayout.tvDawei.isSelected() ||
                mBinding.codeLayout.tvXiaowei.isSelected()) {
            if (mBinding.codeLayout.tvWei0.isSelected()) {
                list.addAll(mWei0);
            }
            if (mBinding.codeLayout.tvWei1.isSelected()) {
                list.addAll(mWei1);
            }
            if (mBinding.codeLayout.tvWei2.isSelected()) {
                list.addAll(mWei2);
            }
            if (mBinding.codeLayout.tvWei3.isSelected()) {
                list.addAll(mWei3);
            }
            if (mBinding.codeLayout.tvWei4.isSelected()) {
                list.addAll(mWei4);
            }
            if (mBinding.codeLayout.tvWei5.isSelected()) {
                list.addAll(mWei5);
            }
            if (mBinding.codeLayout.tvWei6.isSelected()) {
                list.addAll(mWei6);
            }
            if (mBinding.codeLayout.tvWei7.isSelected()) {
                list.addAll(mWei7);
            }
            if (mBinding.codeLayout.tvWei8.isSelected()) {
                list.addAll(mWei8);
            }
            if (mBinding.codeLayout.tvWei9.isSelected()) {
                list.addAll(mWei9);
            }
            if (mBinding.codeLayout.tvDawei.isSelected()) {
                list.addAll(mDawei);
            }
            if (mBinding.codeLayout.tvXiaowei.isSelected()) {
                list.addAll(mXiaowei);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvTou0.isSelected() ||
                mBinding.codeLayout.tvTou1.isSelected() ||
                mBinding.codeLayout.tvTou2.isSelected() ||
                mBinding.codeLayout.tvTou3.isSelected() ||
                mBinding.codeLayout.tvTou4.isSelected()) {
            if (mBinding.codeLayout.tvTou0.isSelected()) {
                list.addAll(mTou0);
            }
            if (mBinding.codeLayout.tvTou1.isSelected()) {
                list.addAll(mTou1);
            }
            if (mBinding.codeLayout.tvTou2.isSelected()) {
                list.addAll(mTou2);
            }
            if (mBinding.codeLayout.tvTou3.isSelected()) {
                list.addAll(mTou3);
            }
            if (mBinding.codeLayout.tvTou4.isSelected()) {
                list.addAll(mTou4);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvMen1.isSelected() ||
                mBinding.codeLayout.tvMen2.isSelected() ||
                mBinding.codeLayout.tvMen3.isSelected() ||
                mBinding.codeLayout.tvMen4.isSelected() ||
                mBinding.codeLayout.tvMen5.isSelected()) {
            if (mBinding.codeLayout.tvMen1.isSelected()) {
                list.addAll(mMen1);
            }
            if (mBinding.codeLayout.tvMen2.isSelected()) {
                list.addAll(mMen2);
            }
            if (mBinding.codeLayout.tvMen3.isSelected()) {
                list.addAll(mMen3);
            }
            if (mBinding.codeLayout.tvMen4.isSelected()) {
                list.addAll(mMen4);
            }
            if (mBinding.codeLayout.tvMen5.isSelected()) {
                list.addAll(mMen5);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvDuan1.isSelected() ||
                mBinding.codeLayout.tvDuan2.isSelected() ||
                mBinding.codeLayout.tvDuan3.isSelected() ||
                mBinding.codeLayout.tvDuan4.isSelected() ||
                mBinding.codeLayout.tvDuan5.isSelected() ||
                mBinding.codeLayout.tvDuan6.isSelected() ||
                mBinding.codeLayout.tvDuan7.isSelected()) {
            if (mBinding.codeLayout.tvDuan1.isSelected()) {
                list.addAll(mDuan1);
            }
            if (mBinding.codeLayout.tvDuan2.isSelected()) {
                list.addAll(mDuan2);
            }
            if (mBinding.codeLayout.tvDuan3.isSelected()) {
                list.addAll(mDuan3);
            }
            if (mBinding.codeLayout.tvDuan4.isSelected()) {
                list.addAll(mDuan4);
            }
            if (mBinding.codeLayout.tvDuan5.isSelected()) {
                list.addAll(mDuan5);
            }
            if (mBinding.codeLayout.tvDuan6.isSelected()) {
                list.addAll(mDuan6);
            }
            if (mBinding.codeLayout.tvDuan7.isSelected()) {
                list.addAll(mDuan7);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvHe1.isSelected() ||
                mBinding.codeLayout.tvHe2.isSelected() ||
                mBinding.codeLayout.tvHe3.isSelected() ||
                mBinding.codeLayout.tvHe4.isSelected() ||
                mBinding.codeLayout.tvHe5.isSelected() ||
                mBinding.codeLayout.tvHe6.isSelected() ||
                mBinding.codeLayout.tvHe7.isSelected() ||
                mBinding.codeLayout.tvHe8.isSelected() ||
                mBinding.codeLayout.tvHe9.isSelected() ||
                mBinding.codeLayout.tvHe10.isSelected() ||
                mBinding.codeLayout.tvHe11.isSelected() ||
                mBinding.codeLayout.tvHe12.isSelected() ||
                mBinding.codeLayout.tvHe13.isSelected()) {
            if (mBinding.codeLayout.tvHe1.isSelected()) {
                list.addAll(mHe1);
            }
            if (mBinding.codeLayout.tvHe2.isSelected()) {
                list.addAll(mHe2);
            }
            if (mBinding.codeLayout.tvHe3.isSelected()) {
                list.addAll(mHe3);
            }
            if (mBinding.codeLayout.tvHe4.isSelected()) {
                list.addAll(mHe4);
            }
            if (mBinding.codeLayout.tvHe5.isSelected()) {
                list.addAll(mHe5);
            }
            if (mBinding.codeLayout.tvHe6.isSelected()) {
                list.addAll(mHe6);
            }
            if (mBinding.codeLayout.tvHe7.isSelected()) {
                list.addAll(mHe7);
            }
            if (mBinding.codeLayout.tvHe8.isSelected()) {
                list.addAll(mHe8);
            }
            if (mBinding.codeLayout.tvHe9.isSelected()) {
                list.addAll(mHe9);
            }
            if (mBinding.codeLayout.tvHe10.isSelected()) {
                list.addAll(mHe10);
            }
            if (mBinding.codeLayout.tvHe11.isSelected()) {
                list.addAll(mHe11);
            }
            if (mBinding.codeLayout.tvHe12.isSelected()) {
                list.addAll(mHe12);
            }
            if (mBinding.codeLayout.tvHe13.isSelected()) {
                list.addAll(mHe13);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        list.clear();
        if (mBinding.codeLayout.tvToudan0.isSelected() ||
                mBinding.codeLayout.tvToudan1.isSelected() ||
                mBinding.codeLayout.tvToudan2.isSelected() ||
                mBinding.codeLayout.tvToudan3.isSelected() ||
                mBinding.codeLayout.tvToudan4.isSelected() ||
                mBinding.codeLayout.tvToushuang0.isSelected() ||
                mBinding.codeLayout.tvToushuang1.isSelected() ||
                mBinding.codeLayout.tvToushuang2.isSelected() ||
                mBinding.codeLayout.tvToushuang3.isSelected() ||
                mBinding.codeLayout.tvToushuang4.isSelected()) {
            if (mBinding.codeLayout.tvToudan0.isSelected()) {
                list.addAll(mToudan0);
            }
            if (mBinding.codeLayout.tvToudan1.isSelected()) {
                list.addAll(mToudan1);
            }
            if (mBinding.codeLayout.tvToudan2.isSelected()) {
                list.addAll(mToudan2);
            }
            if (mBinding.codeLayout.tvToudan3.isSelected()) {
                list.addAll(mToudan3);
            }
            if (mBinding.codeLayout.tvToudan4.isSelected()) {
                list.addAll(mToudan4);
            }
            if (mBinding.codeLayout.tvToushuang0.isSelected()) {
                list.addAll(mToushuang0);
            }
            if (mBinding.codeLayout.tvToushuang1.isSelected()) {
                list.addAll(mToushuang1);
            }
            if (mBinding.codeLayout.tvToushuang2.isSelected()) {
                list.addAll(mToushuang2);
            }
            if (mBinding.codeLayout.tvToushuang3.isSelected()) {
                list.addAll(mToushuang3);
            }
            if (mBinding.codeLayout.tvToushuang4.isSelected()) {
                list.addAll(mToushuang4);
            }
            Iterator<Integer> iterator = mAll.iterator();
            while (iterator.hasNext()) {
                Integer next = iterator.next();
                if (!list.contains(next))
                    iterator.remove();
            }
        }
        if (!(mBinding.codeLayout.tvDan.isSelected() ||
                mBinding.codeLayout.tvShuang.isSelected() ||

                mBinding.codeLayout.tvDa.isSelected() ||
                mBinding.codeLayout.tvXiao.isSelected() ||

                mBinding.codeLayout.tvDadan.isSelected() ||
                mBinding.codeLayout.tvDashuang.isSelected() ||
                mBinding.codeLayout.tvXiaodan.isSelected() ||
                mBinding.codeLayout.tvXiaoshuang.isSelected() ||

                mBinding.codeLayout.tvHedan.isSelected() ||
                mBinding.codeLayout.tvHeshuang.isSelected() ||
                mBinding.codeLayout.tvHeda.isSelected() ||
                mBinding.codeLayout.tvHexiao.isSelected() ||

                mBinding.codeLayout.tvJin.isSelected() ||
                mBinding.codeLayout.tvMu.isSelected() ||
                mBinding.codeLayout.tvShui.isSelected() ||
                mBinding.codeLayout.tvHuo.isSelected() ||
                mBinding.codeLayout.tvTu.isSelected() ||

                mBinding.codeLayout.tvHongbo.isSelected() ||
                mBinding.codeLayout.tvLanbo.isSelected() ||
                mBinding.codeLayout.tvLvbo.isSelected() ||

                mBinding.codeLayout.tvJiaqian.isSelected() ||
                mBinding.codeLayout.tvYeshou.isSelected() ||

                mBinding.codeLayout.tvHongdan.isSelected() ||
                mBinding.codeLayout.tvHongshuang.isSelected() ||
                mBinding.codeLayout.tvLandan.isSelected() ||
                mBinding.codeLayout.tvLanshuang.isSelected() ||
                mBinding.codeLayout.tvLvdan.isSelected() ||
                mBinding.codeLayout.tvLvshuang.isSelected() ||

                mBinding.codeLayout.tvShu.isSelected() ||
                mBinding.codeLayout.tvNiu.isSelected() ||
                mBinding.codeLayout.tvHu.isSelected() ||
                mBinding.codeLayout.tvTuzi.isSelected() ||
                mBinding.codeLayout.tvLong.isSelected() ||
                mBinding.codeLayout.tvShe.isSelected() ||
                mBinding.codeLayout.tvMa.isSelected() ||
                mBinding.codeLayout.tvYang.isSelected() ||
                mBinding.codeLayout.tvHou.isSelected() ||
                mBinding.codeLayout.tvJi.isSelected() ||
                mBinding.codeLayout.tvGou.isSelected() ||
                mBinding.codeLayout.tvZhu.isSelected() ||

                mBinding.codeLayout.tvWei0.isSelected() ||
                mBinding.codeLayout.tvWei1.isSelected() ||
                mBinding.codeLayout.tvWei2.isSelected() ||
                mBinding.codeLayout.tvWei3.isSelected() ||
                mBinding.codeLayout.tvWei4.isSelected() ||
                mBinding.codeLayout.tvWei5.isSelected() ||
                mBinding.codeLayout.tvWei6.isSelected() ||
                mBinding.codeLayout.tvWei7.isSelected() ||
                mBinding.codeLayout.tvWei8.isSelected() ||
                mBinding.codeLayout.tvWei9.isSelected() ||
                mBinding.codeLayout.tvDawei.isSelected() ||
                mBinding.codeLayout.tvXiaowei.isSelected() ||

                mBinding.codeLayout.tvTou0.isSelected() ||
                mBinding.codeLayout.tvTou1.isSelected() ||
                mBinding.codeLayout.tvTou2.isSelected() ||
                mBinding.codeLayout.tvTou3.isSelected() ||
                mBinding.codeLayout.tvTou4.isSelected() ||

                mBinding.codeLayout.tvMen1.isSelected() ||
                mBinding.codeLayout.tvMen2.isSelected() ||
                mBinding.codeLayout.tvMen3.isSelected() ||
                mBinding.codeLayout.tvMen4.isSelected() ||
                mBinding.codeLayout.tvMen5.isSelected() ||

                mBinding.codeLayout.tvDuan1.isSelected() ||
                mBinding.codeLayout.tvDuan2.isSelected() ||
                mBinding.codeLayout.tvDuan3.isSelected() ||
                mBinding.codeLayout.tvDuan4.isSelected() ||
                mBinding.codeLayout.tvDuan5.isSelected() ||
                mBinding.codeLayout.tvDuan6.isSelected() ||
                mBinding.codeLayout.tvDuan7.isSelected() ||

                mBinding.codeLayout.tvHe1.isSelected() ||
                mBinding.codeLayout.tvHe2.isSelected() ||
                mBinding.codeLayout.tvHe3.isSelected() ||
                mBinding.codeLayout.tvHe4.isSelected() ||
                mBinding.codeLayout.tvHe5.isSelected() ||
                mBinding.codeLayout.tvHe6.isSelected() ||
                mBinding.codeLayout.tvHe7.isSelected() ||
                mBinding.codeLayout.tvHe8.isSelected() ||
                mBinding.codeLayout.tvHe9.isSelected() ||
                mBinding.codeLayout.tvHe10.isSelected() ||
                mBinding.codeLayout.tvHe11.isSelected() ||
                mBinding.codeLayout.tvHe12.isSelected() ||
                mBinding.codeLayout.tvHe13.isSelected() ||

                mBinding.codeLayout.tvToudan0.isSelected() ||
                mBinding.codeLayout.tvToudan1.isSelected() ||
                mBinding.codeLayout.tvToudan2.isSelected() ||
                mBinding.codeLayout.tvToudan3.isSelected() ||
                mBinding.codeLayout.tvToudan4.isSelected() ||
                mBinding.codeLayout.tvToushuang0.isSelected() ||
                mBinding.codeLayout.tvToushuang1.isSelected() ||
                mBinding.codeLayout.tvToushuang2.isSelected() ||
                mBinding.codeLayout.tvToushuang3.isSelected() ||
                mBinding.codeLayout.tvToushuang4.isSelected())
        ) {
            mAll.clear();
        }
        mBinding.ccContainer.addChooseCodeViewsNumber(mAll);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_choose_code_helper;
    }

    @Override
    public LiuHeGalleryViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LiuHeGalleryViewModel.class);
    }

    private void handleTMZS(List<TMZSModel2> tmzsModel) {
        List<TMZSModel2.FiveBean> fiveModel = tmzsModel.get(0).getFive();
        TMZSModel2.FiveBean five = fiveModel.get(0);
        String gold = five.getGold();
        String[] golds = gold.split(",");
        for (String gold1 : golds) {
            mJin.add(Integer.parseInt(gold1));
        }
        String timber = fiveModel.get(1).getTimber();
        String[] timbers = timber.split(",");
        for (String timber1 : timbers) {
            mMu.add(Integer.parseInt(timber1));
        }
        String water = fiveModel.get(2).getWater();
        String[] waters = water.split(",");
        for (String water1 : waters) {
            mShui.add(Integer.parseInt(water1));
        }
        String fires = fiveModel.get(3).getFires();
        String[] fireses = fires.split(",");
        for (String fires1 : fireses) {
            mHuo.add(Integer.parseInt(fires1));
        }
        String soil = fiveModel.get(4).getSoil();
        String[] soils = soil.split(",");
        for (String soil1 : soils) {
            mTu.add(Integer.parseInt(soil1));
        }
        TMZSModel2.AnimalBean animal = tmzsModel.get(1).getAnimal().get(10);
        String rat = animal.getRat();
        String[] rats = rat.split(",");
        for (String rat1 : rats) {
            mShu.add(Integer.parseInt(rat1));
            mYeshou.addAll(mShu);
        }
        String cattle = tmzsModel.get(1).getAnimal().get(9).getCattle();
        String[] cattles = cattle.split(",");
        for (String cattle1 : cattles) {
            mNiu.add(Integer.parseInt(cattle1));
            mJiaqian.addAll(mNiu);
        }
        String tiger = tmzsModel.get(1).getAnimal().get(8).getTiger();
        String[] tigers = tiger.split(",");
        for (String tiger1 : tigers) {
            mHu.add(Integer.parseInt(tiger1));
            mYeshou.addAll(mHu);
        }
        String rabbit = tmzsModel.get(1).getAnimal().get(7).getRabbit();
        String[] rabbits = rabbit.split(",");
        for (String rabbit1 : rabbits) {
            mTuzi.add(Integer.parseInt(rabbit1));
            mYeshou.addAll(mTuzi);
        }
        String loong = tmzsModel.get(1).getAnimal().get(6).getLoong();
        String[] loongs = loong.split(",");
        for (String loong1 : loongs) {
            mLong.add(Integer.parseInt(loong1));
            mYeshou.addAll(mLong);
        }
        String snake = tmzsModel.get(1).getAnimal().get(5).getSnake();
        String[] snakes = snake.split(",");
        for (String snake1 : snakes) {
            mShe.add(Integer.parseInt(snake1));
            mYeshou.addAll(mShe);
        }
        String horse = tmzsModel.get(1).getAnimal().get(4).getHorse();
        String[] horses = horse.split(",");
        for (String horse1 : horses) {
            mMa.add(Integer.parseInt(horse1));
            mJiaqian.addAll(mMa);
        }
        String sheep = tmzsModel.get(1).getAnimal().get(3).getSheep();
        String[] sheeps = sheep.split(",");
        for (String sheep1 : sheeps) {
            mYang.add(Integer.parseInt(sheep1));
            mJiaqian.addAll(mYang);
        }
        String monkey = tmzsModel.get(1).getAnimal().get(2).getMonkey();
        String[] monkeys = monkey.split(",");
        for (String monkey1 : monkeys) {
            mHou.add(Integer.parseInt(monkey1));
            mYeshou.addAll(mHou);
        }
        String chicken = tmzsModel.get(1).getAnimal().get(1).getChicken();
        String[] chickens = chicken.split(",");
        for (String chicken1 : chickens) {
            mJi.add(Integer.parseInt(chicken1));
            mJiaqian.addAll(mJi);
        }
        String dog = tmzsModel.get(1).getAnimal().get(0).getDog();
        String[] dogs = dog.split(",");
        for (String dog1 : dogs) {
            mGou.add(Integer.parseInt(dog1));
            mJiaqian.addAll(mGou);
        }
        String pig = tmzsModel.get(1).getAnimal().get(11).getPig();
        String[] pigs = pig.split(",");
        for (String pig1 : pigs) {
            mZhu.add(Integer.parseInt(pig1));
            mJiaqian.addAll(mZhu);
        }
        initData();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        
        //生成邀请二维码
        mBinding.ivClose.setOnClickListener(v -> finish());
        mViewModel.getHelperTMZS();
        initClick();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.mTMZSLiveData.observe(this, this::handleTMZS);
    }
}