package caixin.android.com.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.PermissionUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import caixin.android.com.base.BaseFragment;
import caixin.android.com.base.BaseViewModel;
import caixin.android.com.constant.Extras;
import caixin.android.com.utils.FileUtil;
import caixin.android.com.utils.ImgLoader;
import caixin.android.com.utils.ToastUtils;

import com.caixin.huanyu.R;
import com.caixin.huanyu.databinding.FragmentPostImageDetailBinding;
import com.kongzue.dialog.v3.MessageDialog;

import java.io.File;
import java.lang.ref.WeakReference;


public class PostImageDetailFragment extends BaseFragment<FragmentPostImageDetailBinding, BaseViewModel> {
    private String mImage;

    public static PostImageDetailFragment newInstance(String image) {
        PostImageDetailFragment fragment = new PostImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Extras.IMAGE, image);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initContentView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        return R.layout.fragment_post_image_detail;
    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    // 保存图片到手机
    public void download() {
        MyAsyncTask mMyAsyncTask = new MyAsyncTask(this);
        mMyAsyncTask.execute();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        assert arguments != null;
        mImage = arguments.getString(Extras.IMAGE);
        ImgLoader.GlideLoad(mBinding.pvImage,mImage, R.mipmap.web_default);
        mBinding.ivDownload.setOnClickListener(v -> {
            if (PermissionUtils.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PostImageDetailFragment.this.download();
            } else {
                MessageDialog.show((AppCompatActivity) getActivity(), null, getString(R.string.need_permission_msg), getString(R.string.confirm), getString(R.string.cancel)).setOnOkButtonClickListener((baseDialog, v1) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", getContext().getPackageName(), null));
                    PostImageDetailFragment.this.startActivityForResult(intent, 0);
                    return false;
                });
            }
        });
        mBinding.pvImage.setOnClickListener(v -> getActivity().finish());
    }

    @Override
    public void initViewObservable() {

    }

    private static class MyAsyncTask extends AsyncTask<Void, Integer, File> {
        private WeakReference<PostImageDetailFragment> mReference;

        MyAsyncTask(PostImageDetailFragment fragment) {
            mReference = new WeakReference<>(fragment);
        }

        @Override
        protected File doInBackground(Void... params) {
            PostImageDetailFragment fragment = mReference.get();
            if (fragment == null) return null;
            File file = null;
            try {
                FutureTarget<File> future = Glide
                        .with(fragment)
                        .load(fragment.mImage)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                file = future.get();
                // 首先保存图片
                File pictureFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
                File appDir = new File(pictureFolder, "Beauty");
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }
                String fileName = System.currentTimeMillis() + ".jpg";
                File destFile = new File(appDir, fileName);

                FileUtil.getInstance().copyFile(file.getAbsolutePath(), destFile.getPath());
                // 最后通知图库更新
                fragment.getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(destFile.getPath()))));
            } catch (Exception ignored) {
            }
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            try {
                PostImageDetailFragment fragment = mReference.get();
                if (fragment == null) return;
                ToastUtils.show("已保存到图库");
                fragment.getActivity().finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}