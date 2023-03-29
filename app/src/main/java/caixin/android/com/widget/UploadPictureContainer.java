package caixin.android.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.caixin.huanyu.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import caixin.android.com.utils.DpUtil;

public class UploadPictureContainer extends ViewGroup {
    private static int MAX_COUNT = 9;
    private static final int DEFAULT_GOOD_IMG_SIZE = 72;
    private static final int DEFAULT_GOOD_IMG_MARGIN = 10;
    private int childWidth = DpUtil.dp2px(DEFAULT_GOOD_IMG_SIZE);
    private int childHeight = DpUtil.dp2px(DEFAULT_GOOD_IMG_SIZE);
    private OnUploadPictureListener mListener;
    private List<String> mImages = new ArrayList<>();
    private List<UploadImage> mUploadImages = new ArrayList<>();
    private int mWidth;


    public interface OnUploadPictureListener {

        void onDeleteClick(UploadImage uploadImage);

        void onAddClick();

        void onPicClick();
    }

    public void setOnUploadPictureListener(OnUploadPictureListener listener) {
        mListener = listener;
    }

    public UploadPictureContainer(Context context) {
        this(context, null);
    }

    public UploadPictureContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadPictureContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        ImageView header = new ImageView(context);
        header.setImageResource(R.mipmap.icon_add);
        header.setScaleType(ImageView.ScaleType.CENTER_CROP);
        header.setTag(R.id.tag_view_pos, getChildCount());//记录当前位置
        MarginLayoutParams params = initParam(header);
        header.setLayoutParams(params);
        header.setOnClickListener(view -> {
            if (mListener != null) {
                if (getChildCount() - 1 >= MAX_COUNT) {
                    Toast.makeText(context, "超出图片限制", Toast.LENGTH_SHORT).show();
                    return;
                }
                mListener.onAddClick();
            }
        });
        addView(header);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    public void setMaxCount(int maxCount) {
        MAX_COUNT = maxCount;
    }

    public void addImages(Context context, List<String> uploadImages) {
        addImages(context, uploadImages, false);
    }

    public void addImages(Context context, List<String> uploadImages, boolean isServer) {
        if (uploadImages == null || uploadImages.isEmpty()) return;
        for (int i = 0; i < uploadImages.size(); i++) {
            addImage(context, uploadImages.get(i), isServer);
        }
    }

    public void addImage(Context context, String imageUrl) {
        addImage(context, imageUrl, false);
    }

    public void addImage(Context context, String imageUrl, boolean isServer) {
        UploadImage uploadImage = new UploadImage();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pic_container_layout, this, false);
        final RelativeLayout container = new RelativeLayout(getContext());
        if (isServer) {
            new Thread(() -> {
                FutureTarget<File> target = Glide.with(context)
                        .downloadOnly()
                        .load(imageUrl)
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                try {
                    uploadImage.setServerImg(target.get().getAbsolutePath());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            uploadImage.setLocalImg(imageUrl);
        }
        uploadImage.setId(getChildCount());
        container.setTag(R.id.tag_img_url, imageUrl);
        container.setTag(R.id.tag_view_pos, getChildCount());//记录当前位置
        mUploadImages.add(uploadImage);
        RequestOptions options = new RequestOptions().centerCrop().error(R.mipmap.web_default);
        Glide.with(getContext())
                .load(imageUrl)
                .apply(options)
                .into((ImageView) view.findViewById(R.id.iv_image));
        view.findViewById(R.id.iv_image).setOnClickListener(view1 -> {
            if (mListener != null)
                mListener.onPicClick();
        });
        view.findViewById(R.id.iv_delete).setOnClickListener(view12 -> {
            if (mListener != null) {
                mListener.onDeleteClick(uploadImage);
            }
        });
        container.addView(view);
        MarginLayoutParams params = initParam(view);
        container.setLayoutParams(params);
        addView(container);
    }

    public void removeImage(UploadImage uploadImage) {
        if (uploadImage.getId() > getChildCount() || uploadImage.getId() < 0) return;
        removeView(getChildAt(uploadImage.getId()));
        mUploadImages.remove(uploadImage);
//        mImages.remove(pos - 1);
        //移除后重新设置tag
        for (int i = 0; i < mUploadImages.size(); i++) {
            mUploadImages.get(i).setId(i + 1);
        }
    }

    @NonNull
    private MarginLayoutParams initParam(View view) {
        view.measure(0, 0);
        MarginLayoutParams params = new MarginLayoutParams(view.getMeasuredWidth(),
                view.getMeasuredHeight());
        params.leftMargin = DpUtil.dp2px(DEFAULT_GOOD_IMG_MARGIN);
        params.rightMargin = DpUtil.dp2px(DEFAULT_GOOD_IMG_MARGIN);
        params.topMargin = DpUtil.dp2px(DEFAULT_GOOD_IMG_MARGIN);
        params.bottomMargin = DpUtil.dp2px(DEFAULT_GOOD_IMG_MARGIN);
        return params;
    }

    public List<String> getImages() {
        return mImages;
    }

    public List<UploadImage> getUploadImages() {
        return mUploadImages;
    }

    public int getCurrentMaxLength() {
        return MAX_COUNT - mImages.size();
    }

    /**
     * 计算控件的大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingTop() + getPaddingBottom();
        int maxHeight = 0;
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            if (maxWidth + childWidth + params.leftMargin + params.rightMargin > mWidth) {
//                if (i == childCount - 1) {
                maxHeight = Math.max(childHeight + params.bottomMargin + params
                        .topMargin, maxHeight);
                height += maxHeight;
                maxWidth = childWidth + params.leftMargin + params.rightMargin;
//                }
            } else {
                maxWidth += childWidth + params.leftMargin + params.rightMargin;
                maxHeight = Math.max(childHeight + params.bottomMargin + params
                        .topMargin, maxHeight);
            }
        }
        height += maxHeight;
        setMeasuredDimension(width, height);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            if (i == 0) {
                child.layout(params.leftMargin, params.topMargin, params.leftMargin + childWidth,
                        params.topMargin + childHeight);
            } else {
                View last = getChildAt(i - 1);
                int right = last.getRight();
                int top = last.getTop();
                int bottom = last.getBottom();
                if (right + childWidth + params.rightMargin > mWidth) {
                    //换行
                    child.layout(params.leftMargin, params.topMargin + bottom, params.leftMargin
                            + childWidth, params.topMargin + childHeight + bottom);
                } else {
                    child.layout(params.leftMargin + right, top, params.leftMargin + right +
                            childWidth, top + childHeight);
                }
            }
        }
    }
}
