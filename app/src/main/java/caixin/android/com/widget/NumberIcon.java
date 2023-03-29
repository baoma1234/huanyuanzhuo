package caixin.android.com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.caixin.huanyu.R;

import caixin.android.com.entity.HomeKJModel;
import caixin.android.com.entity.KJResultModel;

public class NumberIcon extends LinearLayout {

    private TextView tvNumber;
    private ImageView ivIcon;
    private TextView tvName;
    private boolean mNumberVisible;

    public NumberIcon(@NonNull Context context) {
        this(context, null);
    }

    public NumberIcon(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberIcon(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberIcon);
        int number = typedArray.getInteger(R.styleable.NumberIcon_number, 1);
        int textsize = typedArray.getInteger(R.styleable.NumberIcon_textsize, 0);
        mNumberVisible = typedArray.getBoolean(R.styleable.NumberIcon_number_visible, false);
        View view = LayoutInflater.from(context).inflate(R.layout.number_icon_layout, this, false);
        tvNumber = view.findViewById(R.id.tv_number);
        tvName = view.findViewById(R.id.tv_name);
        tvName.setVisibility(mNumberVisible ? VISIBLE : GONE);
        ivIcon = view.findViewById(R.id.iv_icon);
        if (textsize != 0)
            tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_SP, textsize);
        setNumber(number);
        typedArray.recycle();
        addView(view);
    }

    public void setNumberVisible(boolean numberVisible) {
        mNumberVisible = numberVisible;
        tvName.setVisibility(GONE);
    }

    public void setNumberTextSize(float size) {
        tvNumber.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    public void setStringNumber(String number) {
        int no = 0;
        try {
            no = Integer.parseInt(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setNumber(no);
    }

    public void setKJResult(KJResultModel.NewDataBean haoBean) {
        tvNumber.setText(String.valueOf(haoBean.getNum()));
        if ("red".equals(haoBean.getBs())) {
            ivIcon.setImageResource(R.mipmap.red_icon);
        } else if ("green".equals(haoBean.getBs())) {
            ivIcon.setImageResource(R.mipmap.green_icon);
        } else {
            ivIcon.setImageResource(R.mipmap.blue_icon);
        }
        tvName.setText(haoBean.getSx());
    }

    public void setKJNumber(HomeKJModel.HaoBean haoBean) {
        tvNumber.setText(haoBean.getNumber());
        if ("red".equals(haoBean.getColor())) {
            ivIcon.setImageResource(R.mipmap.red_icon);
        } else if ("green".equals(haoBean.getColor())) {
            ivIcon.setImageResource(R.mipmap.green_icon);
        } else {
            ivIcon.setImageResource(R.mipmap.blue_icon);
        }
        tvName.setText(haoBean.getSx());
    }

    public void setNumber(int number) {
        switch (number) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 19:
            case 23:
            case 24:
            case 29:
            case 30:
            case 34:
            case 35:
            case 40:
            case 45:
            case 46:
                if (number < 10) {
                    tvNumber.setText("0" + number);
                } else {
                    tvNumber.setText(String.valueOf(number));
                }
                ivIcon.setImageResource(R.mipmap.red_icon);
                break;
            case 3:
            case 4:
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 25:
            case 26:
            case 31:
            case 36:
            case 37:
            case 41:
            case 42:
            case 47:
            case 48:
                if (number < 10) {
                    tvNumber.setText("0" + number);
                } else {
                    tvNumber.setText(String.valueOf(number));
                }
                ivIcon.setImageResource(R.mipmap.blue_icon);
                break;
            case 5:
            case 6:
            case 11:
            case 16:
            case 17:
            case 21:
            case 22:
            case 27:
            case 28:
            case 32:
            case 33:
            case 38:
            case 39:
            case 43:
            case 44:
            case 49:
                if (number < 10) {
                    tvNumber.setText("0" + number);
                } else {
                    tvNumber.setText(String.valueOf(number));
                }
                ivIcon.setImageResource(R.mipmap.green_icon);
                break;
        }
        if (!mNumberVisible) {
            tvName.setVisibility(GONE);
            return;
        }
        switch (number) {
            case 12:
            case 24:
            case 36:
            case 48:
                tvName.setText(R.string.rat);
                break;
            case 11:
            case 23:
            case 35:
            case 47:
                tvName.setText(R.string.ox);
                break;
            case 10:
            case 22:
            case 34:
            case 46:
                tvName.setText(R.string.tiger);
                break;
            case 9:
            case 21:
            case 33:
            case 45:
                tvName.setText(R.string.rabbit);
                break;
            case 8:
            case 20:
            case 32:
            case 44:
                tvName.setText(R.string.loong);
                break;
            case 7:
            case 19:
            case 31:
            case 43:
                tvName.setText(R.string.snake);
                break;
            case 6:
            case 18:
            case 30:
            case 42:
                tvName.setText(R.string.horse);
                break;
            case 5:
            case 17:
            case 29:
            case 41:
                tvName.setText(R.string.sheep);
                break;
            case 4:
            case 16:
            case 28:
            case 40:
                tvName.setText(R.string.monkey);
                break;
            case 3:
            case 15:
            case 27:
            case 39:
                tvName.setText(R.string.chicken);
                break;
            case 2:
            case 14:
            case 26:
            case 38:
                tvName.setText(R.string.dog);
                break;
            case 1:
            case 13:
            case 25:
            case 37:
            case 49:
                tvName.setText(R.string.pig);
                break;

        }
    }
}
