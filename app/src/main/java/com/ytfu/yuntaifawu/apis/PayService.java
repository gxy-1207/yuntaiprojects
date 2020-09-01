package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.chatroom.bean.FeeWechatOrderResponse;
import com.ytfu.yuntaifawu.ui.mseeage.bean.WhetherToPayBean;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.pay.bean.PayBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 支付相关网络请求
 */
public interface PayService {


    /**
     * 咨询付费,获取微信订单
     *
     * @param selfId       自己的id
     * @param receiverId   接收者的id
     * @param redPackageId 对应的咨询服务费的id
     * @param type         固定为12
     * @param xitong       固定为1
     */
    @POST("wxapppay/buy")
    @FormUrlEncoded
    Observable<FeeWechatOrderResponse> getFeeWechatPayOrder(@Field("uid") String selfId,
                                                            @Field("lv_id") String receiverId,
                                                            @Field("jilu_id") String redPackageId,
                                                            @Field("type") int type,
                                                            @Field("xitong") int xitong
    );

    @POST("income/buy")
    @FormUrlEncoded
    Observable<AccountPayResponseBean> payFeeByAccount(@Field("uid") String selfId,
                                                       @Field("lv_id") String receiverId,
                                                       @Field("jilu_id") String redPackageId,
                                                       @Field("type") int type,
                                                       @Field("xitong") int xitong
    );

    /**
     * 咨询付费,获取支付宝订单
     *
     * @param selfId       自己的id
     * @param receiverId   接收者的id
     * @param redPackageId 对应的咨询服务费的id
     * @param type         固定为12
     * @param xitong       固定为1
     */
    @POST("pay/buy")
    @FormUrlEncoded
    Observable<PayBean> getFeeAliPayOrder(@Field("uid") String selfId,
                                          @Field("lv_id") String receiverId,
                                          @Field("jilu_id") String redPackageId,
                                          @Field("type") int type,
                                          @Field("xitong") int xitong
    );


    /**
     * 检测用户时候已经支付,已经支付了才能进行下一步操作
     */
    @POST("huanxin/checkpay2")
    @FormUrlEncoded
    Observable<WhetherToPayBean> checkPay(@Field("user_id") String selfId,
                                          @Field("lv_id") String toUserId
    );

    /**
     * 获取悬赏支付订单
     * <p>
     *
     * @param userId      用户id
     * @param advisoryId  咨询id
     * @param type        14是咨询悬赏16是置顶咨询18是追加悬赏19是咨询解锁
     * @param xitong      安卓是1 苹果是2
     * @param isExpedited 是否加急 加急是1 不加急是2
     * @param price       咨询悬赏：悬赏的金额 追加悬赏：悬赏的金额
     */
    @POST("wxapppay/buy")
    @FormUrlEncoded
    Observable<FeeWechatOrderResponse> getBountyPaymentWechatOrder(
            @Field("uid") String userId,
            @Field("id") String advisoryId,
            @Field("type") int type,
            @Field("xitong") int xitong,
            @Field("consult_jiaji") int isExpedited,
            @Field("price") String price
    );

    /**
     * 获取悬赏支付订单
     * <p>
     *
     * @param userId      用户id
     * @param advisoryId  咨询id
     * @param type        14是咨询悬赏16是置顶咨询18是追加悬赏19是咨询解锁
     * @param xitong      安卓是1 苹果是2
     * @param isExpedited 是否加急 加急是1 不加急是2
     * @param price       咨询悬赏：悬赏的金额 追加悬赏：悬赏的金额
     */
    @POST("pay/buy")
    @FormUrlEncoded
    Observable<PayBean> getBountyPaymentAliOrder(
            @Field("uid") String userId,
            @Field("id") String advisoryId,
            @Field("type") int type,
            @Field("xitong") int xitong,
            @Field("consult_jiaji") int isExpedited,
            @Field("price") String price
    );

    /**
     * 余额支付
     * <p>
     *
     * @param userId      用户id
     * @param advisoryId  咨询id
     * @param type        14是咨询悬赏16是置顶咨询18是追加悬赏19是咨询解锁
     * @param xitong      安卓是1 苹果是2
     * @param isExpedited 是否加急 加急是1 不加急是2
     * @param price       咨询悬赏：悬赏的金额 追加悬赏：悬赏的金额
     */
    @POST("income/buy")
    @FormUrlEncoded
    Observable<AccountPayResponseBean> payByAccount(
            @Field("uid") String userId,
            @Field("id") String advisoryId,
            @Field("type") int type,
            @Field("xitong") int xitong,
            @Field("consult_jiaji") int isExpedited,
            @Field("price") String price
    );


    /**
     * 用户发送红包
     * <p>
     * uid	是	int	用户id
     * lid	是	int	律师id
     * type	是	int	15
     * xitong	是	int	安卓是1 苹果是2
     * price	是	int	红包的金额
     */
    @POST("wxapppay/buy")
    @FormUrlEncoded
    Observable<FeeWechatOrderResponse> userSendRedPackageByWechat(
            @Field("uid") String userId,
            @Field("lid") String lawyerId,
            @Field("type") int type,
            @Field("xitong") int xitong,
            @Field("price") double price
    );

    /**
     * 用户发送红包
     * <p>
     * uid	是	int	用户id
     * lid	是	int	律师id
     * type	是	int	15
     * xitong	是	int	安卓是1 苹果是2
     * price	是	int	红包的金额
     */
    @POST("pay/buy")
    @FormUrlEncoded
    Observable<PayBean> userSendRedPackageByAli(
            @Field("uid") String userId,
            @Field("lid") String lawyerId,
            @Field("type") int type,
            @Field("xitong") int xitong,
            @Field("price") double price
    );
    /**
     * 用户发送红包
     * <p>
     * uid	是	int	用户id
     * lid	是	int	律师id
     * type	是	int	15
     * xitong	是	int	安卓是1 苹果是2
     * price	是	int	红包的金额
     * https://yuntaifawu.com/api/income/buy
     */
    @POST("income/buy")
    @FormUrlEncoded
    Observable<AccountPayResponseBean> userSendRedPackageByBlance(
            @Field("uid") String userId,
            @Field("lid") String lawyerId,
            @Field("type") int type,
            @Field("xitong") int xitong,
            @Field("price") double price
    );

    /**
     * 微信充值
     *
     * @param userId 用户id
     * @param type   20
     * @param xitong 安卓是1 苹果是2
     * @param price  充值金额
     */
    @POST("wxapppay/buy")
    @FormUrlEncoded
    Observable<FeeWechatOrderResponse> topUpByWechat(
            @Field("uid") String userId,
            @Field("type") int type,
            @Field("xitong") int xitong,
            @Field("price") double price
    );


    /**
     * 微信充值
     *
     * @param userId 用户id
     * @param type   20
     * @param xitong 安卓是1 苹果是2
     * @param price  充值金额
     */
    @POST("pay/buy")
    @FormUrlEncoded
    Observable<PayBean> topUpByAli(
            @Field("uid") String userId,
            @Field("type") int type,
            @Field("xitong") int xitong,
            @Field("price") double price
    );


}
