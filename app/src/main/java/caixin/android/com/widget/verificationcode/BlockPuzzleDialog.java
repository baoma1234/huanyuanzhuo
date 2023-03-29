package caixin.android.com.widget.verificationcode;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.caixin.huanyu.R;

import caixin.android.com.entity.CaptchaGetIt;
import caixin.android.com.utils.ImageUtil;

/**
 * Date:2020/5/19
 * author:wuyan
 */
public class BlockPuzzleDialog extends Dialog {
    private String baseImageBase64;//背景图片
    private String slideImageBase64;//滑动图片
    private String token;
    private Context mContext;
    private TextView tvDelete;
    private ImageView tvRefresh;
    private DragImageView dragView;
    private Handler handler = new Handler();
    private String key;

    public BlockPuzzleDialog(@NonNull Context context) {
        super(context, R.style.BlockPuzzleDialog);
        this.mContext = context;
        setContentView(R.layout.dialog_block_puzzle);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        ViewGroup.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 9 / 10;
        getWindow().setAttributes((WindowManager.LayoutParams) lp);
        setCanceledOnTouchOutside(false);//点击外部Dialog不消失
        initView();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnResultsListener != null)
                    mOnResultsListener.onReInit();
            }
        });
    }

    private void initView() {
        tvDelete = findViewById(R.id.tv_delete);
        tvRefresh = findViewById(R.id.tv_refresh);
        dragView = findViewById(R.id.dragView);
        Bitmap bitmap = ImageUtil.getBitmap(getContext(), R.drawable.bg_default);
        dragView.setUp(bitmap, bitmap);
    }

    public void init(CaptchaGetIt data) {
        baseImageBase64 = data.getOriginalImageBase64();
        slideImageBase64 = data.getJigsawImageBase64();
        token = data.getToken();
        key = data.getSecretKey();
        dragView.setUp(ImageUtil.base64ToBitmap(baseImageBase64), ImageUtil.base64ToBitmap(slideImageBase64));
        initEvent();
    }

    private void initEvent() {
        dragView.setDragListenner(new DragImageView.DragListenner() {
            @Override
            public void onDrag(double position) {
                if (mOnResultsListener != null)
                    mOnResultsListener.checkCaptcha(position);
            }
        });
    }

    public void OK() {
        dragView.ok();
    }

    public void FAILED() {
        dragView.fail();
    }

    private OnResultsListener mOnResultsListener;

    public interface OnResultsListener {
        void onResultsClick(String result);

        void onReInit();

        void checkCaptcha(double sliderXMoved);
    }

    public void setOnResultsListener(OnResultsListener mOnResultsListener) {
        this.mOnResultsListener = mOnResultsListener;
    }
}
