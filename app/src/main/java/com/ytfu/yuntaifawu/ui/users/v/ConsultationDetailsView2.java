package com.ytfu.yuntaifawu.ui.users.v;

import com.ytfu.yuntaifawu.base.BaseRefreshView;
import com.ytfu.yuntaifawu.base.BaseView;
import com.ytfu.yuntaifawu.ui.pay.bean.AccountPayResponseBean;
import com.ytfu.yuntaifawu.ui.users.bean.ConsultationDetailsBean;

/**
*
*  @Auther  gxy
*
*  @Date    2020/6/15
*
*  @Des  咨询详情view
*
*/
public interface ConsultationDetailsView2 extends BaseView {

    void onConsultationDetailsSuccess(ConsultationDetailsBean consultationDetailsBean);
//    /**唤起支付宝支付*/
//    void onAwakeAli(String sign);
//
//    /**账户余额支付成功的回调*/
//    void onPayByAccountSuccess(AccountPayResponseBean payResponseBean);
    void onConsultationDetailsFiled();
}
