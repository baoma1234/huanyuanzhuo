package caixin.android.com.view.activity;

import static caixin.android.com.constant.Config.QR_SEPECIFIC_CODE;

import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityScanBinding;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.yalantis.ucrop.view.OverlayView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.constant.Config;
import caixin.android.com.daomanager.FriendDaoManager;
import caixin.android.com.entity.AppDownloadUrlEntity;
import caixin.android.com.entity.FriendEntity;
import caixin.android.com.utils.GlideEngine1;
import caixin.android.com.viewmodel.ContactViewModel;
import caixin.android.com.zxing.qrcodecore.QRCodeView;

public class ScanActivity extends BaseActivity<ActivityScanBinding, ContactViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_scan;
    }

    @Override
    public ContactViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ContactViewModel.class);
    }

    private String scanResult;

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.titleBar.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.titleBar.title.setText(getResources().getString(R.string.scan));
        mWindowAnimationStyle = new PictureWindowAnimationStyle();
        mWindowAnimationStyle.ofAllAnimation(R.anim.picture_anim_up_in, R.anim.picture_anim_down_out);
        mBinding.zxingview.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                Log.i(TAG, "result:" + result);
                if (TextUtils.isEmpty(result)) {
                    showShortToast(getResources().getString(R.string.scan_cannot_identify));
                } else {
                    vibrate();
                    if (result.contains(Config.QR_SEPECIFIC_CODE)) {
                        mViewModel.getAppDownLoadUrl();
                        scanResult = result;
                    } else {
                        Timer timer = new Timer();//实例化Timer类
                        timer.schedule(new TimerTask() {
                            public void run() {
                                mBinding.zxingview.startSpot(); // 开始识别
                            }
                        }, 2000);
                        showShortToast(getResources().getString(R.string.only_scan_related_my_app));
                    }
                }
            }

            @Override
            public void onCameraAmbientBrightnessChanged(boolean isDark) {
                // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
                String tipText = mBinding.zxingview.getScanBoxView().getTipText();
                String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
                if (isDark) {
                    if (!tipText.contains(ambientBrightnessTip)) {
                        mBinding.zxingview.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
                    }
                } else {
                    if (tipText.contains(ambientBrightnessTip)) {
                        tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                        mBinding.zxingview.getScanBoxView().setTipText(tipText);
                    }
                }
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                Log.e(TAG, "打开相机出错");
            }
        });

        mBinding.cbOpenLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBinding.zxingview.openFlashlight();// 打开闪光灯
                } else {
                    mBinding.zxingview.closeFlashlight();// 关闭闪光灯
                }
            }
        });

        mBinding.ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   EasyPhotos.createAlbum(ScanActivity.this, false, GlideEngine.getInstance())
                        .setCount(1)
                        .filter(Type.image())
                        .setGif(false)
                        .setPuzzleMenu(false)
                        .setCleanMenu(false)
                        .start(new SelectCallback() {
                            @Override
                            public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
//                                mBinding.zxingview.startSpotAndShowRect(); // 显示扫描框，并开始识别
                                mBinding.zxingview.decodeQRCode(paths.get(0));
                            }
                        });*/

                PictureSelector.create(ScanActivity.this)
                        .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .imageEngine(GlideEngine1.createGlideEngine())// 外部传入图片加载引擎，必传项
                        .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
                        .isWeChatStyle(true)// 是否开启微信图片选择风格
                        .isUseCustomCamera(true)// 是否使用自定义相机
                        .maxSelectNum(1)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        .maxVideoSelectNum(1) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                        .imageSpanCount(3)// 每行显示个数
                        .isReturnEmpty(true)// 未选择数据时点击按钮是否可以返回
                        .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，裁剪功能将会失效
                        .isDisplayOriginalSize(true)// 是否显示原文件大小，isOriginalImageControl true有效
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                        .isPreviewImage(false)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .setCameraImageFormat(PictureMimeType.PNG) // 相机图片格式后缀,默认.jpeg
//                .setCameraAudioFormat(PictureMimeType.AMR)// 录音音频格式后缀,默认.amr
                        .isEnableCrop(false)// 是否裁剪
                        .isCompress(true)// 是否压缩
                        .compressQuality(80)// 图片压缩后输出质量 0~ 100
                        .synOrAsy(true)//同步false或异步true 压缩 默认同步
                        .isGif(true)// 是否显示gif图片
                        .freeStyleCropMode(OverlayView.DEFAULT_FREESTYLE_CROP_MODE)// 裁剪框拖动模式
                        .isCropDragSmoothToCenter(true)// 裁剪框拖动时图片自动跟随居中
                        .isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                        .cutOutQuality(90)// 裁剪输出质量 默认100
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                if (null == result || result.isEmpty()) {
                                    return;
                                }
                                String path = result.get(0).getPath();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    path = result.get(0).getAndroidQToPath();
                                }
                                mBinding.zxingview.decodeQRCode(path);
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });
    }

    private static final String TAG = "ScanActivity";
    private PictureWindowAnimationStyle mWindowAnimationStyle;

    @Override
    public void initViewObservable() {
        mViewModel.uc.getAppDownLoadUrl.observe(this, this::handleGetAppDownloadUrl);
        mViewModel.uc.searchFriend.observe(this, this::handleSearchFriend);
    }

    private void handleSearchFriend(FriendEntity friendEntity) {
        if (friendEntity == null) {
            showShortToast("搜错出错，请联系客服！");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddFriendActivity.TAG_FRIEND, friendEntity);
        startActivity(NewFriendActivity.class, bundle);
        finish();
    }

    private void handleGetAppDownloadUrl(AppDownloadUrlEntity appDownloadUrlEntity) {
        scanResult = scanResult.replaceAll(QR_SEPECIFIC_CODE, "");
        String[] results = scanResult.split("&mobile=");
        String mobile;
        if (results.length == 2) {
            mobile = results[1];
            FriendEntity friendEntity = FriendDaoManager.getInstance().searchByAccount(mobile);
            if (friendEntity != null) {
                if (friendEntity.getIsBlack() == 2) {
                    showShortToast(getResources().getString(R.string.he_is_in_your_black_list));
                } else {
                    showShortToast(getResources().getString(R.string.already_exist_friend));
                }
            } else {
                mViewModel.searchFriend(mobile);
            }
        } else {
            dismissDialog();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBinding.zxingview.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mBinding.zxingview.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mBinding.zxingview.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        Log.e(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mBinding.zxingview.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
