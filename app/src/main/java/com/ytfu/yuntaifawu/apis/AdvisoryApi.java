package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.home.bean.AdvisoryPostBean;
import com.ytfu.yuntaifawu.ui.lawyer.transaction.bean.TransactionResponseBean;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountInfoBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AdvisoryApi {

    /**
     * uid	 	用户id
     * consult_type	 	分类
     * consult_content	 	咨询内容
     * reward_type	 	1:是悬赏状态
     */
    @POST("Consult/post_consult_cntent_new")
    @FormUrlEncoded
    Observable<AdvisoryPostBean> postConsultation(@Field("uid") String userId,
                                                  @Field("consult_type") String consultType,
                                                  @Field("consult_content") String consultContent,
                                                  @Field("reward_type") int rewardType);


    /**
     * 获取悬赏支付信息
     * uid	 	用户id
     */
    @POST("consult/get_xuanshan_pay")
    @FormUrlEncoded
    Observable<AccountInfoBean> getAdvisoryInfo(@Field("uid") String userId);


}
