package com.ytfu.yuntaifawu.utils.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.tencent.smtt.sdk.TbsReaderView;
import com.ytfu.yuntaifawu.R;
import com.ytfu.yuntaifawu.apis.download.DownloadUtil;
import com.ytfu.yuntaifawu.apis.download.OnDownloadListener;
import com.ytfu.yuntaifawu.utils.DiskUtil;

import java.io.File;

/**
 * office在线预览的自定义view
 */
public class OfficePreviewView extends FrameLayout implements TbsReaderView.ReaderCallback {
    private static String TAG = "OfficePreviewView";
    private TbsReaderView previewView;

    private LinearLayout ll_preview_tip;
    private ProgressBar pb_preview_progress;
    //===Desc:================================================================================

    public OfficePreviewView(@NonNull Context context) {
        this(context, null);
    }

    public OfficePreviewView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OfficePreviewView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //===Desc:================================================================================

    private void init(Context context) {
        View rootView = inflate(context, R.layout.view_office_preview, this);

        ll_preview_tip = rootView.findViewById(R.id.ll_preview_tip);
        pb_preview_progress = rootView.findViewById(R.id.pb_preview_progress);


        FrameLayout fl_preview_content = rootView.findViewById(R.id.fl_preview_content);
        previewView = new TbsReaderView(context, this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        fl_preview_content.addView(previewView, params);

    }

    private String getFileType(String fileName) {
        String str = "";
        if (TextUtils.isEmpty(fileName)) {
            return str;
        }
        int i = fileName.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = fileName.substring(i + 1);
        return str;
    }

    //===Desc:================================================================================

    /**
     * 加载远端文件
     */
    //https://www.cs.cmu.edu/afs/cs.cmu.edu/academic/class/15251-f06/Site/Materials/Lectures/Lecture01/lecture01.pdf
    public void loadUrl(String url) {
        //开始下载
        DownloadUtil.startDownloadByGet(getContext(), url, new OnDownloadListener() {
            @Override
            public void onStart() {
                Logger.e("开始下载");
                ll_preview_tip.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgress(long current, long total) {
                Logger.e("总长度：　" + total + "...当前进度：　" + current);
                int progress = (int) ((current * 1.0 / total) * 100);
                pb_preview_progress.setMax(100);
                pb_preview_progress.setProgress(progress);
            }

            @Override
            public void onFinish(File file) {
                Logger.e("下载完成　：　" + file.getAbsolutePath());
                ll_preview_tip.setVisibility(View.GONE);

                boolean preOpen = previewView.preOpen(getFileType(file.getName()), false);
                if (preOpen) {
                    Bundle bundle = new Bundle();
                    bundle.putString("filePath", file.getAbsolutePath());
                    bundle.putString("tempPath", DiskUtil.getTempDir(getContext()).getAbsolutePath());
                    previewView.openFile(bundle);
                } else {
                    for (int i = 0; i < 5; i++) {
                        preOpen = previewView.preOpen(getFileType(file.getName()), false);
                        if (preOpen) {
                            Bundle bundle = new Bundle();
                            bundle.putString("filePath", file.getAbsolutePath());
                            bundle.putString("tempPath", DiskUtil.getTempDir(getContext()).getAbsolutePath());
                            previewView.openFile(bundle);
                            break;
                        } else {
                            continue;
                        }

                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Logger.e("下载失败", t);
                t.printStackTrace();
            }
        });


    }


    public void onDestroy() {
        previewView.onStop();
    }

    //===Desc:================================================================================
    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
        Log.e(TAG, "onCallBackAction: integer = " + integer + ", object = " + o + ",object1 = " + o1);
    }
}
