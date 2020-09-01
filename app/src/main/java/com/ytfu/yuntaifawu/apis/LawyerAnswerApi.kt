package com.ytfu.yuntaifawu.apis

import com.ytfu.yuntaifawu.ui.mseeage.bean.LawyerAnswerBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LawyerAnswerApi {
    /**
     *https://yuntaifawu.com/api/index/get_lvshi_huida
     *律师回答接口
     */
    @POST("index/get_lvshi_huida")
    @FormUrlEncoded
    suspend fun getLawyerAnswer(@Field("lid") lid: String,
                                @Field("p") p: Int): LawyerAnswerBean
}