package com.ytfu.yuntaifawu.apis;

import android.os.SystemClock;

import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OppoService {


    @POST("api/uploadActiveData")
    Single<ResponseBody> uploadActiveData(
            @Header("signature") String signature,
            @Header("timestamp") String timestamp,
            @Body RequestBody info);
}
