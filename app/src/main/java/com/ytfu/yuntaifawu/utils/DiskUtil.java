package com.ytfu.yuntaifawu.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class DiskUtil {


    /**
     * 获取下载目录
     */
    public static File getPrivateDownloadDir(Context context) {
        return new File(context.getFilesDir(), Environment.DIRECTORY_DOWNLOADS);
    }

    public static File getPrivateCacheDir(Context context) {
        return context.getCacheDir();
    }

    public static File getTempDir(Context context) {
        File cacheDir = context.getExternalCacheDir();
        if (null == cacheDir) {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists() || !cacheDir.isDirectory()) {
            boolean isMk = cacheDir.mkdirs();
        }
        return cacheDir;
    }
}
