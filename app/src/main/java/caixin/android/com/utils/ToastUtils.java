package caixin.android.com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.caixin.huanyu.R;

import caixin.android.com.Application;


/**
 * Created by it on 2017/2/22.
 * 土司工具
 */

public class ToastUtils {

    private static Toast toast;

    public static void show(String text) {
        if (text == null) {
            return;
        }
        if (toast != null)
            toast.cancel();
        LayoutInflater inflater = (LayoutInflater) Application.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.layout_custom_toast, null);
        TextView contentText = view.findViewById(R.id.toast_message);
        contentText.setText(text);
        toast = new Toast(Application.getInstance());
        if (text.length() > 10) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(view);
        toast.show();
    }

    public static void show(int id) {
        show(Application.getInstance().getResources().getString(id));
    }

    public static void reset() {
        toast = null;
    }
}