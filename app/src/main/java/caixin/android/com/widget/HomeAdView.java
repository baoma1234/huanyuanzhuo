package caixin.android.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.caixin.huanyu.R;

public class HomeAdView extends RelativeLayout {
    private int lastX;
    private int lastY;
    private ImageView ivAd;
    private ImageView ivClose;

    public HomeAdView(Context context) {
        super(context);
        initView(context);
    }

    public HomeAdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public HomeAdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_home_ad, this, true);
        ivClose = findViewById(R.id.iv_guanggao_close);
        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
            }
        });
        ivAd = findViewById(R.id.iv_guanggao);
    }


    private MyClick onClickListener;

    public void setADListener(MyClick onClickListener) {
        this.onClickListener = onClickListener;
    }

    private static final String TAG = "HomeAdView";
    private int mLastX;
    private int mLastY;
    private long oldTime;

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;//计算x坐标上的差值
                int deltaY = y - mLastY;//计算y坐标上的差值
                float tranX = getTranslationX() + deltaX;//要平移的x值
                float tranY = getTranslationY() + deltaY;//要平移的y值
                setTranslationX(tranX);//设置值
                setTranslationY(tranY);
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - oldTime < 300) {
                    if (onClickListener != null)
                        onClickListener.onClick();
                }
                break;
            default:
                break;
        }
        mLastX = x;//记录上次的坐标
        mLastY = y;
        return true;
    }

    public interface MyClick {
        public void onClick();
    }
}
