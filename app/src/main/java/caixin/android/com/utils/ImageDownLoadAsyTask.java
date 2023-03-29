package caixin.android.com.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import caixin.android.com.constant.Contact;

/**
 * 下载网络图片
 */

public class ImageDownLoadAsyTask extends AsyncTask<Void, Integer, File> {

    private Context context;
    private String imageUrl;
    private DownloadCallBack downloadCallBack;

    public ImageDownLoadAsyTask(Context context, String imageUrl, DownloadCallBack downloadCallBack) {
        this.context = context;
        this.imageUrl = imageUrl;
        this.downloadCallBack = downloadCallBack;
    }

    @Override
    protected File doInBackground(Void... params) {
        if (context == null) return null;
        File file = null;
        String fileName = imageUrl.split("/")[imageUrl.split("/").length - 1];
        File appDir = new File(Contact.LOCAL_IMAGE_DIR);
        File destFile = new File(appDir, fileName);
        if (FileUtil.checkDirExist(destFile.getAbsolutePath())) {
            downloadCallBack.downloadSuccess();
            return null;
        }
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        try {
            FutureTarget<File> future = Glide
                    .with(context)
                    .load(imageUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            file = future.get();
            FileUtil.getInstance().copyFile(file.getAbsolutePath(), destFile.getPath());
            downloadCallBack.downloadSuccess();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), destFile.getAbsolutePath(), fileName, null);
            // 最后通知图库更新
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(destFile);
            intent.setData(uri);
            context.sendBroadcast(intent);
        } catch (Exception ignored) {
            downloadCallBack.downloadFailed();
        }
        return file;
    }

    @Override
    protected void onPostExecute(File file) {
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    public interface DownloadCallBack {

        public void downloadSuccess();

        public void downloadFailed();

    }
}
