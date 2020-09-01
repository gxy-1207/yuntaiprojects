package com.ytfu.yuntaifawu.apis.interceptor

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.ytfu.yuntaifawu.app.App
import com.ytfu.yuntaifawu.ui.TipLoginActivity
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.nio.charset.Charset

class CheckTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalResponse = chain.proceed(originalRequest)

        val responseBody = originalResponse.body() ?: return originalResponse

        val source = responseBody.source()
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer
        val contentType = responseBody.contentType()
        val charset = if (null == contentType) {
            Charset.forName("UTF-8")
        } else {
            contentType.charset(Charset.forName("UTF-8")) ?: Charset.forName("UTF-8")
        }
        val bodyString = buffer.clone().readString(charset)
        //        LogUtil.e("Interceptor response body is : $bodyString")
        try {
            val invalidTokenBean = Gson().fromJson(bodyString, InvalidTokenBean::class.java)
            //            invalidTokenBean = InvalidTokenBean("", "", "", 666)
            if (null == invalidTokenBean) {
                return originalResponse
            } else {
                val status = invalidTokenBean.status
                return if (status == 666) {
                    if (!TipLoginActivity.isOpenLogin) {
                        TipLoginActivity.startWithFlag(App.getContext())
                    }
                    val create = ResponseBody.create(null, "")
                    originalResponse.newBuilder().body(
                            create).build()
                    //                    return originalResponse //chain.proceed(originalRequest)
                } else {
                    originalResponse
                }
            }
        } catch (e: JsonParseException) {
            return originalResponse
        }

    }
}