package caixin.android.com.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.caixin.huanyu.R;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.entity.SSCModel;
import caixin.android.com.utils.ScreenDimenUtil;
import caixin.android.com.widget.qmui.QMUIRoundButton;
import caixin.android.com.widget.qmui.QMUIRoundButtonDrawable;
import io.reactivex.annotations.NonNull;

public class CodeContainer extends ViewGroup {
    private static final int DEFAULT_CODE_IMG_SIZE = 32;
    private static final int DEFAULT_CODE_IMG_MARGIN = 8;
    private int childWidth = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_SIZE);
    private int childHeight = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_SIZE);
    private List<String> mImages = new ArrayList<>();
    private int mWidth;

    public CodeContainer(Context context) {
        this(context, null);
    }

    public CodeContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CodeContainer);
        mSingleLine = typedArray.getBoolean(R.styleable.CodeContainer_is_single_line, false);
    }

    public void addCommonRoundButtons(List<SSCModel.CodeBean> buttons) {
        int size = ScreenDimenUtil.getInstance().getScreenWidth() / 10 - ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN) - 6;
        addCommonRoundButtons(buttons, size);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    public void addMinCommonRoundButtons(List<SSCModel.CodeBean> buttons) {
        int size = ScreenDimenUtil.getInstance().getScreenWidth() / 10 - ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN) - 6;
        addCommonRoundButtons(buttons, size);
    }

    public void addCommonRoundButtons(List<SSCModel.CodeBean> buttons, int size) {
        removeAllViews();
        for (int i = 0; i < buttons.size(); i++) {
            final RelativeLayout container = new RelativeLayout(getContext());
            QMUIRoundButton roundButton = new QMUIRoundButton(getContext());
            roundButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            roundButton.setText(buttons.get(i).getNumber());
            roundButton.setPadding(0, 0, 0, 0);
            QMUIRoundButtonDrawable qmuiRoundButtonDrawable = (QMUIRoundButtonDrawable) roundButton.getBackground();
            qmuiRoundButtonDrawable.setBgData(ColorStateList.valueOf(getContext().getResources().getColor(R.color.color_3579F6)));
            roundButton.setTextColor(Color.WHITE);
            qmuiRoundButtonDrawable.setIsRadiusAdjustBounds(true);
            roundButton.setClickable(false);
            RelativeLayout.LayoutParams viewParam = new RelativeLayout.LayoutParams(size, size);
            viewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            roundButton.setLayoutParams(viewParam);
            container.addView(roundButton);
            MarginLayoutParams params = initParam(roundButton, size);
            container.setLayoutParams(params);
            addView(container);
        }
    }

    public void addRoundButtons(List<SSCModel.CodeBean> buttons) {
        int size = ScreenDimenUtil.getInstance().getScreenWidth() / 10 - ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN) - 6;
        addRoundButtons(buttons, size, false);
    }

    public void addMinRoundButtons(List<SSCModel.CodeBean> buttons) {
        addRoundButtons(buttons, ScreenDimenUtil.getInstance().getScreenWidth() / (buttons.size() + 4) - ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN), true);
    }

    public void addRoundButtons(List<SSCModel.CodeBean> buttons, int size, boolean isMin) {
        removeAllViews();
        for (int i = 0; i < buttons.size(); i++) {
            final RelativeLayout container = new RelativeLayout(getContext());
            QMUIRoundButton roundButton = new QMUIRoundButton(getContext());
            if (isMin)
                roundButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
            else
                roundButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            roundButton.setPadding(0, 0, 0, 0);
            roundButton.setText(String.valueOf(buttons.get(i)));
            roundButton.setTextColor(Color.WHITE);
            SSCModel.CodeBean codeBean = buttons.get(i);
            QMUIRoundButtonDrawable qmuiRoundButtonDrawable = (QMUIRoundButtonDrawable) roundButton.getBackground();
            if (!TextUtils.isEmpty(codeBean.getColor()))
                qmuiRoundButtonDrawable.setBgData(ColorStateList.valueOf(Color.parseColor(codeBean.getColor())));
            qmuiRoundButtonDrawable.setIsRadiusAdjustBounds(true);
            roundButton.setText(codeBean.getNumber());
            roundButton.setClickable(false);
            RelativeLayout.LayoutParams viewParam = new RelativeLayout.LayoutParams(size, size);
            viewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            roundButton.setLayoutParams(viewParam);
            container.addView(roundButton);
            MarginLayoutParams params = initParam(roundButton, size);
            container.setLayoutParams(params);
            addView(container);
        }
    }

    public void addCodeViewsNumber(List<Integer> codes) {
        removeAllViews();
        for (int i = 0; i < codes.size(); i++) {
            final RelativeLayout container = new RelativeLayout(getContext());
            NumberIcon numberIcon = new NumberIcon(getContext());
            numberIcon.setNumberTextSize(12);
            numberIcon.setNumber(codes.get(i));
            RelativeLayout.LayoutParams viewParam = new RelativeLayout.LayoutParams(ScreenDimenUtil.getInstance().getScreenWidth() / 11,
                    ScreenDimenUtil.getInstance().getScreenWidth() / 11);
            viewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            numberIcon.setLayoutParams(viewParam);
            container.addView(numberIcon);
            MarginLayoutParams params = initParam(numberIcon, 0);
//            params.topMargin=8;
            container.setLayoutParams(params);
            addView(container);
        }
    }

    public void addCodeViews(List<String> codes) {
        removeAllViews();
        for (int i = 0; i < codes.size(); i++) {
            final RelativeLayout container = new RelativeLayout(getContext());
            NumberIcon numberIcon = new NumberIcon(getContext());
            numberIcon.setNumberTextSize(12);
            numberIcon.setStringNumber(codes.get(i));
            int size = ScreenDimenUtil.getInstance().getScreenWidth() / 10 - ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN) - 4;
            RelativeLayout.LayoutParams viewParam = new RelativeLayout.LayoutParams(size,
                    size);
            viewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            numberIcon.setLayoutParams(viewParam);
            container.addView(numberIcon);
            MarginLayoutParams params;
            params = new MarginLayoutParams(size,
                    size);
            if (i % 10 == 0) {
                params.rightMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            }
            params.topMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            params.leftMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
//            params.topMargin=8;
            container.setLayoutParams(params);
            addView(container);
        }
    }

    public void addChooseCodeViewsNumber(List<Integer> codes) {
        removeAllViews();
        for (int i = 0; i < codes.size(); i++) {
            final RelativeLayout container = new RelativeLayout(getContext());
            NumberIcon numberIcon = new NumberIcon(getContext());
            numberIcon.setNumberTextSize(12);
            numberIcon.setNumber(codes.get(i));
            int size = ScreenDimenUtil.getInstance().getScreenWidth() / 10 - ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN) - 4;
            RelativeLayout.LayoutParams viewParam = new RelativeLayout.LayoutParams(size, size);
            viewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            numberIcon.setLayoutParams(viewParam);
            container.addView(numberIcon);
            MarginLayoutParams params;
            params = new MarginLayoutParams(size,
                    size);
            if (i % 10 == 0) {
                params.rightMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            }
            params.topMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            params.leftMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            container.setLayoutParams(params);
            addView(container);
        }
    }

    public void addChooseCodeViews(List<String> codes) {
        for (int i = 0; i < codes.size(); i++) {
            final RelativeLayout container = new RelativeLayout(getContext());
            NumberIcon numberIcon = new NumberIcon(getContext());
            numberIcon.setNumberTextSize(12);
            numberIcon.setStringNumber(codes.get(i));
            int size = ScreenDimenUtil.getInstance().getScreenWidth() / 10 - ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN) - 4;
            RelativeLayout.LayoutParams viewParam = new RelativeLayout.LayoutParams(size, size);
            viewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            numberIcon.setLayoutParams(viewParam);
            container.addView(numberIcon);
            MarginLayoutParams params;
            params = new MarginLayoutParams(size,
                    size);
            if (i % 10 == 0) {
                params.rightMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            }
            params.topMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            params.leftMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
//            params.topMargin=8;
            container.setLayoutParams(params);
            addView(container);
        }
    }

    public void addViews(List<Integer> codes) {
        for (int i = 0; i < codes.size(); i++) {
            final RelativeLayout container = new RelativeLayout(getContext());
            NumberIcon numberIcon = new NumberIcon(getContext());
            numberIcon.setNumberTextSize(12);
            numberIcon.setNumber(codes.get(i));
            RelativeLayout.LayoutParams viewParam = new RelativeLayout.LayoutParams(ScreenDimenUtil.dp2px(32),
                    ScreenDimenUtil.dp2px(32));
            viewParam.addRule(RelativeLayout.CENTER_IN_PARENT);
            numberIcon.setLayoutParams(viewParam);
            container.addView(numberIcon);
            MarginLayoutParams params = initParam(numberIcon, 0);
//            params.topMargin=8;
            container.setLayoutParams(params);
            addView(container);
        }
    }

    @NonNull
    private MarginLayoutParams initParam(View view, int size) {
        view.measure(0, 0);
        MarginLayoutParams params;
        if (size != 0) {
            params = new MarginLayoutParams(size,
                    size);
            params.leftMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            params.rightMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
//            params.topMargin =ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
//            params.bottomMargin =ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
        } else {
            params = new MarginLayoutParams(view.getMeasuredWidth(),
                    view.getMeasuredHeight());
            params.leftMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
            params.rightMargin = ScreenDimenUtil.dp2px(DEFAULT_CODE_IMG_MARGIN);
        }
        return params;
    }

    public List<String> getImages() {
        return mImages;
    }

    boolean mSingleLine;

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
        if (mSingleLine) {
            width = getMeasuredWidth();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
//                childWidth = Math.min(width / childCount - params.leftMargin - params.rightMargin, ScreenDimenUtil.dp2px(32));
//                ViewGroup.MarginLayoutParams viewParam = (ViewGroup.MarginLayoutParams) childView.getLayoutParams();
//                viewParam.width = childWidth;
//                viewParam.height = childHeight;
//                childView.setLayoutParams(viewParam);
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                childWidth = childView.getMeasuredWidth();
                childHeight = childWidth;
                maxWidth += childWidth + params.leftMargin + params.rightMargin;
                maxHeight = Math.max(childView.getMeasuredHeight() + params.bottomMargin + params
                        .topMargin, maxHeight);
            }
            height += maxHeight;
            setMeasuredDimension(width, height);
            return;
        }
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();
            MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            if (mSingleLine) {
                maxWidth += childWidth + params.leftMargin + params.rightMargin;
                maxHeight = Math.max(childView.getMeasuredHeight() + params.bottomMargin + params
                        .topMargin, maxHeight);
                continue;
            }
            if (maxWidth + childWidth + params.leftMargin + params.rightMargin > mWidth) {
                maxHeight = Math.max(childView.getMeasuredHeight() + params.bottomMargin + params
                        .topMargin, maxHeight);
//                if (i != childCount - 1)
                    height += maxHeight;
                maxWidth = childWidth + params.leftMargin + params.rightMargin;
            } else {
                maxWidth += childWidth + params.leftMargin + params.rightMargin;
                maxHeight = Math.max(childView.getMeasuredHeight() + params.bottomMargin + params
                        .topMargin, maxHeight);
            }
        }
        height += maxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            if (i == 0) {
                child.layout(params.leftMargin, 0, params.leftMargin + childWidth,
                        childHeight);
            } else {
                View last = getChildAt(i - 1);
                int right = last.getRight();
                int top = last.getTop();
                int bottom = last.getBottom();
                if (mSingleLine) {
                    child.layout(params.leftMargin + right, top, params.leftMargin + right +
                            childWidth, childHeight);
                    continue;
                }
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


    // 继承自margin，支持子视图android:layout_margin属性
    public static class LayoutParams extends MarginLayoutParams {


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }


        public LayoutParams(int width, int height) {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }


        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }
}
