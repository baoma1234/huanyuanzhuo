package caixin.android.com.view.activity;

import static caixin.android.com.view.activity.ChatRoomActivity.OSS_ENDPOINT;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivitySendMyNewsBinding;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.yalantis.ucrop.view.OverlayView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.base.BaseModel;
import caixin.android.com.entity.AddMyNewsRequest;
import caixin.android.com.entity.OOSInfoEntity;
import caixin.android.com.utils.GlideEngine1;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.utils.OssService;
import caixin.android.com.utils.StatusBarUtils;
import caixin.android.com.utils.oss.MyOSSAuthCredentialsProvider;
import caixin.android.com.viewmodel.FriendCommunityViewModel;
import caixin.android.com.widget.UploadImage;
import caixin.android.com.widget.UploadPictureContainer;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class SendMyNewsActivity extends BaseActivity<ActivitySendMyNewsBinding, FriendCommunityViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_send_my_news;
    }

    private PictureWindowAnimationStyle mWindowAnimationStyle;

    @Override
    public FriendCommunityViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(FriendCommunityViewModel.class);
    }

    private String bucketName = "huanyump";
    private OssService mService;
    private LocalMedia localMedia;

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtils.immersive(this, this.getResources().getColor(R.color.colorPrimary));
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mWindowAnimationStyle = new PictureWindowAnimationStyle();
        mWindowAnimationStyle.ofAllAnimation(R.anim.picture_anim_up_in, R.anim.picture_anim_down_out);
        mBinding.upcImgContainer.setMaxCount(9);
        mBinding.upcImgContainer.setOnUploadPictureListener(new UploadPictureContainer.OnUploadPictureListener() {
            @Override
            public void onDeleteClick(UploadImage uploadImage) {
                mBinding.upcImgContainer.removeImage(uploadImage);
            }

            @Override
            public void onAddClick() {
              /*  EasyPhotos.createAlbum(SendMyNewsActivity.this, true, GlideEngine.getInstance())
                        .setCount(9)
                        .filter(Type.image())
                        .setGif(false)
                        .setPuzzleMenu(false)
                        .setCleanMenu(false)
                        .start(new SelectCallback() {
                            @Override
                            public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
                                new Thread(() -> zipPic(paths)).start();
                            }
                        });*/
                localMedia = null;
                PictureSelector.create(SendMyNewsActivity.this)
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
                                localMedia = result.get(0);
                                showDialog("发送中...");
                                mViewModel.getOOSInfo();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }

            @Override
            public void onPicClick() {

            }
        });

        mBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                if (addMyNewsRequest == null)
                    addMyNewsRequest = new AddMyNewsRequest();
                addMyNewsRequest.setContent(mBinding.etContent.getText().toString());
                addMyNewsRequest.setToken(MMKVUtil.getToken());
                List<File> bitmaps = new ArrayList<>();
                if (mBinding.upcImgContainer.getUploadImages() != null && !mBinding.upcImgContainer.getUploadImages().isEmpty()) {
                    for (int i = 0; i < mBinding.upcImgContainer.getUploadImages().size(); i++) {
                        bitmaps.add(new File(mBinding.upcImgContainer.getUploadImages().get(i).getLocalImg()));
                    }
                    mViewModel.publishPictures(bitmaps);
                } else {
                    mViewModel.addMyNews(addMyNewsRequest);
                }
            }
        });
    }

    private AddMyNewsRequest addMyNewsRequest;

    private List<String> picPath = new ArrayList<>();

    private void zipPic(List<String> list) {
        picPath.clear();
        Luban.with(this)
                .load(list)
                .ignoreBy(200)
                .setFocusAlpha(false)
                .filter(path -> !(TextUtils.isEmpty(path)))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        picPath.add(file.getAbsolutePath());
                        if (picPath.size() == list.size()) {
                            mBinding.upcImgContainer.addImages(SendMyNewsActivity.this, picPath);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.publishPics.observe(this, this::handlePublishPics);
        mViewModel.uc.addMyNews.observe(this, this::handleAddMyNews);
        mViewModel.uc.getOOSInfo.observe(this, this::handleGetOOS);
    }

    private static final String TAG = "SendMyNewsActivity";

    private void handleGetOOS(OOSInfoEntity oosInfoEntity) {
        if (localMedia != null) {
            String fileName;
            String path = localMedia.getPath();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                path = localMedia.getAndroidQToPath();
            }
            if (TextUtils.isEmpty(path)) {
                path = localMedia.getRealPath();
            }
            if (TextUtils.isEmpty(path)) {
                dismissDialog();
                return;
            }

            fileName = path.split("/")[path.split("/").length - 1];
            mService = initOSS(OSS_ENDPOINT, bucketName, "http://112.121.164.18:6161/api/Sts/getStsOuah");
            //设置上传的callback地址，目前暂时只支持putObject的回调
            mService.setCallbackAddress(oosInfoEntity.getUrl());
            String finalPath = path;
            mService.asyncPutImage(fileName, path, new BaseModel.Callback() {
                @Override
                public void onSuccess(Object data, String msg) {
                    picPath.add(finalPath);

                }

                @Override
                public void onFailure(String msg) {

                }

                @Override
                public void onDisconnected(String msg) {

                }
            });
        }
    }


    public OssService initOSS(String endpoint, String bucket, String stsServer) {

//        移动端是不安全环境，不建议直接使用阿里云主账号ak，sk的方式。建议使用STS方式。具体参
//        https://help.aliyun.com/document_detail/31920.html
//        注意：SDK 提供的 PlainTextAKSKCredentialProvider 只建议在测试环境或者用户可以保证阿里云主账号AK，SK安全的前提下使用。具体使用如下
//        主账户使用方式
//        String AK = "******";
//        String SK = "******";
//        credentialProvider = new PlainTextAKSKCredentialProvider(AK,SK)
//        以下是使用STS Sever方式。
//        如果用STS鉴权模式，推荐使用OSSAuthCredentialProvider方式直接访问鉴权应用服务器，token过期后可以自动更新。
//        详见：https://help.aliyun.com/document_detail/31920.html
//        OSSClient的生命周期和应用程序的生命周期保持一致即可。在应用程序启动时创建一个ossClient，在应用程序结束时销毁即可。

        MyOSSAuthCredentialsProvider credentialProvider = new MyOSSAuthCredentialsProvider(stsServer);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        OSSLog.enableLog();
        return new OssService(oss, bucket);
    }

    private void handleAddMyNews(Object o) {
        finish();
    }

    private void handlePublishPics(List<String> strings) {
        addMyNewsRequest.setImgs(strings);
        mViewModel.addMyNews(addMyNewsRequest);
    }
}
