package caixin.android.com.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by cxf on 2018/6/7.
 */

@SuppressLint("AppCompatCustomView")
public class HighSquareImageView extends ImageView {
    public HighSquareImageView(Context context) {
        super(context);
    }

    public HighSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HighSquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
