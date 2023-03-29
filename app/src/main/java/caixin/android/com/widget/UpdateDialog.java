package caixin.android.com.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.caixin.huanyu.R;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;

import java.io.File;
import java.util.Objects;

import caixin.android.com.entity.AppVersion;
import caixin.android.com.service.DownloadAppService;
import caixin.android.com.service.UpdateAppService;

public class UpdateDialog extends DialogFragment {

    @SuppressLint("InflateParams")
    private View mView;
    private AppVersion mAppVersion;
    private ProgressDialog mProgressDialog;

    public void setAppVersion(AppVersion contents) {
        mAppVersion = contents;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()), R.style.style_dailog_from_transparent);
        mView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_update, null);
        mView.findViewById(R.id.iv_close).setOnClickListener(v -> dialog.dismiss());
        if (mAppVersion.getIs_update() == 1) {
            mView.findViewById(R.id.iv_close).setVisibility(View.GONE);
        }
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("开始下载新版本APP");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (mAppVersion != null && !TextUtils.isEmpty(mAppVersion.getContents())) {
            ((TextView) mView.findViewById(R.id.tv_msg)).setText(mAppVersion.getContents().replace("\\n", "\n"));
            mView.findViewById(R.id.btn_update).setOnClickListener(v -> downloadApp(mAppVersion));
        }
        dialog.setContentView(mView);
        return dialog;
    }

    private void downloadApp(AppVersion appVersion) {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
            mProgressDialog.setCancelable(false);
        }
        updateApp(appVersion.getWeblocal());
    }

    private void updateApp(String url) {
        DownloadAppService.downloadApp(url, mDownloadListener);
    }

    private DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void onDownloadError(int what, Exception exception) {
            if (isFinishingOrDestroyed()) return;
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
        }

        @Override
        public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders,
                            long allCount) {
            if (isFinishingOrDestroyed()) return;
            if (mProgressDialog != null && !mProgressDialog.isShowing()) {
                mProgressDialog.setMax(100);
                mProgressDialog.setProgress(0);
                mProgressDialog.show();
            }
        }

        @Override
        public void onProgress(int what, int progress, long fileCount, long speed) {
            if (isFinishingOrDestroyed()) return;
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.setProgress(progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
            if (isFinishingOrDestroyed()) return;
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
            UpdateAppService.installApp(getContext(), new File(filePath));
        }

        @Override
        public void onCancel(int what) {
            if (isFinishingOrDestroyed()) return;
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
        }
    };

    private boolean isFinishingOrDestroyed() {
        return getActivity() == null || getActivity().isFinishing() || getActivity().isDestroyed();
    }
}