package caixin.android.com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;


/**
 * max limit-able RecyclerView
 */
public class MaxLimitRecyclerView extends RecyclerView {
    private int mMaxWidth;
    public MaxLimitRecyclerView(Context context) {
        this(context, null);
    }
    public MaxLimitRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MaxLimitRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inti(attrs);
    }
    private void inti(AttributeSet attrs) {
        if (getContext() != null && attrs != null) {
            TypedArray typedArray = null;
            try {
                typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MaxLimitRecyclerView);
                if (typedArray.hasValue(R.styleable.MaxLimitRecyclerView_limit_maxWidth)) {
                    mMaxWidth = typedArray.getDimensionPixelOffset(R.styleable.MaxLimitRecyclerView_limit_maxWidth, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (typedArray != null) {
                    typedArray.recycle();
                }
            }
        }
    }
    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        Log.e(TAG, "onMeasure: " +mMaxWidth);
        if (mMaxWidth > 0) {
            widthSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    private static final String TAG = "MaxLimitRecyclerView";
}