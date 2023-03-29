package caixin.android.com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class WebUtils {
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    public void setWebView(Context context, LinearLayout linearLayout, String content) {
        linearLayout.removeAllViews();
        webView = new WebView(context);
        webView.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setSupportZoom(false); // 可以缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webView等宽的一列中
        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
        webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        webView.loadData( content, "text/html; charset=UTF-8", null);
        webView.setWebViewClient(new MyWebViewClient());
        linearLayout.addView(webView);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webView.setBackgroundColor(0);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();//重置webView中img标签的图片大小
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var obJs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<obJs.length;i++)" +
                "{ var img = obJs[i]; img.style.maxWidth = '100%'; img.style.height = 'auto';}})()");
    }
}