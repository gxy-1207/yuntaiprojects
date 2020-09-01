package com.ytfu.yuntaifawu.apis;

import com.ytfu.yuntaifawu.ui.users.bean.MyRefundBean;
import com.ytfu.yuntaifawu.ui.users.bean.RefundButtonVisibleBean;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @Auther gxy
 * @Date 2020/7/23
 * @Des 我的退款
 */
public interface RefundService {

    /**
     * 退款列表接口
     * http://yuntaifawu.com/api/Refund/RefundList
     *
     * @param UserID 用户id
     */
    @POST("Refund/RefundList")
    @FormUrlEncoded
    Observable<MyRefundBean> setMyRefundList(@Field("UserID") String UserID);

    @Multipart
    @POST("Refund/subRefund")
    Observable<MyRefundBean> subRefund(
            @Part("UserID") RequestBody userId,
            @Part("LsID") RequestBody lawyerId,
            @Part("Content") RequestBody content,
            @Part MultipartBody.Part file
    );

    /**
     *控制退款入口开关接口
     *https://yuntaifawu.com/api/Refund/SwitcthRefund
     * */
    @POST("Refund/SwitcthRefund")
    Observable<RefundButtonVisibleBean> setRefundButtonVisible();

}
