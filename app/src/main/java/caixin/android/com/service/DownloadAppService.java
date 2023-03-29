package caixin.android.com.service;

import android.os.Environment;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;

/**
 * 作者：leavesC
 * 时间：2018/11/20 0:58
 * 描述：
 */
public class DownloadAppService {

    public static void downloadApp(String url, DownloadListener downloadListener) {
        DownloadQueue queue = NoHttp.newDownloadQueue();
        DownloadRequest request = new DownloadRequest(url, RequestMethod.GET, Environment.getExternalStorageDirectory().getPath(), true, false);
        queue.add(0, request, downloadListener);
    }

}
