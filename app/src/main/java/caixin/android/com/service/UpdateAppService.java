package caixin.android.com.service;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import com.caixin.huanyu.R;
import com.tencent.mmkv.MMKV;

import java.io.File;
import java.util.Objects;

/**
 * 作者：leavesC
 * 时间：2018/11/18 17:58
 * 描述：
 */
public class UpdateAppService {

    public static final int INSTALL_REQUEST_CODE = 10010;

    public static void saveUpdateId(long id) {
        MMKV kv = MMKV.defaultMMKV();
        kv.putLong("Id", id);
    }

    public static long getUpdateId() {
        MMKV kv = MMKV.defaultMMKV();
        return kv.getLong("Id", -1);
    }

    public static void downloadApk(Context context, String apkUrl, String apkName) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, apkName.concat(".apk"));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(context.getString(R.string.app_name));
        request.setDescription("APK下载中...");
        //获得唯一下载id
        if (downloadManager != null) {
            long id = downloadManager.enqueue(request);
            saveUpdateId(id);
        }
    }

    public static void installApp(Context mContext, long id) {
        File apkFile = queryApkPath(mContext, id);
        installApp(mContext, apkFile);
    }

    public static void installApp(Context mContext, File apkFile) {
        if (apkFile != null) {
            try {
                //8.0跳转设置允许安装未知应用
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = mContext.getPackageManager().canRequestPackageInstalls();
                    if (!hasInstallPermission) {
                        startInstallPermissionSettingActivity(mContext);
                        return;
                    }
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //兼容7.0之后禁用在应用外部公开file://URI，以FileProvider替换
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //给目标应用一个临时授权
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //注意此处的authority必须与manifest的provider保持一致
                    intent.setDataAndType(FileProvider.getUriForFile(mContext, mContext.getPackageName().concat(".fileprovider"), apkFile), "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                }
                //验证是否有APP可以接受此Intent，防止FC
                if (mContext.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    mContext.startActivity(intent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    private static File queryApkPath(Context context, long id) {
        File apkFile = null;
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        Cursor cur = manager.query(query);
        if (cur != null) {
            if (cur.moveToFirst()) {
                // 获取文件下载路径
                String filePath = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                if (!TextUtils.isEmpty(filePath)) {
                    apkFile = new File(Objects.requireNonNull(Uri.parse(filePath).getPath()));
                }
            }
            cur.close();
        }
        return apkFile;
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context mContext) {
        Uri packageURI = Uri.parse("package:".concat(mContext.getPackageName()));
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        ((Activity) mContext).startActivityForResult(intent, INSTALL_REQUEST_CODE);
    }

}
