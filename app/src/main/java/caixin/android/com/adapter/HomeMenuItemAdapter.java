package caixin.android.com.adapter;


import android.animation.AnimatorSet;
import android.content.Context;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import caixin.android.com.entity.CustomerModel;
import caixin.android.com.entity.HomeMenuItemResponse;
import caixin.android.com.utils.ImgLoader;

public class HomeMenuItemAdapter extends BaseQuickAdapter<HomeMenuItemResponse, BaseViewHolder> {
    AnimationSet animationSet4;
    AnimationSet animationSet1;
    AnimationSet animationSet2;
    AnimationSet animationSet3;
    AnimationSet[] animatorSets;

    public HomeMenuItemAdapter(@Nullable List<HomeMenuItemResponse> data, Context context) {
        super(R.layout.home_menu_item, data);
        animationSet1 = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.anim1);
        animationSet2 = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.anim2);
        animationSet3 = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.anim3);
        animationSet4 = (AnimationSet) AnimationUtils.loadAnimation(context, R.anim.anim4);
        animatorSets = new AnimationSet[]{animationSet1, animationSet2, animationSet3, animationSet4};
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeMenuItemResponse item) {
        helper.setText(R.id.tv_title, item.getTitle());
        if (helper.getAdapterPosition()<=4){
            ImgLoader.normalGlideSource(helper.getView(R.id.iv_content), item.getSource(), R.mipmap.web_default);
        }else {
            ImgLoader.GlideLoadCircle(helper.getView(R.id.iv_content), item.getImg(), R.mipmap.web_default);
        }
        helper.itemView.startAnimation(animatorSets[helper.getAdapterPosition() % 4]);
    }
}