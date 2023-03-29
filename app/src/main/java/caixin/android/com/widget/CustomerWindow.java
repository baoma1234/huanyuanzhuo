package caixin.android.com.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;

import java.util.List;

import caixin.android.com.adapter.CustomerAdapter;
import caixin.android.com.entity.CustomerModel;

public class CustomerWindow {
    private PopupWindow mPopupWindow;
    private Activity mActivity;
    private final View mView;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(CustomerModel model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CustomerWindow setTitle(String title) {
        if (!TextUtils.isEmpty(title))
            ((TextView) mView.findViewById(R.id.tv_title)).setText(title);
        return this;
    }

    public CustomerWindow(Activity activity, List<CustomerModel> names) {
        this(activity, names, 0);
    }

    public CustomerWindow(Activity activity, List<CustomerModel> names, int type) {
        mActivity = activity;
        mView = LayoutInflater.from(mActivity).inflate(R.layout.window_choose_customer, null);
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            RecyclerView rvChoose = mView.findViewById(R.id.rv_customer);
            rvChoose.setLayoutManager(new LinearLayoutManager(mActivity));
            CustomerAdapter customerAdapter = new CustomerAdapter(names,type);
            rvChoose.setAdapter(customerAdapter);
            customerAdapter.setOnItemClickListener((adapter, view, position) -> {
                CustomerModel customerModel = (CustomerModel) adapter.getItem(position);
                if (customerModel == null) return;
                mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onItemClick(customerModel);
                }
            });
            TextView tvCancel = mView.findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(view1 -> mPopupWindow.dismiss());
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setAnimationStyle(R.style.AnimBottom);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mPopupWindow.setOnDismissListener(() -> backgroundAlpha(1f));
        }
    }

    public void show() {
        backgroundAlpha(0.5f);
        mPopupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
    }


    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(layoutParams);
    }
}
