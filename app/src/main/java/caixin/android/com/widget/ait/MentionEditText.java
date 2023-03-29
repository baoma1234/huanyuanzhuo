package caixin.android.com.widget.ait;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.widget.EditText;

public class MentionEditText extends EditText{

    public MentionEditText(Context context) {
        super(context);
    }


    //这个是需要成块删除的内容
    private class MyTextSpan extends MetricAffectingSpan {
        private String showText;
        private long userId;
        //userId是为了适应需求，如果不需要请自行去掉
        public MyTextSpan(String showText, long userId) {
            this.showText = showText;
            this.userId = userId;
        }
        public String getShowText() {
            return showText;
        }
        public long getUserId() {
            return userId;
        }
        @Override
        public void updateMeasureState(TextPaint p) {
        }
        @Override
        public void updateDrawState(TextPaint tp) {
        }
    }
    //这个是非整块删除的内容，当然你如果想也是可以删除的
    private class UnSpanText {
        int start;
        int end;
        String returnText;
        UnSpanText(int start, int end, String returnText) {
            this.start = start;
            this.end = end;
            this.returnText = returnText;
        }
    }


    public void addSpan(String showText, String returnText, long userId) {
        getText().append(showText);
        SpannableString spannableString = new SpannableString(getText());
        makeSpan(spannableString, new UnSpanText(spannableString.length() - showText.length(), spannableString.length(),returnText), userId);
        setText(spannableString);
        setSelection(spannableString.length());
    }
    //生成一个需要整体删除的Span
    private void makeSpan(Spannable sps, UnSpanText unSpanText, long userId) {
        MyTextSpan what = new MyTextSpan(unSpanText.returnText, userId);
        int start = unSpanText.start;
        int end = unSpanText.end;
        sps.setSpan(what, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
