package caixin.android.com.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by cxf on 2018/6/7.
 */

@SuppressLint("AppCompatCustomView")
public class WidthSquareImageView extends ImageView {
    public WidthSquareImageView(Context context) {
        super(context);
    }

    public WidthSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WidthSquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
