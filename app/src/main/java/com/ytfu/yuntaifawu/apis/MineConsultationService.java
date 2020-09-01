package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.users.bean.MineConsulitatioBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/12
*
*  @Des  我的咨询service
*
*/
public interface MineConsultationService {
    /**
    *
    *  @Auther  gxy
    *
    *  @Date    2020/6/12
    *
    *  @Des   咨询列表接口   http://yuntaifawu.com/api/consult/get_conuslt_list
    *
    */
    @POST("consult/get_conuslt_list")
    @FormUrlEncoded
    Observable<MineConsulitatioBean> setMineConsulitationList(@Field("page") int page, @Field("uid") String uid);
}
