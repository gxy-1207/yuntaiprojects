package com.ytfu.yuntaifawu.apis

import com.ytfu.yuntaifawu.ui.complaint.bean.ComplaintResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ComplaintApi {

    @POST("qisuzhuang/get_qisuzhuang_list")
    @FormUrlEncoded
    fun complaintList(
            @Field("uid") ownerId: String,
            @Field("lsid") lawyerId: String,
            @Field("type") type: Int
    ): Observable<ComplaintResponse>


}