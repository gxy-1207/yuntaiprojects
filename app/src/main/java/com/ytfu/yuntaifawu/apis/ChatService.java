package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.chat.bean.DeleteResponseBean;
import com.ytfu.yuntaifawu.ui.chat.bean.LawyerListBean;
import com.ytfu.yuntaifawu.ui.chat.bean.UnLockMessage;
import com.ytfu.yuntaifawu.ui.home.bean.AudioNavBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 律师相关的网络请求
 */
public interface ChatService {
    /**
     * 获取律师聊天列表
     */
    @GET("Newzixun/chat_list")
    Observable<LawyerListBean> getLawyerChatList(@Query("uid") String uid);


    @POST("consult/get_consult_type_new")
    @FormUrlEncoded
    Observable<UnLockMessage> getConsultType(@Field("uid") String uid);


    @POST("index/get_lvshi_msg_ceshi")
    @FormUrlEncoded
    Observable<Object> test(@Field("uid") String uid);


    @POST("chat/delete_chat_list")
    @FormUrlEncoded
    Observable<DeleteResponseBean> deleteConversation(
            @Field("uid") String uid,
            @Field("list") String list);


}
