package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.users.bean.AnnouncementBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/** @Auther gxy @Date 2020/6/12 @Des 公告service */
public interface AnnouncementService {
  /** 公告列表接口 *https://yuntaifawu.com/api/find/Notice */
  @POST("find/Notice")
  @FormUrlEncoded
  Observable<AnnouncementBean> setAnnouncementList(
      @Field("page") int page, @Field("uid") String uid);
}
