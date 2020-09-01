package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.zixun.bean.AdoptionBean;
import com.ytfu.yuntaifawu.ui.zixun.bean.ReplyDetailBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Auther gxy
 * @Date 2020/6/16
 * @Des 回复详情api
 */
public interface ReplyDetailsApi {
    /**
     * 评价详情
     * https://yuntaifawu.com/api/consult/get_consult_pingjia
     */
    @POST("consult/get_consult_pingjia")
    @FormUrlEncoded
    Observable<ReplyDetailBean> setReplyDetail(@Field("id") String id);

    /**
     * 采纳
     * https://yuntaifawu.com/api/consult/get_adopt
     * */
    @POST("consult/get_adopt")
    @FormUrlEncoded
    Observable<AdoptionBean> setAdoption(@Field("uid") String uid,
                                         @Field("pingjia_uid") String pingjia_uid,
                                         @Field("pingjia_id") String pingjia_id,
                                         @Field("consult_id") String consult_id);
}
