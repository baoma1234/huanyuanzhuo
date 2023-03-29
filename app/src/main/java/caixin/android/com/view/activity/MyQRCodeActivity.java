package caixin.android.com.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityMyQrCodeBinding;
import com.caixin.huanyu.databinding.ActivityMyWalletBinding;

import java.io.File;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.entity.AppDownloadUrlEntity;
import caixin.android.com.entity.UserInfoEntity;
import caixin.android.com.utils.FileUtil;
import caixin.android.com.utils.ImageDownLoadAsyTask;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.viewmodel.HomeViewModel;
import caixin.android.com.viewmodel.MyWalletViewModel;
import caixin.android.com.zxing.QRCodeEncoder;
import caixin.android.com.zxing.qrcodecore.BGAQRCodeUtil;

import static caixin.android.com.constant.Config.QR_SEPECIFIC_CODE;

public class MyQRCodeActivity extends BaseActivity<ActivityMyQrCodeBinding, HomeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_my_qr_code;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.titleBar.title.setText(getResources().getString(R.string.my_qr_code));
        UserInfoEntity userResponse = MMKVUtil.getUserInfo();
        ImgLoader.GlideLoadCircle(mBinding.ivAvatar, userResponse.getImg(), R.mipmap.img_user_head);
        mBinding.userNickname.setText(userResponse.getNikeName());
        mViewModel.getAppDownLoadUrl();
        mBinding.tvSaveQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myQrBitmap != null) {
                    FileUtil.saveFile(myQrBitmap, true);
                    showShortToast(getResources().getString(R.string.save_success));
                } else {
                    showShortToast(getResources().getString(R.string.qr_code_wrong));
                }
            }
        });
    }

    private Bitmap myQrBitmap;

    @Override
    public void initViewObservable() {
        mViewModel.uc.getAppDownLoadUrl.observe(this, this::handleGetAppDownloadUrl);
    }

    private void handleGetAppDownloadUrl(AppDownloadUrlEntity appDownloadUrlEntity) {
        createMyQRCodeWithLogo(appDownloadUrlEntity.getAddress());
    }

    private static final String TAG = "MyQRCodeActivity";

    private void createMyQRCodeWithLogo(String downloadUrl) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_caixin);
                String info = downloadUrl + "?incode=" + MMKVUtil.getUserInfo().getIncode() + "&mobile=" + MMKVUtil.getUserInfo().getMobile() + QR_SEPECIFIC_CODE;
                Log.e(TAG, "doInBackground: " + info);
                return QRCodeEncoder.syncEncodeQRCode(info, BGAQRCodeUtil.dp2px(MyQRCodeActivity.this, 200), Color.BLACK, Color.WHITE,
                        logoBitmap);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    myQrBitmap = bitmap;
                    runOnUiThread(() -> dismissDialog());
                    mBinding.ivQrCode.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(MyQRCodeActivity.this, "生成带logo的英文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
