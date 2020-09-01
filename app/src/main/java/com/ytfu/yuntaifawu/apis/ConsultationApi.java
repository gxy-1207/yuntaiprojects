package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Auther gxy
 * @Date 2020/6/15
 * @Des 咨询详情
 */
public interface ConsultationApi {
/**
 * https://yuntaifawu.com/api/consult/get_conuslt_xiangqin_new
 * 咨询详情
 * */
    @POST("consult/get_conuslt_xiangqin_new")
    @FormUrlEncoded
    Observable<ConsultationDetailsBean> setConsultationDetail(@Field("id") String id);
}
