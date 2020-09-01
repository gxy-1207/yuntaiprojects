package com.ytfu.yuntaifawu.apis.download;

import java.io.File;

public interface OnDownloadListener {

    void onStart();

    void onProgress(long current, long total);

    void onFinish(File file);

    void onFailure(Throwable t);

}
