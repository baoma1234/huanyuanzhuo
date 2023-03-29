package caixin.android.com.widget.ait;

import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;


public class MyTextSpan extends ForegroundColorSpan {

    private String showText;
    private long userId;

    //userId是为了适应需求，如果不需要请自行去掉
    public MyTextSpan(String showText, long userId, int colorRes) {
        super(colorRes);
        this.showText = showText;
        this.userId = userId;
    }

    public String getShowText() {
        return showText;
    }

    public long getUserId() {
        return userId;
    }
}
