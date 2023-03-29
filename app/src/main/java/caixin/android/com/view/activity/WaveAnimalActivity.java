package caixin.android.com.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityWaveAnimalBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.utils.LotteryUtil;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.ToastUtils;

public class WaveAnimalActivity extends BaseActivity<ActivityWaveAnimalBinding, BaseViewModel> {

    private List<Integer> list = new ArrayList<>();
    private List<Integer> waveList = new ArrayList<>();
    private List<ImageView> viewAnimalList = new ArrayList<>();
    private List<Object> waveAnimalList = new ArrayList<>();
    private List<Object> waveAnimalSelectedList = new ArrayList<>();
    private List<TextView> animalList = new ArrayList<>();


    private void initView() {
        mBinding.ivClose.setOnClickListener(v -> finish());
        mBinding.ivStar.setOnClickListener(v -> {
            if (waveList.size() != 3) {
                saveData(waveList.size());
            } else {
                ToastUtils.show("本期抽奖次数已用完");
            }
        });
    }

    private void initData() {
        Random rand = new Random();
        for (int i = 0; i < 12; i++) {
            int k = rand.nextInt(12);
            if (!list.contains(k)) {
                list.add(k);
            }
            if (list.size() == 3) {
                break;
            }
        }
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal1);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal2);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal3);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal4);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal5);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal6);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal7);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal8);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal9);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal10);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal11);
        viewAnimalList.add(mBinding.layoutWaveAnimal.ivWaveAnimal12);

        waveAnimalList.add(R.mipmap.ic_wave_animal_1);
        waveAnimalList.add(R.mipmap.ic_wave_animal_2);
        waveAnimalList.add(R.mipmap.ic_wave_animal_3);
        waveAnimalList.add(R.mipmap.ic_wave_animal_4);
        waveAnimalList.add(R.mipmap.ic_wave_animal_5);
        waveAnimalList.add(R.mipmap.ic_wave_animal_6);
        waveAnimalList.add(R.mipmap.ic_wave_animal_7);
        waveAnimalList.add(R.mipmap.ic_wave_animal_8);
        waveAnimalList.add(R.mipmap.ic_wave_animal_9);
        waveAnimalList.add(R.mipmap.ic_wave_animal_10);
        waveAnimalList.add(R.mipmap.ic_wave_animal_11);
        waveAnimalList.add(R.mipmap.ic_wave_animal_12);

        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_1);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_2);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_3);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_4);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_5);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_6);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_7);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_8);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_9);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_10);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_11);
        waveAnimalSelectedList.add(R.mipmap.ic_wave_animal_selected_12);

        animalList.add(mBinding.tvAnimal1);
        animalList.add(mBinding.tvAnimal2);
        animalList.add(mBinding.tvAnimal3);

        if (MMKVUtil.getWaveColorAnimalList() != null) {
            waveList.addAll(MMKVUtil.getWaveColorAnimalList());
            try {
                mBinding.tvAnimal1.setText(LotteryUtil.getSx(String.valueOf(waveList.get(0) + 1)));
                mBinding.tvAnimal2.setText(LotteryUtil.getSx(String.valueOf(waveList.get(1) + 1)));
                mBinding.tvAnimal3.setText(LotteryUtil.getSx(String.valueOf(waveList.get(2) + 1)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        setText(3 - waveList.size());
    }


    private void saveData(int size) {
        mBinding.ivStar.setVisibility(View.INVISIBLE);
        setText(3 - (size + 1));
        waveList.add(list.get(size));
        MMKVUtil.setWaveColorAnimalList(waveList);
        for (int i = 0; i < 3; i++) {
            int animal = i;
            new Handler().postDelayed(() -> {
                for (int k = 0; k < 12; k++) {
                    int animal_l = k;
                    new Handler().postDelayed(() -> {
                        viewAnimalList.get(animal_l).setImageResource((Integer) waveAnimalSelectedList.get(animal_l));
                        if (animal < 2) {
                            new Handler().postDelayed(() -> viewAnimalList.get(animal_l).setImageResource((Integer) waveAnimalList.get(animal_l)), 100);
                        }
                        if (animal == 2 && animal_l != waveList.get(size)) {
                            new Handler().postDelayed(() -> viewAnimalList.get(animal_l).setImageResource((Integer) waveAnimalList.get(animal_l)), 100);
                        }
                    }, 100 * k);
                    if (animal == 2 && animal_l == waveList.get(size)) {
                        break;
                    }
                }
            }, 1200 * i);
        }
        new Handler().postDelayed(() -> {
            mBinding.ivStar.setVisibility(View.VISIBLE);
            animalList.get(size).setText(LotteryUtil.getSx(String.valueOf(waveList.get(size) + 1)));
        }, 1200 * 3);
    }

    private void setText(int size) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        String s1 = "还有";
        ssb.append(s1);
        String times = String.valueOf(size);
        ssb.append(times);
        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.main_yellow)), s1.length(), times.length() + s1.length(), 0);
        ssb.append("次");
        mBinding.tvTimes.setText(ssb);
    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_wave_animal;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initData();
        initView();
    }

    @Override
    public void initViewObservable() {

    }
}