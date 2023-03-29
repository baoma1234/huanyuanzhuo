package caixin.android.com.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caixin.huanyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import caixin.android.com.adapter.HomeMenuItemAdapter;
import caixin.android.com.constant.Extras;
import caixin.android.com.entity.CustomerModel;
import caixin.android.com.entity.FindItemModel;
import caixin.android.com.entity.HomeMenuItemResponse;
import caixin.android.com.utils.ActionUtil;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.ScreenDimenUtil;
import caixin.android.com.view.activity.LoginActivity;

public class HomeMenuPop {
    private PopupWindow mPopupWindow;
    private Activity mActivity;
    private final View mView;
    private MyOnClick mListener;
    private RecyclerView recyclerView;
    private List<FindItemModel> findItemModels;
    private List<HomeMenuItemResponse> homeMenuItemResponses;

    public void setFindItemModels(List<FindItemModel> findItemModels) {
        this.findItemModels = findItemModels;
        initItem();
    }

    public interface OnItemClickListener {
        void onItemClick(CustomerModel model);
    }

    public void setListener(MyOnClick mListener) {
        this.mListener = mListener;
    }

    int popupWidth;
    int popupHeight;

    public void setOnDismiss(PopupWindow.OnDismissListener onDismissListener) {
        mPopupWindow.setOnDismissListener(onDismissListener);
    }

    HomeMenuItemAdapter homeMenuItemAdapter;

    private void initItem() {
        if (homeMenuItemResponses == null)
            homeMenuItemResponses = new ArrayList<>();
        homeMenuItemResponses.clear();
        homeMenuItemResponses.add(new HomeMenuItemResponse("签到", "https://01234789.com/img/caixinhoutai/2020-09-11/1599807759393caixinhoutai.png", 0, R.mipmap.ic_home_menu_sign));
        homeMenuItemResponses.add(new HomeMenuItemResponse("澳门图库", "https://01234789.com/img/caixinhoutai/2020-09-11/1599807759393caixinhoutai.png", 1, R.mipmap.ic_home_menu_aomen_gallery));
        homeMenuItemResponses.add(new HomeMenuItemResponse("香港图库", "https://01234789.com/img/caixinhoutai/2020-09-11/1599807759393caixinhoutai.png", 2, R.mipmap.ic_home_menu_xianggang_gallery));
        homeMenuItemResponses.add(new HomeMenuItemResponse("挑码助手", "https://01234789.com/img/caixinhoutai/2020-09-11/1599807759393caixinhoutai.png", 3, R.mipmap.ic_home_menu_quer_code));
        homeMenuItemResponses.add(new HomeMenuItemResponse("实用宝库", "https://01234789.com/img/caixinhoutai/2020-09-11/1599807759393caixinhoutai.png", 4, R.mipmap.icon_interest_game_aomen));
        if (findItemModels != null && findItemModels.size() > 0) {
            for (int i = 0; i < findItemModels.size(); i++) {
                if (!Patterns.WEB_URL.matcher(findItemModels.get(i).getHref()).matches()) {
                    continue;
                }
                HomeMenuItemResponse homeMenuItemResponse = new HomeMenuItemResponse();
                homeMenuItemResponse.setTitle(findItemModels.get(i).getTitle());
                homeMenuItemResponse.setId(5 + i);
                homeMenuItemResponse.setImg(findItemModels.get(i).getImg());
                homeMenuItemResponse.setUrl(findItemModels.get(i).getHref());
                homeMenuItemResponses.add(homeMenuItemResponse);
            }
        }
        homeMenuItemAdapter.setNewData(homeMenuItemResponses);
    }

    public HomeMenuPop(Activity activity) {
        Log.e(TAG, "HomeMenuPop: ");
        mActivity = activity;
        mView = LayoutInflater.from(mActivity).inflate(R.layout.pop_home_menu, null);
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(mView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
            mView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupWidth = mView.getMeasuredWidth();
            mPopupWindow.setAnimationStyle(R.style.AnimBottom);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            recyclerView = mView.findViewById(R.id.rv_content);
            recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4));
            homeMenuItemAdapter = new HomeMenuItemAdapter(homeMenuItemResponses, mActivity);
            recyclerView.setAdapter(homeMenuItemAdapter);
            homeMenuItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Log.e(TAG, "onItemClick: ");
                    if (mListener != null)
                        mListener.onClick(homeMenuItemAdapter.getData().get(position));
                }
            });
            popupHeight = mView.getMeasuredHeight();
            initItem();
            Log.e(TAG, "HomeMenuPop: " + popupHeight);
        }
    }

    private static final String TAG = "HomeMenuPop";

    public void show(View parent) {
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        Log.e(TAG, "show: " + location[1]);
        int row;
        if (homeMenuItemResponses.size() % 4 == 0) {
            row = homeMenuItemResponses.size() / 4;
        } else {
            row = homeMenuItemResponses.size() / 4 + 1;
        }
        mPopupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, location[1] - ScreenDimenUtil.dp2px(10) - popupHeight - ScreenDimenUtil.dp2px(66 * row));
        homeMenuItemAdapter.notifyDataSetChanged();
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
        layoutParams.alpha = bgAlpha;
        mActivity.getWindow().setAttributes(layoutParams);
    }

    public interface MyOnClick {
        public void onClick(HomeMenuItemResponse homeMenuItemAdapter);
    }

    public void dismiss() {
        if (mPopupWindow != null)
            mPopupWindow.dismiss();
    }
}
