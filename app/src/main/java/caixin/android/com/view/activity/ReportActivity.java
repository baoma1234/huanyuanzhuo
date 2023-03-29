/*
package caixin.android.com.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.ActivityReportBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import caixin.android.com.base.AppViewModelFactory;
import caixin.android.com.base.BaseActivity;
import caixin.android.com.entity.ReportRequest;
import caixin.android.com.utils.MMKVUtil;
import caixin.android.com.viewmodel.HomeViewModel;
import caixin.android.com.widget.UploadImage;
import caixin.android.com.widget.UploadPictureContainer;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ReportActivity extends BaseActivity<ActivityReportBinding, HomeViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        
        return R.layout.activity_report;
    }

    @Override
    public HomeViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(HomeViewModel.class);
    }

    private ReportRequest reportRequest;

    @Override
    public void initData(Bundle savedInstanceState) {
        mBinding.upcImgContainer.setMaxCount(9);
        mBinding.upcImgContainer.setOnUploadPictureListener(new UploadPictureContainer.OnUploadPictureListener() {
            @Override
            public void onDeleteClick(UploadImage uploadImage) {
                mBinding.upcImgContainer.removeImage(uploadImage);
            }

            @Override
            public void onAddClick() {
            */
/*    EasyPhotos.createAlbum(ReportActivity.this, true, GlideEngine.getInstance())
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
                        });*//*

            }

            @Override
            public void onPicClick() {

            }
        });

        mBinding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportRequest = new ReportRequest();
                reportRequest.setContent(mBinding.etContent.getText().toString());
                reportRequest.setToken(MMKVUtil.getToken());
                List<File> bitmaps = new ArrayList<>();
                if (mBinding.upcImgContainer.getUploadImages() != null && !mBinding.upcImgContainer.getUploadImages().isEmpty()) {
                    showDialog();
                    for (int i = 0; i < mBinding.upcImgContainer.getUploadImages().size(); i++) {
                        bitmaps.add(new File(mBinding.upcImgContainer.getUploadImages().get(i).getLocalImg()));
                    }
                    mViewModel.publishPictures(bitmaps);
                } else {
                    if (TextUtils.isEmpty(reportRequest.getContent())) {
                        showShortToast(getResources().getString(R.string.content_can_not_empty));
                        return;
                    }
                    showDialog();
                    mViewModel.report(reportRequest);
                }
            }
        });
    }

    @Override
    public void initViewObservable() {
        mViewModel.uc.publishPics.observe(this, this::handlePublishPics);
        mViewModel.uc.report.observe(this, this::handleReport);
    }

    private void handleReport(Object o) {
        finish();
    }

    private List<String> picPath = new ArrayList<>();


    private void handlePublishPics(List<String> strings) {
        reportRequest.setImgs(strings);
        mViewModel.report(reportRequest);
    }

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
                            mBinding.upcImgContainer.addImages(ReportActivity.this, picPath);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }
}
*/
