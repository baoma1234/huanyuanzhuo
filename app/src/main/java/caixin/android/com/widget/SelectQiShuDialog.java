package caixin.android.com.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.caixin.huanyu.R;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.widget.picker.WheelChooseQishuAdapter;
import caixin.android.com.widget.picker.WheelView;

/**
 * Created by xxx on 2018/7/18.
 * 选择年份Dialog
 */

public class SelectQiShuDialog extends Dialog implements View.OnClickListener {

    private WheelView wheelQiShu;
    private List<String> mList = new ArrayList<>();
    private WheelChooseQishuAdapter qishuAdapter;
    private OnSelectorQiShuListener listener;

    public SelectQiShuDialog(Context context) {
        this(context, R.style.DialogTheme);
        initUI();
    }

    public SelectQiShuDialog(Context context, int theme) {
        super(context, theme);
    }

    private void initUI() {
        setContentView(R.layout.dialog_select_qishu);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        setCancelable(true);
        wheelQiShu = findViewById(R.id.wheel_qishu);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.tv_ok).setOnClickListener(this);
        qishuAdapter = new WheelChooseQishuAdapter();
        wheelQiShu.setCyclic(false);
        wheelQiShu.setIsOptions(true);
        wheelQiShu.setAdapter(qishuAdapter);
    }

    public void setData(List<String> list) {
        mList = list;
        qishuAdapter.setData(mList);
    }

    public void setQiShu(int qiShu) {
        for (int i = 0; i < mList.size(); i++) {
            if (Integer.valueOf(mList.get(i)) == qiShu) {
                wheelQiShu.setCurrentItem(i);
                break;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_ok:
                if (listener != null) {
                    listener.onSelectorQiShu(mList.get(wheelQiShu.getCurrentItem()));
                }
                dismiss();
                break;
        }
    }


    public void setOnSelectQiShuListener(OnSelectorQiShuListener listener) {
        this.listener = listener;
    }

    public interface OnSelectorQiShuListener {
        void onSelectorQiShu(String qishu);
    }
}