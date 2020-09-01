package com.ytfu.yuntaifawu.ui.users.v;

import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.users.bean.OverageBean;
import com.ytfu.yuntaifawu.ui.users.bean.PublicPriceBean;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/17
*
*  @Des 置顶支付view
*
*/
public interface TopPaymentView extends BaseView {
    //获取置顶支付金额
    void getTopPayPriec(PublicPriceBean publicPriceBean);
    /**唤起支付宝支付*/
    void onAwakeAli(String sign);

    /**账户余额支付成功的回调*/
    void onPayByAccountSuccess(AccountPayResponseBean payResponseBean);
}
