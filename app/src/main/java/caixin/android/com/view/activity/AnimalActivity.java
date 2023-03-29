package caixin.android.com.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityAnimalBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import caixin.android.com.adapter.AnimalAdapter;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.utils.MMKVUtil;

public class AnimalActivity extends BaseActivity<ActivityAnimalBinding, BaseViewModel> {

    private List<Integer> animal;
    private List<Object> animalList;
    private AnimalAdapter animalAdapter;

    private void initView() {
        mBinding.ivClose.setOnClickListener(v -> finish());
        RecyclerView rvAnimal = findViewById(R.id.rv_animal);
        rvAnimal.setLayoutManager(new GridLayoutManager(this, 3));
        animalAdapter = new AnimalAdapter();
        rvAnimal.setAdapter(animalAdapter);
    }

    private void initData() {
        animalList = new ArrayList<>();
        animalList.add(R.mipmap.ic_animal_1);
        animalList.add(R.mipmap.ic_animal_2);
        animalList.add(R.mipmap.ic_animal_3);
        animalList.add(R.mipmap.ic_animal_4);
        animalList.add(R.mipmap.ic_animal_5);
        animalList.add(R.mipmap.ic_animal_6);
        animalList.add(R.mipmap.ic_animal_7);
        animalList.add(R.mipmap.ic_animal_8);
        animalList.add(R.mipmap.ic_animal_9);
        animalList.add(R.mipmap.ic_animal_10);
        animalList.add(R.mipmap.ic_animal_11);
        animalList.add(R.mipmap.ic_animal_12);

        if (MMKVUtil.getAnimalList() == null || MMKVUtil.getAnimalList().size() == 0) {
            List<Integer> animalNum = new ArrayList<>();
            Random rand = new Random();
            for (int i = 0; i < 100; i++) {
                int k = rand.nextInt(12);
                if (!animalNum.contains(k)) {
                    animalNum.add(k);
                }
                if (animalNum.size() == 12) {
                    break;
                }
            }
            List<Object> bgList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                bgList.add(R.mipmap.ic_animal_bg);
            }
            animal = new ArrayList<>();
            animalAdapter.setNewData(bgList);
            animalAdapter.setOnItemClickListener((adapter, view, position) -> {
                bgList.remove(position);
                bgList.add(position, animalList.get(animalNum.get(position)));
                animalAdapter.notifyDataSetChanged();
                animal.add(animalNum.get(position));
                if (animal.size() == 3) {
                    showDialog();
                    new Handler().postDelayed(() -> {
                        MMKVUtil.setAnimalList(animal);
                        setData(animal);
                    }, 1000);
                }
            });
        } else {
            setData(MMKVUtil.getAnimalList());
        }
    }

    private void setData(List<Integer> list) {
        dismissDialog();
        List<Object> animal = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            animal.add(animalList.get(list.get(i)));
        }
        animalAdapter.setNewData(animal);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_animal;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        initData();
    }

    @Override
    public void initViewObservable() {

    }
}