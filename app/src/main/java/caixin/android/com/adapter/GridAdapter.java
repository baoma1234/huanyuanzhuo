package caixin.android.com.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import caixin.android.com.utils.ImgLoader;

public class GridAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<String> imgsOrigin = new ArrayList<>();

    public GridAdapter(int layoutResId) {
        super(layoutResId);
    }

    private boolean scrolling = false;

    public boolean isScrolling() {
        return scrolling;
    }

    public void setScrolling(boolean scrolling) {
        this.scrolling = scrolling;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (!scrolling) {
            ImgLoader.normalGlide(helper.getView(R.id.item_img), item, R.mipmap.web_default);
            helper.addOnClickListener(R.id.item_img);
        }
    }

    public void addData(@NonNull Collection<? extends String> newData, List<String> imgs) {
        addData(newData);
        imgsOrigin.addAll(imgs);
    }

    public void setNewData(@Nullable List<String> data, List<String> imgs) {
        setNewData(data);
        imgsOrigin.clear();
        imgsOrigin.addAll(imgs);
    }
}
