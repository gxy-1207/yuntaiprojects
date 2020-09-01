package com.ytfu.yuntaifawu.apis

import com.ytfu.yuntaifawu.ui.mseeage.bean.EvaluateClassBean
import com.ytfu.yuntaifawu.ui.mseeage.bean.EvaluateListBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EvaluateService {
    /**
     * 评价分类接口
     *https://yuntaifawu.com/api/index/get_talk_class
     * */
    @POST("index/get_talk_class")
    @FormUrlEncoded
    suspend fun getEvaluateClass(@Field("lid") lid: String): EvaluateClassBean

    /**
     * 评价列表接口
     *https://yuntaifawu.com/api/shop/talk_list
     * */
    @POST("shop/talk_list")
    @FormUrlEncoded
    suspend fun getEvaluateList(@Field("lid") lid: String,
                                @Field("pingjia") pingjia: String,
                                @Field("p") p: Int): EvaluateListBean
}