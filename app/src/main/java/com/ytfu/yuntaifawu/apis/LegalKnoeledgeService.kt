package com.ytfu.yuntaifawu.apis

import com.ytfu.yuntaifawu.ui.knowledge.bean.KnowledgeDetailsBean
import com.ytfu.yuntaifawu.ui.knowledge.bean.LegalKnowledgeBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LegalKnoeledgeService {
    /**
     * 知识列表
     * https://yuntaifawu.com/api/special/get_zhuanti
     * */
    @POST("special/get_zhuanti")
    fun getLegalKnoeledge():Observable<LegalKnowledgeBean>

    /**
     *知识详情
     *https://yuntaifawu.com/api/special/get_zhuanti_neirong
     * */
    @POST("special/get_zhuanti_neirong")
    @FormUrlEncoded
    fun getKnowledgeDetails(@Field("id") id :String) :Observable<KnowledgeDetailsBean>
}