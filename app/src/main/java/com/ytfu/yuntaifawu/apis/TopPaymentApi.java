package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.WxPayBean;
import com.ytfu.yuntaifawu.ui.users.bean.OverageBean;
import com.ytfu.yuntaifawu.ui.users.bean.PublicPriceBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/17
*
*  @Des  置顶支付api
*
*/
public interface TopPaymentApi {
    /**
     * 获取公共金额
     *https://yuntaifawu.com/api/zixun/zixun_price
     * */
    @GET("zixun/zixun_price")
    Observable<PublicPriceBean> setPublicPrice();
    /**
     * 获取账户余额
     * http://yuntaifawu.com/api/my/get_my_pay
     * */
    @POST("my/get_my_pay")
    @FormUrlEncoded
    Observable<OverageBean> setOverage(@Field("uid") String uid);
    /**
     * 微信支付
     * https://yuntaifawu.com/api/wxapppay/buy
     * */
    @POST("wxapppay/buy")
    @FormUrlEncoded
    //String uid, String id, int type, int xitong, String consult_jiaji, String price
    Observable<WxPayBean> setPayWatch(@Field("uid") String uid,
                                      @Field("id") String id,
                                      @Field("type") int type,
                                      @Field("xitong") int xitong,
                                      @Field("consult_jiaji") String consult_jiaji,
                                      @Field("price") String price);
    /**
     * 支付宝
     *https://yuntaifawu.com/api/pay/buy
     * */
    @POST("pay/buy")
    @FormUrlEncoded
    Observable<PayBean> stePayAli(@Field("uid") String uid,
                                  @Field("id") String id,
                                  @Field("type") int type,
                                  @Field("xitong") int xitong,
                                  @Field("consult_jiaji") String consult_jiaji,
                                  @Field("price") String price);
    /**
     * 余额支付
     * https://yuntaifawu.com/api/income/buy
     * */
    @POST("income/buy")
    @FormUrlEncoded
    Observable<AccountPayResponseBean> setPayOverage(@Field("uid") String uid,
                                                     @Field("id") String id,
                                                     @Field("type") int type,
                                                     @Field("xitong") int xitong,
                                                     @Field("consult_jiaji") String consult_jiaji,
                                                     @Field("price") String price);
}
