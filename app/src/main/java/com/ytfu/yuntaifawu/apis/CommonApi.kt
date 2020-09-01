package com.ytfu.yuntaifawu.apis

import com.ytfu.yuntaifawu.ui.mine.bean.ShenHeJInduBean
import com.ytfu.yuntaifawu.ui.updatapk.UpDateApkBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface CommonApi {


    /**获取用户时候认证律师成功*/
    @POST("lvshi/query")
    @FormUrlEncoded
    suspend fun userAuthInfo(@Field("uid") userId: String): ShenHeJInduBean


    @GET("index/app_update?xt=1")
    suspend fun checkUpdate(): UpDateApkBean

}