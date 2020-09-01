package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.chatroom.bean.RoomLawyerCardInfoResponse;
import com.ytfu.yuntaifawu.ui.home.bean.RandomLawyerResponse;
import com.ytfu.yuntaifawu.ui.lvshiwode.bean.QuestionResponseBean;
import com.ytfu.yuntaifawu.ui.mine.bean.InformationBean;
import com.ytfu.yuntaifawu.ui.mine.bean.MineBean;
import com.ytfu.yuntaifawu.ui.users.bean.LawyerHomeListBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LawyerService {

    @POST("my/get_my_content")
    Observable<MineBean> getPersonalData(@Query("uid") String uid);

    /**
     * 获取个人信息
     */
    @POST("my/information")
    @FormUrlEncoded
    Observable<InformationBean> getInformation(@Field("uid") String uid);

    /**
     * 获取个人信息
     */
    @POST("index/get_lvshi_card")
    @FormUrlEncoded
    Observable<RoomLawyerCardInfoResponse> getLawyerCardInfo(@Field("lid") String uid);

    /**
     * 首页律师列表和搜索列表
     * http://yuntaifawu.com/api/find/lawyerList
     */
    @POST("find/lawyerList")
    @FormUrlEncoded
    Observable<LawyerHomeListBean> setLawyerList(@Field("lawyername") String lawyername, @Field("expertplace") String expertplace, @Field("page") String page);


    /**
     * 获取可是设置的抢答信息
     */
    @POST("find/getQuestionInfo")
    @FormUrlEncoded
    Observable<QuestionResponseBean> getQuestionInfo(@Field("lsid") String lawyerId);


    @POST("Find/getRandLawyer")
    Observable<RandomLawyerResponse> getRandomLawyers();
}
