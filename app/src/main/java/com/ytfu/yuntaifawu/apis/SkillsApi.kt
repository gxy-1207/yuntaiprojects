package com.ytfu.yuntaifawu.apis

import com.ytfu.yuntaifawu.ui.users.bean.SkillsBean
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SkillsApi {
    /**
     *使用技巧列表接口
     *https://yuntaifawu.com/api/find/shiyongjiqiao
     * @param  page
     * @param  uid
     * */
    @POST("find/shiyongjiqiao")
    @FormUrlEncoded
    suspend fun getSkillsList(@Field("page") page: Int,
                              @Field("uid") uid: String): SkillsBean
}