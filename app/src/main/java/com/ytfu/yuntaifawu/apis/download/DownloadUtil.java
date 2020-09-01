package com.ytfu.yuntaifawu.apis.download;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.ytfu.yuntaifawu.apis.HttpUtil;
import com.ytfu.yuntaifawu.utils.DiskUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadUtil {

    private static Handler handler = new Handler();


    public static void startDownloadByGet(Context context, String url, @NonNull OnDownloadListener listener) {
        //解析url获取文件名称
//        try {
//            url = URLEncoder.encode(url, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String fileName = "";
        int index = url.lastIndexOf("/");
        if (index != -1) {
            fileName = url.substring(index);
        }

        if (TextUtils.isEmpty(fileName)) {
            runOnUiThread(() -> listener.onFailure(new IllegalAccessException("Can not get remote file name")));
            return;
        }
        File saveFile = new File(DiskUtil.getPrivateCacheDir(context), fileName);

        DownloadApi api = HttpUtil.getInstance().getService(DownloadApi.class);
        Call<ResponseBody> downloadCall = api.downloadByGet(url);
        downloadCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                new Thread() {
                    @Override
                    public void run() {
                        //写文件到本地
                        saveRemoteFile(response, saveFile, listener);
                    }
                }.start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                runOnUiThread(() -> listener.onFailure(t));
            }
        });


    }


    private static void saveRemoteFile(Response<ResponseBody> response, File localFile, @NotNull OnDownloadListener listener) {
        runOnUiThread(listener::onStart);
        long progress = 0;
        OutputStream os = null;
        ResponseBody body = response.body();
        if (null == body) {
            runOnUiThread(() -> listener.onFailure(new IllegalAccessException()));
            return;
        }
        InputStream is = body.byteStream();
        long total = body.contentLength();
        try {
            os = new FileOutputStream(localFile);
            int len;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                os.write(buf, 0, len);
                os.flush();
                progress += len;
                long current = progress;
                runOnUiThread(() -> listener.onProgress(current, total));
            }
            runOnUiThread(() -> listener.onFinish(localFile));
        } catch (IOException e) {
            runOnUiThread(() -> listener.onFailure(e));
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //===Desc:================================================================================

    private static void runOnUiThread(Runnable run) {
        handler.post(run);
    }

}
